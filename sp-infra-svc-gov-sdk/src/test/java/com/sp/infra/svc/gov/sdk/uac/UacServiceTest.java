package com.sp.infra.svc.gov.sdk.uac;

import com.sp.infra.svc.gov.sdk.auth.uac.UacService;
import com.sp.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.sp.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.command.RefreshToken;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class UacServiceTest {

	private ClientAndServer mockServer;
	MockServerClient client;
			
    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("=======================UacServiceTest.setUp");
		MockitoAnnotations.initMocks(this);

		mockServer = ClientAndServer.startClientAndServer(9081);
		
		client = new MockServerClient("localhost", 9081);

		AppRegConfig cfg;
		AppRegContext ctx;
		cfg = new AppRegConfig();
		cfg.setAppKey("demo--sp-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(11);
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(new UacService(ctx));
		
		HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
		sc.register(HttpClientProxy.class.getName(), httpClient);
		
		doReturn(true).when(mockLogger).isDebugEnabled();
		oriLogger = null;
	}

	@After
	public void tearDown() throws Exception {
		mockServer.stop();
		client.stop();
		client.close();
		
    	if (oriLogger != null)
            TestReflectionUtils.setStaticValue(UacService.class, "logger", oriLogger);
		System.out.println("=======================UacServiceTest.tearDown");
	}

	/**
	 * ?????????????????? ??????token???reset???
	 * 
	 */
	@Test
	public void test_getToken_normal() {
		//?????????????????????
		BaseResponseEntity res = new BaseResponseEntity();
		res.setData("428970405202628608");
		res.setCode("20000");
		res.setIsSuccess(true);
		String respJson = JsonUtil.writeValueSafe(res);
		prepareMockServer("/app/getAppAuthCode", respJson);
		
		//????????????Token
		AccessTokenCommand tokenCmd = new AccessTokenCommand();
		tokenCmd.setAccessToken("svc-gov-app-token-428630176021221376");
		tokenCmd.setExpireTime(System.currentTimeMillis() + 10000);
		respJson = JsonUtil.writeValueSafe(tokenCmd);
		res.setData(respJson);
		respJson = JsonUtil.writeValueSafe(res);
		prepareMockServer("/app/login", respJson);
		
		//????????????????????????
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        uacService.resetToken();

        // ??????
        String token = uacService.getAppToken();
        assertEquals("svc-gov-app-token-428630176021221376", token);
	}

	/**
	 * ????????????????????????token???reset?????????token???????????????????????????????????????????????????
	 * 
	 */
	@Test
	public void test_getToken_failed_because_getApoAuth_fail() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);

       	// ?????????????????????
		prepareMockServerFail("/app/getAppAuthCode", HttpStatusCode.INTERNAL_SERVER_ERROR_500.code());
		
		// ????????????????????????
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        uacService.resetToken();

        //??????
        String token = uacService.getAppToken();
        assertNull(token);
        verify(mockLogger, times(1)).warn(eq("result : {}"),any(Map.class));
	}

	
	/**
	 * token???????????????refresh
	 * 
	 */
	@Test
	public void test_getToken_refresh_expired() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);
		
		//?????????????????????
		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setData("428970405202628608");
		aaro.setErrorCode(0);
		aaro.setResultFlag(true);
		String respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/app/getAppAuthCode", respJson);
		
		//??????app token
		RefreshToken rt = new RefreshToken();
		rt.setExpireDate(System.currentTimeMillis() + 10000000);
		rt.setRefreshFlag(true);
		rt.setToken("svc-gov-app-token-428630176021221376");
		String rtJson = JsonUtil.writeValueSafe(rt);
		
		aaro = new AccountAuthReturnObj();
		aaro.setData(rtJson);
		aaro.setResultFlag(true);
		respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/app/refreshNewToken", respJson);

        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);

		AccessTokenCommand tokenCmd = new AccessTokenCommand();
		tokenCmd.setExpireTime(System.currentTimeMillis());
        tokenCmd.setAccessToken("svc-gov-app-token-428630176021221376");
		TestReflectionUtils.setValue(uacService, "appTokenRespInfo", tokenCmd);
		TestReflectionUtils.setValue(uacService, "appTokenStr", "svc-gov-app-token-428630176021221376");
		
        //??????
        String token = uacService.getAppToken();
        verify(mockLogger, times(1)).debug(startsWith("Begin to refresh token. reLoginFlag:"),any(), any());
        assertEquals("svc-gov-app-token-428630176021221376", token);
        
        tokenCmd = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "appTokenRespInfo");
        assertTrue(tokenCmd.getExpireTime() > System.currentTimeMillis());
	}
	
	/**
	 * ????????????????????? 60001???????????? int string ?????????????????????
	 * 
	 */
	@Test
	public void test_getToken_forced_to_relogin() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);
       	
		// uac service
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
		
		// ?????? token
		AccessTokenCommand uacToken = new AccessTokenCommand();
		uacToken.setAccessToken("svc-gov-app-token-428630176021221376");
		uacToken.setExpireTime(System.currentTimeMillis() + 60 * 60 * 1000);
		TestReflectionUtils.setValue(uacService, "uacToken", uacToken);
		TestReflectionUtils.setValue(uacService, "uacTokenStr", "token-12345");
		
		// ??????60001?????????
		Map<String, Object> resp = new HashMap<>();
		resp.put("error_code", 60001);
		String respJson = JsonUtil.writeValueSafe(resp);
		prepareMockServer("/oms/create", respJson, 500);

		
        //??????
		Map<String, String> retmap = uacService.sendRequestWithToken("http://localhost:9081/oms/create", "abc" , 10000);
		verify(mockLogger, times(1)).warn(eq("Received a response asking for re-login. {}, {}"),any(), any());
	}
	
	
	private void prepareMockServer(String url, String resp) {
		prepareMockServer(url, resp, 200);
	}
	private void prepareMockServer(String url, String resp, int errorCode) {
		client.when(request().withMethod("POST").withPath(url))
				.respond(response().withStatusCode(errorCode)
						.withHeaders(org.mockserver.model.Header.header("content-type", "application/json")
								,org.mockserver.model.Header.header("Connection", "close"))
						.withBody(resp));
	}
	private void prepareMockServerFail(String url, int errorCode) {
		client.when(request().withMethod("POST").withPath(url))
				.respond(response().withStatusCode(errorCode)
						.withHeaders(org.mockserver.model.Header.header("Connection", "close")));
	}
}
