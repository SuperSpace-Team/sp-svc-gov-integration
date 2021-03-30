package com.yh.infra.svc.gov.consul.retry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.cloud.consul.retry")
public class ConsulRetryProperties {
    /**
     * 监测间隔（单位：ms）
     */
    private long bzRetryInterval = 10000L;

    private boolean enable = false;

    public long getBzRetryInterval() {
        return bzRetryInterval;
    }

    public void setBzRetryInterval(long bzRetryInterval) {
        this.bzRetryInterval = bzRetryInterval;
    }

    public boolean isEnable() { return enable; }

    public void setEnable(boolean enable) { this.enable = enable; }
}