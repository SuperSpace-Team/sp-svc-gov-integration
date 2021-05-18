package com.yh.infra.svc.gov.sdk.uac;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.command.RefreshToken;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
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
		cfg.setAppKey("TEST-APP");
		cfg.setAppSecret("12345678");
		cfg.setAppAuthUrl("http://localhost:9081");
		
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
	 * 第一次登录， 或者token被reset。
	 * 
	 */
	@Test
	public void test_getToken_normal() {
		
		// 准备 ramdom code
		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setData("ramdom-code-12345");
		aaro.setErrorCode(0);
		aaro.setResultFlag(true);
		String respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/appmember/member/encrypt/code", respJson);
		
		// 准备 token
		AccessTokenCommand tokenCmd = new AccessTokenCommand();
		tokenCmd.setAccessToken("token-12345");
		tokenCmd.setExpireTime(System.currentTimeMillis() + 10000);
		respJson = JsonUtil.writeValueSafe(tokenCmd);
		aaro.setData(respJson);
		respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/app/login", respJson);
		
		// uac service
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        uacService.resetToken();

        // 执行
        String token = uacService.getAppToken();
        
        assertEquals("token-12345", token);
	}

	/**
	 * 第一次登录， 或者token被reset。 取得token失败，失败原因是getRandom失败
	 * 
	 */
	@Test
	public void test_getToken_failed_because_getRamdom_fail() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);

       	// 准备 ramdom code
		prepareMockServerFail("/appmember/member/encrypt/code", HttpStatusCode.INTERNAL_SERVER_ERROR_500.code());
		
		// uac service
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        uacService.resetToken();

        // 执行
        String token = uacService.getAppToken();
        assertNull(token);
        verify(mockLogger, times(1)).warn(eq("result : {}"),any(Map.class));
	}

	
	/**
	 * token超时，强制refresh
	 * 
	 */
	@Test
	public void test_getToken_refresh_expired() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);
		
		// 准备 ramdom code
		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setData("ramdom-code-12345");
		aaro.setErrorCode(0);
		aaro.setResultFlag(true);
		String respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/appmember/member/encrypt/code", respJson);
		
		// 准备 token
		RefreshToken rt = new RefreshToken();
		rt.setExpireDate(System.currentTimeMillis() + 10000000);
		rt.setRefreshFlag(true);
		rt.setToken("new-token-12345");
		String rtJson = JsonUtil.writeValueSafe(rt);
		
		aaro = new AccountAuthReturnObj();
		aaro.setData(rtJson);
		aaro.setResultFlag(true);
		respJson = JsonUtil.writeValueSafe(aaro);
		prepareMockServer("/appmember/member/refreshNewToken", respJson);

        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);

		AccessTokenCommand tokenCmd = new AccessTokenCommand();
		tokenCmd.setExpireTime(System.currentTimeMillis());
        tokenCmd.setAccessToken("old-token-12345");
		TestReflectionUtils.setValue(uacService, "uacToken", tokenCmd);
		TestReflectionUtils.setValue(uacService, "uacTokenStr", "old-token-12345");
		
        // 执行
        String token = uacService.getAppToken();
        verify(mockLogger, times(1)).debug(startsWith("begin to refresh token. reLoginFlag:"),any(), any());
        assertEquals("new-token-12345", token);
        
        tokenCmd = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "uacToken");
        assertTrue(tokenCmd.getExpireTime() > System.currentTimeMillis());
	}
	
	/**
	 * 强制重新登录。 60001错误码， int string 两种格式都测试
	 * 
	 */
	@Test
	public void test_getToken_forced_to_relogin() {
       	oriLogger = TestReflectionUtils.setStaticValue(UacService.class, "logger", mockLogger);
       	
		// uac service
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
		
		// 准备 token
		AccessTokenCommand uacToken = new AccessTokenCommand();
		uacToken.setAccessToken("token-12345");
		uacToken.setExpireTime(System.currentTimeMillis() + 60 * 60 * 1000);
		TestReflectionUtils.setValue(uacService, "uacToken", uacToken);
		TestReflectionUtils.setValue(uacService, "uacTokenStr", "token-12345");
		
		// 准备60001返回值
		Map<String, Object> resp = new HashMap<>();
		resp.put("error_code", 60001);
		String respJson = JsonUtil.writeValueSafe(resp);
		prepareMockServer("/oms/create", respJson, 500);

		
        // 执行
		Map<String, String>  retmap = uacService.sendRequestWithToken("http://localhost:9081/oms/create", "abc" , 10000);
		verify(mockLogger, times(1)).warn(eq("received a response asking for relogin. {}, {}"),any(), any());
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
