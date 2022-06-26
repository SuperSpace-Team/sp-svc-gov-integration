package com.sp.infra.svc.gov.sdk.alm.client;

import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogUnitCommand;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.command.cfg.Node;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinzhiyuan
 * @email 80961464@superspace.cn
 * @date 2021/8/3 6:27 下午
 */
@Data
public class MonitorLogService {
    private static final Logger logger = LoggerFactory.getLogger(MonitorLogService.class);

    private MonitorLogMessage monitorLogMessage;
    private MonitorLogUnitCommand monitorLog;
    private Logger loggerRef;

    public MonitorLogService(Integer nodeCode) {
        this(null, nodeCode, null);
    }

    public MonitorLogService(Integer nodeCode, String bizKey) {
        this(null, nodeCode, bizKey);
    }

    public MonitorLogService(Logger loggerRef, Integer nodeCode) {
        this(loggerRef, nodeCode, null);
    }

    public MonitorLogService(Logger loggerRef, Integer nodeCode, String bizKey) {
        this.loggerRef = loggerRef;

        // 只校验，不抛异常
        if (nodeCode == null) {
            return;
        }
        // 校验是否开启了全链路监控，并初始化完毕
        if (!LogService.checkStatus()) {
            return;
        }
        // 校验节点是否存在
        MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
        Node node = null;
        try {
            node = context.getCustomizeNode(nodeCode);
        } catch (Exception e) {
            logger.warn("Get custom node error {}", nodeCode);
        }
        if (node == null) {
            logger.warn("No custom node found for {}.", nodeCode);
            return;
        }

        monitorLog = new MonitorLogUnitCommand();
        monitorLog.setCode(nodeCode);
        monitorLog.setKey(bizKey);

        List<MonitorLogUnitCommand> mLogList = new ArrayList<>();
        mLogList.add(monitorLog);

        monitorLogMessage = LogService.genLogMessage(node.getBizCode());
        monitorLogMessage.setMonitorLogList(mLogList);
    }

    public void before() {
        this.before(null, null);
    }

    public void before(String log) {
        this.before(null, log);
    }

    public void before(String bizKey, String log) {
        printLog(log, false);

        if (monitorLog == null) {
            return;
        }

        if (StringUtils.isNotEmpty(bizKey)) {
            monitorLog.setKey(bizKey);
        }
        monitorLog.setInLog(log);
        // 记录日志初始时间戳
        monitorLogMessage.setTimeStamp(System.currentTimeMillis());
    }

    public void after() {
        this.after(null, null);
    }

    public void after(String log) {
        this.after(null, log);
    }

    public void after(String bizKey, String log) {
        printLog(log, false);
        handleAfter(bizKey, log, false);
    }

    public void error(Throwable t) {
        this.error(null, t);
    }

    public void error(String bizKey, Throwable t) {
        if (loggerRef != null) {
            loggerRef.error(t.getMessage(), t);
        }
        handleAfter(bizKey, t.getMessage(), true);
    }

    public void error(String log) {
        this.error(null, log);
    }

    public void error(String bizKey, String log) {
        printLog(log, true);
        handleAfter(bizKey, log, true);
    }

    private void handleAfter(String bizKey, String log, Boolean isError) {
        if (monitorLog == null) {
            return;
        }

        if (StringUtils.isNotEmpty(bizKey)) {
            monitorLog.setKey(bizKey);
        }

        if (StringUtils.isEmpty(monitorLog.getKey())) {
            logger.warn("Not set bizKey, cancel send log.");
            return;
        }

        if (isError) {
            monitorLog.setExceptionLog(log);
        } else {
            monitorLog.setOutLog(log);
        }

        // 计算耗时
        if (monitorLogMessage.getTimeStamp() != 0) {
            monitorLogMessage.setElaspedTime(System.currentTimeMillis() - monitorLogMessage.getTimeStamp());
        }
        // 发送日志
        BeanRegistry.getInstance().getBean(MonitorGlobalContext.class).addLog(monitorLogMessage);
    }

    private void printLog(String log, Boolean isError) {
        if (loggerRef == null || StringUtils.isEmpty(log)) {
            return;
        }

        if (isError) {
            loggerRef.error(log);
        } else {
            loggerRef.info(log);
        }
    }
}
