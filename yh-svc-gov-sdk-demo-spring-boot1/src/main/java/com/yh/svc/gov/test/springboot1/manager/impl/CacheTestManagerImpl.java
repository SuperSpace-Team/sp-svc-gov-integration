package com.yh.svc.gov.test.springboot1.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author luchao 2018-08-06
 */
@Service("cacheTestManager")
public class CacheTestManagerImpl {
	private static final Logger logger = LoggerFactory.getLogger(CacheTestManagerImpl.class);

	@Cacheable(value="checkTenantAuth")
	public String visit(String str, String url) {
		logger.info("received request {}, {}", str, url);
		return System.currentTimeMillis() + "";
	}
}
