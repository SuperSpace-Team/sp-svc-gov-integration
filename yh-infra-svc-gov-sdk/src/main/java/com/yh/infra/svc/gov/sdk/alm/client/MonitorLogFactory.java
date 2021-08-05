package com.yh.infra.svc.gov.sdk.alm.client;

import org.slf4j.Logger;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/8/3 6:23 下午
 */
public class MonitorLogFactory {
    private static MonitorLogFactory instance = new MonitorLogFactory();

    public static MonitorLogFactory getInstance() {
        return instance;
    }

    public static MonitorLogFactory getInstance(Logger loggerRef) {
        return new MonitorLogFactory(loggerRef);
    }

    private Logger loggerRef;

    private MonitorLogFactory() {
    }

    private MonitorLogFactory(Logger loggerRef) {
        this.loggerRef = loggerRef;
    }

    public MonitorLogService newService(Integer nodeCode) {
        return new MonitorLogService(loggerRef, nodeCode);
    }

    public MonitorLogService newService(Integer nodeCode, String bizKey) {
        return new MonitorLogService(loggerRef, nodeCode, bizKey);
    }
}
