package com.yh.svc.gov.test.springboot2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:spring/spring-demo.xml"})
public class SpringConfig {
}
