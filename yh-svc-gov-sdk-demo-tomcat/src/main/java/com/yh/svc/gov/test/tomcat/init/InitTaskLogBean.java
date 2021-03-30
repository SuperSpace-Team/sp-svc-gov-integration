package com.yh.svc.gov.test.tomcat.init;

import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author luchao 2019-01-07
 *
 */
public class InitTaskLogBean implements InitializingBean {


//	@Autowired
//	PgClientLauncher client;

	@Override
	public void afterPropertiesSet() throws Exception {
//		client.init();
	}
}
