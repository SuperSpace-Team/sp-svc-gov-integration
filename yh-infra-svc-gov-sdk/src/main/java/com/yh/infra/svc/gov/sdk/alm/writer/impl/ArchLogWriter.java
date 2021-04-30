package com.yh.infra.svc.gov.sdk.alm.writer.impl;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.service.FusingProxyService;
import com.yh.infra.svc.gov.sdk.alm.writer.LogWriter;
import com.yh.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;

import java.util.List;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:33 下午
 */
public class ArchLogWriter implements LogWriter {
	private FusingProxyService fusingProxyService;
	
	public ArchLogWriter(FusingProxyService fps) {
		fusingProxyService = fps;
	}
	
	@Override
	public LogTarget match(List<LogTarget> logTargets, int bizCode) {
		for (LogTarget t: logTargets) {
			if ((t.getBizCode() == bizCode) && t.getLogArch())
				return t;
		}
		return null;
	}

	@Override
	public void writeLog(List<MonitorLogMessage> commands, LogTarget target) {
		fusingProxyService.sendLog(commands, SdkCommonConstant.LOG_TYPE_MONITOR);
	}

	
}
