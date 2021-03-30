package com.yh.svc.gov.test.springboot2;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.KafkaMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.jdbc.DataSourcePoolMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(
		scanBasePackages = "com.yh.svc.gov.test"	 ,
		exclude={
		TomcatMetricsAutoConfiguration.class,
		WebMvcMetricsAutoConfiguration.class,
		JvmMetricsAutoConfiguration.class,
		SystemMetricsAutoConfiguration.class,
		DataSourcePoolMetricsAutoConfiguration.class,
		KafkaMetricsAutoConfiguration.class
		})
public class DemoSpringBoot2Application {

	public static void main(String[] args) {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
		SpringApplication.run(DemoSpringBoot2Application.class, args);
	}

}
