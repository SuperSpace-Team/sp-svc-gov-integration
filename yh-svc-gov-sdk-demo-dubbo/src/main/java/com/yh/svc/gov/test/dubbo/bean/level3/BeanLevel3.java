package com.yh.svc.gov.test.dubbo.bean.level3;

public class BeanLevel3 {
	public String call3(String code, int size) {
		System.out.println("enter bean level3 " + code + " " + size);
		
		return code;
	}
}
