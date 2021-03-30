package com.yh.svc.gov.test.dubbo.manager;

import java.util.List;

public interface OrderService {

	public String getOrder(String code, int size, List<String> codeList);

	public void saveOrder(String code);
	
	public boolean createOrder(String uuid, String orderNo);
}
