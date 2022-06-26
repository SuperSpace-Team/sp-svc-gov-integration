package com.sp.infra.svc.gov.sdk.alm.daemon;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.service.SendLogService;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luchao
 * @date 2021/4/25 8:26 下午
 */
public class MonitorLogSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MonitorLogSender.class.getName());

    private MonitorGlobalContext context;
    private SendLogService sendLogService;
    private boolean exit = false;

    public MonitorLogSender(MonitorGlobalContext context, SendLogService sls) {
        this.context = context;
        sendLogService = sls;
    }

    public void setExit() {
        this.exit = true;
    }

    @Override
    public void run() {
    	logger.info("MonitorLogSender startup, thread name " + Thread.currentThread().getName());
        int batchsize = context.getAlmConfig().getLogBatchSize();
        while (!exit) {
            try {
                List<LogMessage> logList = new ArrayList<>(batchsize + 1);
                
                context.pollLogs(logList, SdkCommonConstant.LOG_TYPE_MONITOR, batchsize);
                if (! logList.isEmpty()) {
                	if (logger.isDebugEnabled())
                		logger.debug("logList size:" + logList.size());
                    sendLogService.writeLog(logList);
                }
                // 没有数据的情况下， sleep 1s
                if (logList.isEmpty() || (logList.size() < batchsize)) {
                    ThreadUtil.sleep(context.getAlmConfig().getLogSendPollingInterval() * 1000);
                }
            } catch (Exception e) {
                logger.error("sender error.", e);
            }
        }
    }
}
