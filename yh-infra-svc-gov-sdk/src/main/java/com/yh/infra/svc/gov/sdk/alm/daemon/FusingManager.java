package com.yh.infra.svc.gov.sdk.alm.daemon;

import com.yh.infra.svc.gov.sdk.alm.command.FusingBucket;
import com.yh.infra.svc.gov.sdk.alm.command.FusingStatus;
import com.yh.infra.svc.gov.sdk.alm.command.ResponseStatusCommand;
import com.yh.infra.svc.gov.sdk.alm.constant.FusingStatusEnum;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理发送日志的结果，计算并管理熔断状态。 单线程，无限循环
 *
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:25 下午
 */
public class FusingManager extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FusingManager.class.getName());

	private MonitorGlobalContext context;
	private boolean exit = false;
	private int threshold = 0;
	private int coolingTime = 0;

	public FusingManager(MonitorGlobalContext context) {
		this.context = context;
		threshold = context.getAlmConfig().getFuseThreshold();
		coolingTime = context.getAlmConfig().getFuseCoolTimeout() * 1000;
	}

	public void setExit() {
		this.exit = true;
	}

	@Override
	public void run() {

		while (!exit) {
			try {
				Boolean init = (Boolean) BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
				// 尚未初始化成功，不做任何操作
				if (init == null) {
					logger.warn("agent has not initialized, will sleep 1s.");
					ThreadUtil.sleep(1000);
					continue; //SONAR
				}

				FusingStatus status = context.getFusingStatus();

				// 打开，但是冷却超时。则 半开。
				if ((status.getStatus() == FusingStatusEnum.OPEN) && status.expireCoolingTime(coolingTime)) {
					logger.info("熔断冷却超时，进入半开状态。");
					status.halfOpen();
				}
				ResponseStatusCommand cmd = context.getStatusCommand(1);
				if (cmd == null) {
					// 如果没有数据或者发生异常，强制sleep 1秒， 避免进入快速的死循环。
					// 这样， 如果是没有数据， 实际sleep 2秒
					ThreadUtil.sleep(1000);
					continue; //SONAR
				}
				if (logger.isDebugEnabled())
					logger.debug("get a new command : " + cmd);

				// 是状态变更之前产生的数据。废弃。
				if (status.after(cmd.getTimeStamp())) {
					logger.info("command is abandoned " + cmd);
					continue; //SONAR
				}

				// 计数，更新bucket的时间戳。
				long current = System.currentTimeMillis();
				status.setHeader(insertCommand(status.getHeader(), cmd, current));

				// 状态评估
				evaluateStatus(status, cmd);
			} catch (Exception t) {
				logger.error("system error.", t);
			}
		}

	}
	
	/**
	 * 当前是halfopen的状态下， 根据command，决定下一步状态是什么。
	 * 
	 * @param status
	 * @param cmd
	 */
	private void evaluateHalfOpen(FusingStatus status, ResponseStatusCommand cmd) {
		switch (cmd.getStatus()) {
		case FAIL: {
			status.open();
			logger.warn("从半开状态进入熔断状态。");
			break;
		}
		case SUCCESS: {
			// 清理half-open时间之前所有的 计数器， 避免其错误技术影响 现在的 阈值计算。
			// 现在是success了， 应该重新计算阈值。
			clearBucket(status.getHeader(), status.getChangeTime());
			status.close();
			logger.info("恢复到正常状态");
			break;
		}
		case REJECT: {
			// 半开后不应该产生REJECT 数据。
			logger.warn("find a REJECT after half-open.");
		}
		}
		
	}
	/**
	 **检查状态， 并改变
	 * 
	 * @param status
	 * @param cmd
	 */
	private void evaluateStatus(FusingStatus status, ResponseStatusCommand cmd) {
		switch (status.getStatus()) {
		case CLOSE: {
			if (fuse(status.getHeader())) {
				logger.warn("进入熔断状态。");
				status.open();
			}
			break;
		}
		case OPEN: {
			break;
		}
		case HALF: {
			evaluateHalfOpen(status, cmd);
			break;
		}
		}
	}

	/**
	 * 计算错误是否超阈值
	 * 
	 * @param header
	 * @return
	 */
	private boolean fuse(FusingBucket header) {
		int total = 0;
		int fail = 0;
		FusingBucket h = header;
		while (h.getNext() != header) {
			total += h.getTotalNum();
			fail += h.getFailNum();
			h = h.getNext();
		}

		if (total <= 1) {
			return false;
		}

		return (((float) fail * 100 / total) > threshold); //NOSONAR
	}

	/**
	 * 清理指定时间点之前的bucket。无论其是否过期。
	 * 
	 * @param header
	 * @param changeTime
	 * @return
	 */
	private void clearBucket(FusingBucket header, long changeTime) {
		long end = changeTime - (changeTime % 1000); // 取得整秒。
		FusingBucket h = header.getPrevious();
		do {
			if (h.getTimeStamp() >= end) {
				break;
			}
			if (logger.isDebugEnabled())
				logger.debug("clear bucket : " + h);
			h.clear(0);
			h = h.getPrevious();
		} while (h != header);
	}

	/**
	 * 计数，并清理过期节点。
	 * 
	 * @param header
	 * @param cmd
	 * @param current
	 * @return
	 */
	private FusingBucket insertCommand(FusingBucket header, ResponseStatusCommand cmd, long current) {

		// 更新双向链表的时间戳并清空 计数器。
		long end = current - (current % 1000); // 取得整秒  的 毫秒数。  1322ms ->  1000ms
		// 这个肯定是整秒。 是目前这个 链条中的 最后的时间
		long latest = header.getTimeStamp();
		// 还在 刚初始化 后的状态。 只赋值header即可。
		if (latest == -1) {
			header.setTimeStamp(end);
			header.increase(cmd.getStatus());
			return header;
		}

		// 以30个bucket为例
		// 逐 秒 的更新每一个 bucket， 确保 当前的链条的节点 保存的 是 当前时间之前 30s 的数据。
		while (latest < end) {
			header = header.getPrevious();
			latest += 1000;
			header.clear(latest);
		}

		// 根据command，增加计数
		long cmdtime = cmd.getTimeStamp();
		cmdtime = cmdtime - (cmdtime % 1000);
		FusingBucket h = header;
		do {
			if (h.getTimeStamp() == cmdtime) {
				h.increase(cmd.getStatus());
				break;
			}
			h = h.getNext();
		} while (h != header);
		return header;
	}
}
