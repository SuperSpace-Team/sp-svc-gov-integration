package com.yh.svc.gov.test.dubbo.bean.level1;


import com.yh.svc.gov.test.dubbo.bean.level2.BeanLevel2;

public class BeanLevel1 {
	public static void call1(String code, int size) {
		System.out.println("enter bean level1 " + code + " " + size);
		BeanLevel2 b1 = new BeanLevel2();
		b1.call2(code, size);
		
		BeanLevel2 b2 = new BeanLevel2();
		b2.call2(code + "-2", size+1);
		
	}
}
