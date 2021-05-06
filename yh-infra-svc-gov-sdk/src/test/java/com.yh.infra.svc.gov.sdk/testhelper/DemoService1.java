package com.yh.infra.svc.gov.sdk.testhelper;

import org.apache.log4j.Logger;

public class DemoService1 {
	private Logger logger = Logger.getLogger(DemoService1.class);

	public void testLog(String n) {
		if (logger.isInfoEnabled())
			logger.info("logger INFO msg: " + n);
		logger.error("logger ERROR msg: " + n);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	
}
