//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yh.infra.svc.gov.metrics.push.config;

import com.yh.infra.svc.gov.metrics.push.manager.PrometheusPushGatewayManager;

import java.util.HashMap;
import java.util.Map;

public class PrometheusProperties {
    private boolean descriptions = true;
    private final Pushgateway pushgateway = new Pushgateway();
    private int step = 1;

    public PrometheusProperties() {
    }

    public boolean isDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(boolean descriptions) {
        this.descriptions = descriptions;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Pushgateway getPushgateway() {
        return this.pushgateway;
    }

    public static class Pushgateway {
        private Boolean enabled = false;
        private String baseUrl = "http://localhost:9091";
        private int pushRate = 1;
        private String job;
        private Map<String, String> groupingKey = new HashMap<>();
        private PrometheusPushGatewayManager.ShutdownOperation shutdownOperation;

        public Pushgateway() {
            this.shutdownOperation = PrometheusPushGatewayManager.ShutdownOperation.NONE;
        }

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getBaseUrl() {
            return this.baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public int getPushRate() {
            return this.pushRate;
        }

        public void setPushRate(int pushRate) {
            this.pushRate = pushRate;
        }

        public String getJob() {
            return this.job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Map<String, String> getGroupingKey() {
            return this.groupingKey;
        }

        public void setGroupingKey(Map<String, String> groupingKey) {
            this.groupingKey = groupingKey;
        }

        public PrometheusPushGatewayManager.ShutdownOperation getShutdownOperation() {
            return this.shutdownOperation;
        }

        public void setShutdownOperation(PrometheusPushGatewayManager.ShutdownOperation shutdownOperation) {
            this.shutdownOperation = shutdownOperation;
        }
    }
}
