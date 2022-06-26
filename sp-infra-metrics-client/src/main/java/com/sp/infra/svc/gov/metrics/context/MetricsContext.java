package com.sp.infra.svc.gov.metrics.context;

/**
 * @description: Metrics上下文
 * @author: luchao
 * @date: Created in 4/16/21 8:10 PM
 */
public class MetricsContext {
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("MetricsContext [time=");
        stb.append(time);
        stb.append("]");
        return stb.toString();
    }
}
