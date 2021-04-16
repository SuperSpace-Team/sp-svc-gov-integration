/**
 * 
 */
package com.yh.infra.svc.gov.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 
 * @author luchao  2020-12-07
 *
 */
@ConfigurationProperties("yh.metrics")
public class BootMetricsConfig extends YhMetricsProperties {
}
