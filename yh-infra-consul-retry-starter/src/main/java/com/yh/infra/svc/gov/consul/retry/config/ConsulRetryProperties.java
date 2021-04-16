package com.yh.infra.svc.gov.consul.retry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.cloud.consul.retry")
public class ConsulRetryProperties {
    /**
     * 监测间隔（单位：ms）
     */
    private long retryInterval = 10000L;

    private boolean enable = false;

    public long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public boolean isEnable() { return enable; }

    public void setEnable(boolean enable) { this.enable = enable; }
}