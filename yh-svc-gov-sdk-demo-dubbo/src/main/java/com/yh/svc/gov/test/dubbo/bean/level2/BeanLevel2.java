package com.yh.svc.gov.test.dubbo.bean.level2;


import com.yh.svc.gov.test.dubbo.bean.level3.BeanLevel3;

public class BeanLevel2 {
	public void call2(String code, int size) {
		System.out.println("enter bean level2 " + code + " " + size);
		BeanLevel3 b = new BeanLevel3();
		b.call3(code + "=b3", size +2);
	}
}
