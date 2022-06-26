package com.sp.infra.svc.gov.sdk.testhelper;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoService2 {
	private Logger logger = LogManager.getLogger(DemoService2.class);

	public void testLog(String n) {
		logger.info("logger2 INFO msg: " + n);
		logger.error("logger2 ERROR msg: " + n);
	}

	public Logger getLogger() {
		return logger;
	}
	
}
