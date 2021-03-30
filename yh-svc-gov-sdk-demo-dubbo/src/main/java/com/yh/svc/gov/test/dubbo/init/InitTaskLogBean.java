package com.yh.svc.gov.test.dubbo.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 */
public class InitTaskLogBean implements InitializingBean {

	protected static final Logger logger = LoggerFactory.getLogger(InitTaskLogBean.class);

		
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(" begin to init........");
	}

}
