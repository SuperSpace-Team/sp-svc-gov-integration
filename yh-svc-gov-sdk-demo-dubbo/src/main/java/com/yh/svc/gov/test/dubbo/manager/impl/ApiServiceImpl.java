/**
 * 
 */
package com.yh.svc.gov.test.dubbo.manager.impl;

import com.yh.svc.gov.test.dubbo.manager.ApiService;
import com.yh.svc.gov.test.dubbo.manager.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luchao  2020-07-22
 *
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {
	private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Autowired
	private OrderService orderService;
	
	
	@Override
	public boolean callBoolean(int type, String uuid, String ordercode, int size) {
		logger.info("enter ApiServiceImpl.callBoolean.  {},{},{},{}",type, uuid, ordercode, size);
		if (type == 1)
			orderService.createOrder(uuid, ordercode);
		return false;
	}

}
