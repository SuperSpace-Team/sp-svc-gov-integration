//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.yh.svc.gov.test.springboot1.manager.RetryManager2;
//import com.yh.svc.gov.test.springboot1.utils.ThreadUtil;
//import com.yh.infra.svc.gov.sdk.retry.RetryService;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryConfirm;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryRegistry;
//import com.yh.infra.svc.gov.sdk.retry.vo.RetryResponse;
//
//@Service("retryManager2")
//public class RetryManager2Impl implements RetryService , RetryManager2 {
//	private static final Logger logger = LoggerFactory.getLogger(RetryManager2Impl.class);
//
//	@Override
//	public RetryResponse retry(String uuid, String bizKey, Map<String, Object> parameters) {
//		logger.info("retry start. {}, {}, {}", uuid, bizKey, parameters);
//
//		if (bizKey.startsWith("S")) {
//			ThreadUtil.sleep(1000);
//			return new RetryResponse(1, "E0", "success");
//		}
//
//		if (bizKey.startsWith("F")) {
//			ThreadUtil.sleep(1000);
//			return new RetryResponse(2, "E1", "fail");
//		}
//		if (bizKey.startsWith("C")) {
//			ThreadUtil.sleep(1000);
//			return new RetryResponse(3, "E2", "cancel");
//		}
//
//		if (bizKey.startsWith("E")) {
//			ThreadUtil.sleep(1000);
//			throw new RuntimeException("retry exception");
//		}
//		ThreadUtil.sleep(1000);
//		return new RetryResponse(444, "E3", "fail");
//	}
//
//	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"", timeoutSeconds = 10)
//	@RetryConfirm(uuidExp = "#P2", confirmExp = "! #RESULT", dbTagExp = "\"db1\"")
//	public boolean normal(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter manager. {}", bizKey);
//		long to = timeout * 1000;
//		switch (type) {
//			case 1:
//				Thread.sleep(to);
//				logger.info("exit manager. true");
//				return true;
//			case 2:
//				Thread.sleep(to);
//				logger.info("exit manager. false");
//				return false;
//			default:
//				throw new RuntimeException("retry exception");
//		}
//	}
//
//	@RetryRegistry(code = "strategy_invalid", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3")
//	@RetryConfirm(uuidExp = "#P2", confirmExp = "! #RESULT")
//	public boolean noStrategy(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter manager. {}", bizKey);
//		long to = timeout * 1000;
//		switch (type) {
//			case 1:
//				Thread.sleep(to);
//				return true;
//			case 2:
//				Thread.sleep(to);
//				return false;
//			default:
//				throw new RuntimeException("retry exception");
//		}
//	}
//
//
//	@RetryRegistry(code = "strategy_async", beanName="retryManager2", notifyBeanName = "notifyHandler", uuidExp = "#P2", bizKeyExp = "#P3", defaultIntervalMinute=1)
//	public boolean asyncReq(int type, String uuid, String bizKey) throws InterruptedException {
//		logger.info("enter manager. {}", bizKey);
//		switch (type) {
//			case 1:
//				Thread.sleep(1000);
//				return true;
//			case 2:
//				Thread.sleep(1000);
//				return false;
//			default:
//				throw new RuntimeException("retry exception");
//		}
//	}
//
//	@RetryConfirm(uuidExp = "#P2", confirmExp = "! #RESULT", srcAppKeyExp = "#P3")
//	public boolean asyncResp(int type, String uuid, String appKey) throws InterruptedException {
//		logger.info("enter manager. {}", uuid);
//		switch (type) {
//			case 1:
//				Thread.sleep(1000);
//				return true;  //  返回true， 所以confirmexp就是false， 就是不重试了。
//			case 2:
//				Thread.sleep(1000);
//				return false;
//			default:
//				throw new RuntimeException("retry exception");
//		}
//	}
//
//}
