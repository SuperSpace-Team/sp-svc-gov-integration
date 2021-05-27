package com.yh.infra.svc.gov.sdk.alm.writer.impl;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.writer.LogWriter;
import com.yh.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author luchao
 * @date 2021/4/25 8:33 下午
 */
public class LocalFileLogWriter implements LogWriter {

    private static final Logger debugLogger = LoggerFactory.getLogger(LocalFileLogWriter.class);
    private static final Logger logger = LoggerFactory.getLogger(SdkCommonConstant.PG_LOCAL_FILE_LOGGER_NAME);

    @Override
    public LogTarget match(List<LogTarget> logTargets, int bizCode) {
        for (LogTarget t : logTargets) {
            if ((t.getBizCode() == bizCode) && t.getLogFile())
                return t;
        }
        return null;
    }

    @Override
    public void writeLog(List<MonitorLogMessage> commands, LogTarget target) {
    	String json = JsonUtil.writeValueSafe(commands);
    	if (debugLogger.isDebugEnabled())
    		debugLogger.debug(json);
    	
        if ("INFO".equalsIgnoreCase(target.getLevel())) {
            logger.info(json);
        }
        else if ("WARN".equalsIgnoreCase(target.getLevel()))
            logger.warn(json);
        else if ("ERROR".equalsIgnoreCase(target.getLevel()))
            logger.error(json);
    }
}
