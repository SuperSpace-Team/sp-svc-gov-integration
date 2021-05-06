package com.yh.infra.svc.gov.sdk.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AesAuthUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String estr = AesAuthUtil.encrypt("a1b2c3d4");
		String ostr = AesAuthUtil.decrypt(estr);
		
		assertEquals("a1b2c3d4", ostr);
	}

}
