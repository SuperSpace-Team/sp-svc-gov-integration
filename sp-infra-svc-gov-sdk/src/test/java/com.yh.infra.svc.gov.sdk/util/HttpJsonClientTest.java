package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.command.HttpClientResponse;
import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class HttpJsonClientTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Send_Log_local() throws Exception {
		HttpClientResponse result = HttpJsonClient.postJsonData("http://localhost:8876/monitor/log", "{\"name\": \"Davie\",\"sex\": \"m\",\"age\": \"18\"}", 5, null);
		System.out.println(result);
	}

	@Test
	public void test_Send_Log_local_gateway() throws Exception {
		HttpClientResponse result = HttpJsonClient.postJsonData("http://localhost:1205/demo/monitor/log", "{\"name\": \"Davie\",\"sex\": \"m\",\"age\": \"18\"}", 5, null);
		System.out.println(result);
	}

	@Test
	public void test_Send_Log() throws Exception {
		String log = "[{\"appId\":\"pg-app-demo1\",\"version\":18,\"bizCode\":101,\"monitorLogList\":[{\"code\":104,\"key\":\"a\",\"inLog\":null,\"outLog\":null,\"exceptionLog\":null},{\"code\":104,\"key\":\"b\",\"inLog\":null,\"outLog\":null,\"exceptionLog\":null},{\"code\":104,\"key\":\"c\",\"inLog\":null,\"outLog\":null,\"exceptionLog\":null},{\"code\":104,\"key\":\"d\",\"inLog\":null,\"outLog\":null,\"exceptionLog\":null},{\"code\":104,\"key\":\"e\",\"inLog\":null,\"outLog\":null,\"exceptionLog\":null}],\"transformLogList\":null,\"timeStamp\":1547532974310,\"elaspedTime\":8943,\"threadName\":\"http-nio-9001-exec-9\",\"hostName\":\"BZF9NK5K21\"}]";
		HttpClientResponse result = HttpJsonClient.postJsonData("http://10.45.91.143:2510/monitorData",log, 5, null);
		System.out.println(result);
	}
	
	
}
