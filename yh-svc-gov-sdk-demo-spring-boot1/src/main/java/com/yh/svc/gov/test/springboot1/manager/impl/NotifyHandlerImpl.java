//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.yh.infra.svc.gov.sdk.retry.RetryNotifyHandler;
//
//@Component("notifyHandler")
//public class NotifyHandlerImpl implements RetryNotifyHandler {
//	private static final Logger logger = LoggerFactory.getLogger(NotifyHandlerImpl.class);
//
//	@Override
//	public void end(String uuid, String bizKey, Map<String, Object> parameters, int status) {
//		logger.info("final status noted!! {}, {}, {}", uuid, bizKey, status);
//	}
//
//}
