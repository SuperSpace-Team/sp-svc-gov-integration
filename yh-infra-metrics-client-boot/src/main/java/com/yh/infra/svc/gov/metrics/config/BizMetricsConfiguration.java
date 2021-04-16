package com.yh.infra.svc.gov.metrics.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableConfigurationProperties(BootMetricsConfig.class)
@ComponentScan(basePackages = {
		"com.yh.infra.svc.gov.metrics"
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
}
