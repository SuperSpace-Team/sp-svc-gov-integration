package com.sp.infra.svc.gov.metrics.config;

import com.sp.infra.svc.gov.metrics.meter.MetricsHelper;
import io.micrometer.core.instrument.Clock;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@EnableConfigurationProperties(BootMetricsConfig.class)
@ComponentScan(basePackages = {
		"com.sp.infra.svc.gov.metrics"
		})
@EnableAspectJAutoProxy
public class BizMetricsConfiguration {
	private final static Logger logger = LoggerFactory.getLogger(BizMetricsConfiguration.class);

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		logger.info("Initialized TimedAspect...");
		return new TimedAspect(registry);
	}

	@Bean
	public CountedAspect countedAspect(MeterRegistry registry) {
		return new CountedAspect(registry);
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
}
