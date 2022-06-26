package com.sp.infra.svc.gov.sdk.net;

import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class HttpClientProxyImplTest {

	HttpClientProxy proxy = new HttpClientProxyImpl();
	
	private ClientAndServer mockServer;

	@Before
	public void setUp() throws Exception {
		mockServer = ClientAndServer.startClientAndServer(1278);
	}

	@After
	public void tearDown() throws Exception {
		mockServer.stop();
		Thread.sleep(1000);
	}
	
	
	@Test
	public void test_postJson() {
		Header headers[] = new Header[1];
		headers[0] = new BasicHeader("Connection", "close");
		
		new MockServerClient("localhost", 1278)
			.when(
				request()
					.withMethod("POST")
					.withPath("/json")
					.withBody("dummy message")
			)
			.respond(
				response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
					.withBody("{\"result\": \"success\"}"));
		
		Map<String, String> ret = proxy.postJson("http://localhost:8100/svc-gov/json", "dummy message", 10, headers);
		assertEquals("200", ret.get("status"));
		assertNull(ret.get("error"));
		assertEquals("{\"result\": \"success\"}", ret.get("result"));
	}
	
//	@Ignore
	@Test
	public void test_postJson_error() {
		Map<String, String> ret = proxy.postJson("ddd://localhost:1278/json", "dummy message", 10, null);
		assertNull(ret.get("status"));
		assertEquals("ddd protocol is not supported", ret.get("error"));
	}
	
//	@Ignore
	@Test
	public void test_postJson_with_customized_header() {
		Header headers[] = new Header[2];
		headers[1] = new BasicHeader("TOKEN", "123456");
		headers[0] = new BasicHeader("Connection", "close");
		
		new MockServerClient("localhost", 1278)
			.when(
				request()
					.withMethod("POST")
					.withPath("/header")
					.withHeader(new org.mockserver.model.Header("TOKEN", "123456"))
					.withBody("dummy message")
			)
			.respond(
				response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
					.withBody("received token"));
		
		
		Map<String, String> ret = proxy.postJson("http://localhost:1278/header", "dummy message", 10, headers);
		assertEquals("200", ret.get("status"));
		assertNull(ret.get("error"));
		assertEquals("received token", ret.get("result"));
	}
}
