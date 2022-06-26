package com.sp.infra.svc.gov.metrics.push.config;

import com.sp.infra.svc.gov.metrics.meter.MetricsHelper;
import com.sp.infra.svc.gov.metrics.push.manager.PrometheusPushGatewayManager;
import com.sp.infra.svc.gov.metrics.push.metrics.MeterRegistryPostProcessor;
import com.sp.infra.svc.gov.metrics.push.util.NetUtils;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.logging.Log4j2Metrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/16/21 8:51 PM
 */
@Configuration
@ComponentScan(basePackages = {
        "com.sp.infra.svc.gov.metrics.annotation",
        "com.sp.infra.svc.gov.metrics.aspect"
})
@EnableAspectJAutoProxy
public class MetricsConfig {
    private static final Logger logger = LoggerFactory.getLogger(MetricsConfig.class);

    @Value("${management.metrics.export.prometheus.pushgateway.enabled:false}")
    private boolean enable;

    @Value("${management.metrics.export.prometheus.pushgateway.base-url:http://localhost:9091}")
    private String baseUrl;

    @Value("${management.metrics.export.prometheus.pushgateway.job:spring-mvc-push}")
    private String job;

    @Value("${management.metrics.export.prometheus.pushgateway.shutdown-operation:push}")
    private String shutdownOperation;

    @Value("${management.metrics.export.prometheus.pushgateway.push-rate:10000}")
    private int pushRate;

    @Value("${management.metrics.tags:}")
    private String tags;

    @Bean(name = "meterRegistryPostProcessor")
    public static MeterRegistryPostProcessor meterRegistryPostProcessor(ApplicationContext applicationContext) {
        logger.info("constructing MeterRegistryPostProcessor ...");
        List<MeterBinder> meterBinders = new ArrayList<>();
        meterBinders.add(new JvmGcMetrics());
        meterBinders.add(new JvmMemoryMetrics());
        meterBinders.add(new JvmThreadMetrics());
        meterBinders.add(new ClassLoaderMetrics());
        meterBinders.add(new Log4j2Metrics());
        meterBinders.add(new ProcessorMetrics());
        meterBinders.add(new FileDescriptorMetrics());
        return new MeterRegistryPostProcessor(meterBinders, applicationContext);
    }

    @Bean(name = "meterRegistry")
    public MeterRegistry prometheusMeterRegistry() {
        logger.info("constructing PrometheusMeterRegistry ...");
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, CollectorRegistry.defaultRegistry, Clock.SYSTEM);
    }

    @Bean(name = "metricsHelper")
    @DependsOn(value = "meterRegistry")
    public MetricsHelper metricsHelper(MeterRegistry registry) {
        logger.info("initialize MetricsHelper...");
        return new MetricsHelper(registry);
    }

    @Bean(name = "prometheusProperties")
    public PrometheusProperties prometheusProperties() {
        logger.info("constructing PrometheusProperties ...");

        PrometheusProperties prometheusProperties = new PrometheusProperties();
        prometheusProperties.getPushgateway().setEnabled(enable);
        prometheusProperties.getPushgateway().setBaseUrl(baseUrl);
        prometheusProperties.getPushgateway().setJob(job);
        prometheusProperties.getPushgateway().setGroupingKey(getGroupingKey());
        prometheusProperties.getPushgateway().setPushRate(pushRate);
        prometheusProperties.getPushgateway().setShutdownOperation(PrometheusPushGatewayManager.ShutdownOperation.getShutdownOperation(shutdownOperation));
        return prometheusProperties;
    }

    @Bean(name = "prometheusPushGatewayManager")
    @DependsOn(value = "prometheusProperties")
    public PrometheusPushGatewayManager prometheusPushGatewayManager(PrometheusProperties prometheusProperties, Environment environment) {
        logger.info("constructing PrometheusPushGatewayManager ...");
        PrometheusProperties.Pushgateway properties = prometheusProperties.getPushgateway();
        return new PrometheusPushGatewayManager(
                this.getPushGateway(properties.getBaseUrl())
                , CollectorRegistry.defaultRegistry
                , properties.getPushRate()
                , job
                , properties.getGroupingKey()
                , properties.getShutdownOperation());
    }

    @Bean
    @DependsOn(value = {"prometheusPushGatewayManager", "metricsHelper", "meterRegistryPostProcessor"})
    public String initialize() {
        logger.info("DefaultExports init...");
        DefaultExports.initialize();
        logger.info("DefaultExports has been initialized...");
        return "";
    }

    private Map<String, String> getGroupingKey() {
        Map<String, String> ret = new HashMap<>();
        ret.put("instance", NetUtils.getInstance());
        String tagarray[] = tags.split(",");
        for (String tag : tagarray) {
            if (! StringUtils.hasLength(tag)) {
                continue;
            }
            String kv[] = tag.trim().split("=");
            if (kv.length > 0) {
                ret.put(kv[0], kv.length > 1? kv[1] : "");
            }
        }
        return ret;
    }

    private PushGateway getPushGateway(String url) {
        try {
            return new PushGateway(new URL(url));
        } catch (Exception ex) {
            logger.warn("Invalid PushGateway base url '{}': update your configuration to a valid URL", url);
            return new PushGateway(url);
        }
    }
}
