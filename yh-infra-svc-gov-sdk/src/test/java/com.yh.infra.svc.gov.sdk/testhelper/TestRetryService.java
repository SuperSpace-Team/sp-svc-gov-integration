///**
// *
// */
//package com.yh.infra.svc.gov.sdk.testhelper;
//
//import com.yh.infra.svc.gov.sdk.constant.Constants;
//import com.yh.infra.svc.gov.sdk.exception.BusinessException;
//import com.yh.infra.svc.gov.sdk.retry.RetryService;
//import com.yh.infra.svc.gov.sdk.retry.vo.RetryResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
///**
// * @author luchao  2020-04-22
// *
// */
//public class TestRetryService implements RetryService {
//	private static final Logger logger = LoggerFactory.getLogger(TestRetryService.class);
//	@Override
//	public RetryResponse retry(String uuid, String bizKey, Map<String, Object> parameters) {
//		if (uuid.startsWith("exception"))
//			throw new BusinessException("haha. unit test.");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		logger.info("received uuid: {}", uuid);
//		return new RetryResponse(Constants.RETRY_RESULT_SUCCESS, "", "");
//	}
//
//}
