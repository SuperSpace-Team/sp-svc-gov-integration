package com.sp.infra.svc.gov.sdk.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberUtilTest {

	@Test
	public void test_getFibonacciInterval() {
		assertEquals(15, NumberUtil.getFibonacciInterval(1, 15));
		assertEquals(15, NumberUtil.getFibonacciInterval(2, 15));
		assertEquals(30, NumberUtil.getFibonacciInterval(3, 15));
	}

}
