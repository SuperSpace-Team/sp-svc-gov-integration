package com.yh.infra.svc.gov.sdk.alm.client;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogUnitCommand;
import com.yh.infra.svc.gov.sdk.alm.command.TransformLogUnitCommand;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 给业务应用使用的日志接口
 *
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:16 下午
 */
public class LogService {
	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	
	/**
	 * 写 监控日志。
	 * 
	 * @param code
	 * @param key
	 * @param inLog
	 * @param outLog
	 */
	public static void mLog(int code, String key, String inLog, String outLog) {
		if (! checkStatus()) {
			return;
		}
		
		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
		Node n = context.getCustomizeNode(code);
		if (n == null) {
			logger.error("No node found for {}.", code);
			return;
		}
		MonitorLogMessage lmc = genLogMessage(n.getBizCode());
		
		List<MonitorLogUnitCommand> mLogList = new ArrayList<>();
		MonitorLogUnitCommand mluc = new MonitorLogUnitCommand();
		mluc.setCode(code);
		mluc.setInLog(inLog);
		mluc.setOutLog(outLog);
		mluc.setKey(key);
		mLogList.add(mluc);
		lmc.setMonitorLogList(mLogList);
		context.addLog(lmc);
	}
	
	private static boolean checkStatus() {
		// 必须是GSDK类型的 监控
		String almType = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_EMBEDDED_TYPE);
		if (! SdkCommonConstant.ALM_TYPE_GSDK.equals(almType)) {
			logger.error("Function disabled. Need to update agent version.");
			return false;
		}
		// 必须是 已经初始化  成功。
		Boolean init = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
		if (! Boolean.TRUE.equals(init)) {
			logger.error("ALM has not been initialized.");
			return false;
		}
		return true;
	}
	
	private static MonitorLogMessage genLogMessage(int bizCode) {
		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
		
		MonitorLogMessage lmc = new MonitorLogMessage(SdkCommonConstant.LOG_TYPE_MONITOR);
		lmc.setAppId(context.getAppId());
		lmc.setBizCode(bizCode);
		lmc.setCfgVersion(context.getCurrentVersion());
		lmc.setElaspedTime(0);
		lmc.setHostName(context.getPgConfig().getHostName());
		lmc.setIp(context.getPgConfig().getIp());
		lmc.setSdkVersion(context.getSdkVersion());
		lmc.setThreadName(Thread.currentThread().getName());
		lmc.setTimeStamp(System.currentTimeMillis());
		return lmc;
	}
	
	/**
	 * 写转换日志。
	 * 
	 * @param code
	 * @param srcKey
	 * @param targetKey
	 */
	public static void tLog(int code, String srcKey, String targetKey) {
		if (! checkStatus()) {
			return;
		}

		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
		TransformNode n = context.getCustomizeTnode(code);
		if (n == null) {
			logger.error("No transform node found for {}.", code);
			return;
		}
		MonitorLogMessage lmc = genLogMessage(n.getBizCode());
		
		List<TransformLogUnitCommand> tLogList = new ArrayList<>();
		TransformLogUnitCommand tluc = new TransformLogUnitCommand();
		tluc.setCode(code);
		tluc.setSrcKey(srcKey);
		tluc.setTargetKey(targetKey);
		
		tLogList.add(tluc);
		lmc.setTransformLogList(tLogList);
		context.addLog(lmc);
	}
}
