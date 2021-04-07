//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.yh.svc.gov.test.springboot1.manager.RetryManager;
//import com.yh.svc.gov.test.springboot1.model.OrderCancel;
//import com.yh.svc.gov.test.springboot1.utils.ThreadUtil;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryConfirm;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryRegistry;
//import com.yh.infra.svc.gov.sdk.retry.vo.RetryResponse;
//import com.yh.infra.svc.gov.sdk.util.JsonUtil;
//
//@Service("retryManager")retryManager
//public class RetryManagerImpl implements RetryManager{
//	private static final Logger logger = LoggerFactory.getLogger(RetryManagerImpl.class);
//
//	@RetryRegistry(code = "strategy_normal1", beanName="retryManager", uuidExp = "#P2", bizKeyExp = "#P3")
//	@RetryConfirm(uuidExp = "#P2", confirmExp = "! #RESULT")
//	public boolean normal(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
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
//	@RetryRegistry(code = "strategy_invalid1", beanName="retryManager", uuidExp = "#P2", bizKeyExp = "#P3")
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
//	@Override
//	@RetryRegistry(code = "strategy_same_method1", beanName="retryManager", uuidExp = "#P1", bizKeyExp = "#P2", defaultIntervalMinute=1)
//	@RetryConfirm(uuidExp = "#P1", confirmExp = "#RESULT.result != 1")
//	public RetryResponse retry(String uuid, String bizKey, Map<String, Object> parameters) {
//
//		List<OrderCancel> orderCancels = JsonUtil.objectToList(parameters.get("P5"), OrderCancel.class);
//
//		logger.info("retry start. {}, {}, {}", uuid, bizKey, parameters);
////		logger.info("orderCancels: {}", orderCancels.get(0).getBsOrderCode());
//		if (parameters.containsKey("P1")) {
//			// UUID用于retry时候的 结果 设定。
//			bizKey = uuid;
//		}
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
//	@RetryRegistry(code = "strategy_async1", beanName="retryManager", uuidExp = "#P2", bizKeyExp = "#P3", defaultIntervalMinute=1)
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
////	@RetryConfirm(uuidExp = "#P2", confirmExp = "! #RESULT")
////	public boolean asyncResp(int type, String uuid) throws InterruptedException {
////		logger.info("enter manager. {}", uuid);
////		switch (type) {
////			case 1:
////				Thread.sleep(1000);
////				return true;
////			case 2:
////				Thread.sleep(1000);
////				return false;
////			default:
////				throw new RuntimeException("retry exception");
////		}
////	}
//
//}
