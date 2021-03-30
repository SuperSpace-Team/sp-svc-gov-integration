package com.yh.svc.gov.test.dubbo.manager.impl;

import com.yh.svc.gov.test.dubbo.bean.level1.BeanLevel1;
import com.yh.svc.gov.test.dubbo.manager.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public String getOrder(String code, int size, List<String> codeList) {
		System.out.println("--" + code + " -- " + size);
		
		BeanLevel1.call1(code, size);
		return code;
	}

	@Override
	public void saveOrder(String code) {
		System.out.println("--" + code);
	}

	
//	@RetryRegistry(code = "strategy-dubbo", beanName="retryService", uuidExp = "#P1", bizKeyExp = "#P2", dbTagExp = "\"db1\"")
//	@RetryConfirm(uuidExp = "#P1", confirmExp = "! #RESULT", dbTagExp = "\"db1\"")
	public boolean createOrder(String uuid, String orderNo) {
//		logger.info("createOrder : {}, {}", uuid, orderNo);
//		if (uuid.startsWith("F"))
//			return false;
		return true;
	}
}
