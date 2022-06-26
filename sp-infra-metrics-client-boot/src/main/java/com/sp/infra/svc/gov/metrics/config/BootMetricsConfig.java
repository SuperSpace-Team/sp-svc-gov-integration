package com.sp.infra.svc.gov.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 
 * @author luchao  2020-12-07
 *
 */
@ConfigurationProperties("sp.metrics")
public class BootMetricsConfig extends YhMetricsProperties {
}
