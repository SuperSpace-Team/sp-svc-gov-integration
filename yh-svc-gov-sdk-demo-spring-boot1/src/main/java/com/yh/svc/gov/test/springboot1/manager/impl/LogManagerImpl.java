package com.yh.svc.gov.test.springboot1.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yh.svc.gov.test.springboot1.manager.LogManager;

/**
 * @author luchao 2018-08-06
 */
@Service("logManager")
@Transactional
public class LogManagerImpl implements LogManager {
	private static final Logger logger = LoggerFactory.getLogger(LogManagerImpl.class);

	@Override
	public void throwException(String str) {
		internalAction(str);
	}
	private void internalAction(String str) {
		logger.info("str length: {}" , str.length());
		
	}

}
