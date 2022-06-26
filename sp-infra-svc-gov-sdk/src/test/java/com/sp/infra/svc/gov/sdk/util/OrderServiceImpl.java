package com.sp.infra.svc.gov.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceImpl implements OrderService{
	public Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());
	
	public void createOrder(String orderCode, Integer count) {
		
	}
	public void test1(String orderCode, Integer count) {
		logger.debug("enter the OrderServiceImpl.test1");
	}
}
