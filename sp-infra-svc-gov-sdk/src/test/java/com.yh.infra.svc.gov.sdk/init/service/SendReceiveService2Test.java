
package com.sp.infra.svc.gov.sdk.init.service;

import com.sp.infra.svc.gov.sdk.auth.uac.UacService;
import com.sp.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.sp.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
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
		cfg.setAppKey("demo--sp-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		cfg.setGovPlatformUrl("http://localhost:8100/svc-gov/version/query");
		cfg.setEnableVersionChecker(false);
		cfg.setSdkVersion("1.0.0-SNAPSHOT");

		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);
		
		HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		uac = new UacService(ctx);
		service = new SendReceiveService(ctx, uac);
		
		BaseResponseEntity aaro = new BaseResponseEntity();
		aaro.setIsSuccess(true);
		aaro.setData("428704779334717440");
		String aaroCodeJson = JsonUtil.writeValue(aaro);
		
		AccessTokenCommand token = new AccessTokenCommand();
		token.setAccessToken("svc-gov-app-token-428630176021221376");
		token.setExpireTime(68548517L);
		aaro.setData(JsonUtil.writeValue(token));
		String aaroTokenJson = JsonUtil.writeValue(aaro);

		
		VersionQueryResp resp = new VersionQueryResp();
		resp.setCode(4);
		
		MockServerClient msc = new MockServerClient("localhost", 1278);
		msc.when(
			request()
				.withMethod("POST")
				.withPath("/app/getAppAuthCode")
		)
		.respond(
			response()
				.withStatusCode(HttpStatusCode.OK_200.code())
				.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
				.withBody(aaroCodeJson));
		msc.when(
				request()
					.withMethod("POST")
					.withPath("/app/login")
			)
			.respond(
				response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
					.withBody(aaroTokenJson));
		msc.when(
				request()
					.withMethod("POST")
					.withPath("/svc-gov/version/query")
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
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("demo--sp-test-svc", "localhost", 12);
		req.setSdkVersion("1.0.0-SNAPSHOT");

		VersionQueryResp resp = service.send(req);
		assertNotNull(resp);
		assertEquals(1, resp.getCode().intValue());
	}

}

