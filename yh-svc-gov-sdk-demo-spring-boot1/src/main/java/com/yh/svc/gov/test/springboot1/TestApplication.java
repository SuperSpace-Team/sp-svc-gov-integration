package com.yh.svc.gov.test.springboot1;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.micrometer.spring.autoconfigure.JvmMetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.jdbc.DataSourcePoolMetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.kafka.consumer.KafkaMetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.web.client.RestTemplateMetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.web.servlet.WebMvcMetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.web.tomcat.TomcatMetricsAutoConfiguration;

@EnableScheduling

@MapperScan({"com.yh.svc.gov.test.springboot1.dao"})
@SpringBootApplication(scanBasePackages = { "com.yh.svc.gov.test", "com.yh.infra.svc.gov.consul"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableAutoConfiguration(exclude={
		TomcatMetricsAutoConfiguration.class,
		WebMvcMetricsAutoConfiguration.class,
		JvmMetricsAutoConfiguration.class,
		DataSourcePoolMetricsAutoConfiguration.class,
		KafkaMetricsAutoConfiguration.class,
		RestTemplateMetricsAutoConfiguration.class,
		MongoAutoConfiguration.class, 
		MongoDataAutoConfiguration.class
		})
public class TestApplication {
	private static final Logger logger = LoggerFactory.getLogger(TestApplication.class);
	
	public static void main(String[] args) {
        ClassLoader p = TestApplication.class.getClassLoader();
        while (p != null) {
        	ClassLoader p1 = p.getParent();
        	logger.info("demo-sb1. {} 's parent is {}", p, p1);
        	p = p1;
        }

        
		SpringApplication.run(TestApplication.class, args);
	}
}
