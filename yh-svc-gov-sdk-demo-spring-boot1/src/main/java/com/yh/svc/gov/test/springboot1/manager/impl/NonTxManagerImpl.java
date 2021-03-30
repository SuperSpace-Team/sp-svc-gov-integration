package com.yh.svc.gov.test.springboot1.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yh.infra.svc.gov.sdk.retry.annotation.RetryRegistry;

/**
 * @author luchao 2018-08-06
 */
@Service("nonTxManagerImpl")
public class NonTxManagerImpl  {
    private static final Logger logger = LoggerFactory.getLogger(NonTxManagerImpl.class);

	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"")
	public boolean testNonTx(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
        logger.info("NonTxManagerImpl service call : {}, {}, {}", type, uuid, bizKey);
    	if (type == 3) {
    		throw new RuntimeException("test testNonTx");
    	}
    	
		logger.info("finished NonTxManagerImpl  testNonTx  {}, {}, {}, {}", type, uuid, bizKey, timeout);
    	return true;
    }

}
