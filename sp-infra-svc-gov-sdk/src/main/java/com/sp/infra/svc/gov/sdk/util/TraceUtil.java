package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * @author qinzhiyuan
 * @date 2021/8/10 8:40 下午
 */
@Slf4j
public class TraceUtil {
    /**
     * 将skywalking信息填入日志消息
     * @param lmc
     */
    public static void fillSkyWalkingInfo(MonitorLogMessage lmc) {
        try {
            lmc.setSwSegment(TraceContext.segmentId());
            lmc.setSwTrace(TraceContext.traceId());
            lmc.setSwSpan(String.valueOf(TraceContext.spanId()));
        } catch (Exception e) {
            log.warn("Failed to fill skywalking trace info. ", e);
        }
    }
}
