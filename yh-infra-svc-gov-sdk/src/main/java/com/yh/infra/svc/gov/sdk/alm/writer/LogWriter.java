package com.yh.infra.svc.gov.sdk.alm.writer;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.command.cfg.LogTarget;

import java.util.List;

/**
 * @author luchao
 * @date 2021/4/25 8:33 下午
 */
public interface LogWriter {
	LogTarget match(List<LogTarget> logTargets, int bizCode);
	
	void writeLog(List<MonitorLogMessage> commands, LogTarget target);
}
