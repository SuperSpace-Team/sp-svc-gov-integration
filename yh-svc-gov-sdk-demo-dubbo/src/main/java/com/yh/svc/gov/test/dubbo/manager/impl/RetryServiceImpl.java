///**
// *
// */
//package com.yh.svc.gov.test.dubbo.manager.impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * @author luchao  2020-07-22
// *
// */
//@Service("retryService")
//public class RetryServiceImpl implements RetryService {
//	private static final Logger logger = LoggerFactory.getLogger(RetryServiceImpl.class);
//
//	@Override
//	public RetryResponse retry(String uuid, String bizKey, Map<String, Object> parameters) {
//		logger.info("retry : {}, {}", uuid, bizKey);
//
//		int ret = 1;
//		if (bizKey.startsWith("S"))
//			ret = 1;
//		else
//			ret = 2;
//
//
//		return new RetryResponse(ret, "RET-1", "msg 1");
//	}
//
//
//}
