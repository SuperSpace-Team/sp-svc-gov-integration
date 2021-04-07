//package com.yh.svc.gov.test.springboot1.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yh.svc.gov.test.springboot1.manager.RetryManager;
//import com.yh.infra.svc.gov.sdk.retry.vo.RetryResponse;
//
//@RestController
//@RequestMapping("/retry")
//public class RetryController {
//	private static final Logger logger = LoggerFactory.getLogger(RetryController.class);
//
//	@Autowired
//	private RetryManager retryManager;
//
//	@GetMapping("/normal")
//	public boolean normal(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey,
//			@RequestParam(value="timeout")int timeout) throws InterruptedException {
//		logger.info("normal {}, {}, {}, {}, {}", type, uuid, bizKey, timeout);
//		return retryManager.normal(type, uuid, bizKey, timeout);
//	}
//	@GetMapping("/no-strategy")
//	public boolean noStrategy(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey, @RequestParam(value="timeout")int timeout) throws InterruptedException {
//		logger.info("noStrategy {}, {}, {}, {}", type, uuid, bizKey, timeout);
//		return retryManager.noStrategy(type, uuid, bizKey, timeout);
//	}
//	@GetMapping("/samecall")
//	public boolean samecall(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey) throws InterruptedException {
//		logger.info("samecall {}, {}, {}, {}", type, uuid, bizKey);
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("datakey", "dataValue");
//		RetryResponse rp = retryManager.retry(uuid, bizKey, parameters);
//		return rp.getResult() == 1;
//	}
//
//	@GetMapping("/async-req")
//	public boolean asyncReq(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid, @RequestParam(value="bizKey")String bizKey) throws InterruptedException {
//		logger.info("asyncReq {}, {}, {}", type, uuid, bizKey);
//		return retryManager.asyncReq(type, uuid, bizKey);
//	}
//
////	@GetMapping("/async-resp")
////	public boolean asyncResp(@RequestParam(value="type")int type, @RequestParam(value="uuid")String uuid) throws InterruptedException {
////		logger.info("asyncResp {}, {}", type, uuid);
////		Map<String, Object> parameters = new HashMap<>();
////		paramameters.put("datakey", "dataValue");
////		return retryManager.asyncResp(type, uuid);
////	}
//}
