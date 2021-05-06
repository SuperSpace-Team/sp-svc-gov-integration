
package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * 用mock server  测试  media 匹配问题。
 * 
 * @author luchao 2020-03-26
 *
 */
public class SendReceiveService2Test {
	
	SendReceiveService service;
	AppRegConfig cfg;
	AppRegContext ctx;
	
	private ClientAndServer mockServer;

	UacService uac;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockServer = ClientAndServer.startClientAndServer(1278);

		cfg = new AppRegConfig();
		cfg.setAppKey("TEST-APP");
		cfg.setAppSecret("12345678");
		cfg.setUacUrl("http://localhost:1278/uac");
		cfg.setGovPlatformUrl("http://localhost:1278/pg/version/query");
		cfg.setEnableVersionChecker(false);
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);
		
		HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		uac = new UacService(ctx);
		service = new SendReceiveService(ctx, uac);
		
		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setResultFlag(true);
		aaro.setData("CODE-1234");
		String aaroCodeJson = JsonUtil.writeValue(aaro);
		
		AccessTokenCommand token = new AccessTokenCommand();
		token.setAccessToken("TOKEN123");
		token.setExpireTime(312312312l);
		aaro.setData(JsonUtil.writeValue(token));
		String aaroTokenJson = JsonUtil.writeValue(aaro);

		
		VersionQueryResp resp = new VersionQueryResp();
		resp.setCode(4);
		
		MockServerClient msc = new MockServerClient("localhost", 1278);
		msc.when(
			request()
				.withMethod("POST")
				.withPath("/uac/appmember/member/encrypt/code")
		)
		.respond(
			response()
				.withStatusCode(HttpStatusCode.OK_200.code())
				.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
				.withBody(aaroCodeJson));
		msc.when(
				request()
					.withMethod("POST")
					.withPath("/uac/appmember/member/appLogin")
			)
			.respond(
				response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
					.withBody(aaroTokenJson));
		msc.when(
				request()
					.withMethod("POST")
					.withPath("/pg/version/query")
			)
			.respond(
				response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
					.withBody(JsonUtil.writeValue(resp)));

		
		service = new SendReceiveService(ctx, new UacService(ctx));
	}

	@After
	public void tearDown() throws Exception {
		mockServer.stop();
	}

	@Test
	public void test_Send_VersionQueryReq_success() {
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("UT-APP1", "localhost", 12);
		
		VersionQueryResp resp = service.send(req);
		assertNotNull(resp);
		assertEquals(4, resp.getCode().intValue());
	}

}

