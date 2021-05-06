package com.yh.infra.svc.gov.sdk.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class NetUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_GetExternalIp() {
		assertFalse(NetUtils.getExternalIp().startsWith("127"));
	}

}
