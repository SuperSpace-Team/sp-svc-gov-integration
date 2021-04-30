package com.yh.infra.svc.gov.sdk.alm.command;

import com.yh.infra.svc.gov.sdk.alm.constant.FusingStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 熔断状态管理， 针对节点。本类的修改操作只会被 FusingManager调用。 不用线程同步。
 *
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:11 下午
 */
public class FusingStatus {
	
	private static final Logger logger = LoggerFactory.getLogger(FusingStatus.class);
	
	private FusingStatusEnum status = FusingStatusEnum.CLOSE;
	private Long changeTime = null;
	private FusingBucket header = null;

	public FusingStatus(int bucketSize) {
		FusingBucket h = null;
		for (int i = 0; i < bucketSize; i++) {
			FusingBucket fb = new FusingBucket();
			if (h == null) {
				h = fb;
				header = fb;
				continue;
			}
			h.setNext(fb);
			fb.setPrevious(h);
			h = fb;
		}
		h.setNext(header);
		header.setPrevious(h);
		changeTime = System.currentTimeMillis();
	}

	/**
	 * 是否超过了熔断的冷却时间。传入参数是毫秒。
	 * 
	 * @param time
	 * @return
	 */
	public boolean expireCoolingTime(int time) {
		if (status != FusingStatusEnum.OPEN)
			return true;

		if (changeTime == null)
			return true;
		return (System.currentTimeMillis() - changeTime) > time ;
	}

	/**
	 * 本状态的change time， 在入参 时间戳之后。
	 * 
	 * @param ts
	 * @return
	 */
	public boolean after(long ts) {
		return ts < changeTime;
	}

	public void open() {
		if (logger.isDebugEnabled())
			logger.debug("status changed to open");
		status = FusingStatusEnum.OPEN;
		changeTime = System.currentTimeMillis();
	}

	public void close() {
		if (logger.isDebugEnabled())
			logger.debug("status changed to close");
		status = FusingStatusEnum.CLOSE;
		changeTime = System.currentTimeMillis();
	}

	public void halfOpen() {
		if (logger.isDebugEnabled())
			logger.debug("status changed to halfOpen");
		status = FusingStatusEnum.HALF;
		changeTime = System.currentTimeMillis();
	}

	public FusingStatusEnum getStatus() {
		return status;
	}

	public Long getChangeTime() {
		return changeTime;
	}

	public FusingBucket getHeader() {
		return header;
	}

	public void setHeader(FusingBucket header) {
		this.header = header;
	}

}
