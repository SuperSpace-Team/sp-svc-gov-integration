/**
 * 
 */
package com.yh.svc.gov.test.dubbo.manager.impl;

import com.yh.svc.gov.test.dubbo.manager.MetricsService;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luchao  2020-12-07
 *
 */
@Service("metricsService")
public class MetricsServiceImpl implements MetricsService {
	private static final Logger logger = LoggerFactory.getLogger(MetricsServiceImpl.class);
	
	@Autowired
	PrometheusMeterRegistry registry;
	
	@Override
	public String get() {
		String ret = registry.scrape();
		logger.info("export prometheus metrics string.  {}", ret);
		return ret;
	}

}
