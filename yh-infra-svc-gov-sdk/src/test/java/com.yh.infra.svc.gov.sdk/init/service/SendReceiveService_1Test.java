
package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.apache.http.Header;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * 特殊情况测试
 * 
 * 
 * @author luchao  2019-02-24
 *
 */
public class SendReceiveService_1Test {
	
	SendReceiveService service;
	AppRegConfig cfg;
	AppRegContext ctx;
	

	@Spy
	HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
	
	UacService uacService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		cfg = new AppRegConfig();
		cfg.setAppKey("demo--yh-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		cfg.setGovPlatformUrl("http://localhost:8100/svc-gov/version/query");
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(11);
		
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		uacService = new UacService(ctx);
		
		service = new SendReceiveService(ctx, uacService);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Send_VersionQueryReq_need_refresh() {
		AccessTokenCommand tokenTargetInfo = new AccessTokenCommand();
		tokenTargetInfo.setExpireTime(100L);
		tokenTargetInfo.setAccessToken("svc-gov-app-token-428630176021221376");
		
		TestReflectionUtils.setValue(uacService, "appTokenRespInfo", tokenTargetInfo);
		TestReflectionUtils.setValue(uacService, "appTokenStr", "svc-gov-app-token-428630176021221376");
		
		BaseResponseEntity res = new BaseResponseEntity();
		res.setIsSuccess(true);
		res.setData("true");

		String tmp = JsonUtil.writeValue(res);
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("result", tmp);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		doReturn(respMap).when(httpClient).postJson(eq("http://localhost:8100/svc-gov/app/refreshNewToken"), anyString(), anyInt(), any(Header[].class));
		
		respMap = new HashMap<String, String>();
		String retstr = JsonUtil.writeValue(TestVoUtil.voVersionQueryResp(1, 12, ""));
		respMap.put("result", retstr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		doReturn(respMap).when(httpClient).postJson(eq("http://localhost:8100/svc-gov"), anyString(), anyInt(), any(Header[].class));
		
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("demo--yh-test-svc", "localhost", 12);
		VersionQueryResp resp = service.send(req);
		assertEquals(1, resp.getCode().intValue());
		
		tmp = (String)TestReflectionUtils.getValue(uacService, "appTokenStr");
		assertEquals("svc-gov-app-token-428630176021221376", tmp);
		verify(httpClient,times(1)).postJson(eq("http://localhost:8100/svc-gov/app/refreshNewToken"), anyString(), anyInt(), any(Header[].class));
	}

	
	@Test
	public void test_Send_VersionQueryReq_refresh_fail_AND_relogin_fail() {
		
		// 设置 refresh拿到的结果， 是false
		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setResultFlag(false);
		aaro.setData("true");
		String tmp = JsonUtil.writeValue(aaro);
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("result", tmp);
		respMap.put("status", "200");
		doReturn(respMap).when(httpClient).postJson(eq("http://localhost:8100/svc-gov/app/refreshNewToken"), anyString(), anyInt(), any(Header[].class));

		
		// 设置getAppAuthCode的返回值， false。
		aaro = new AccountAuthReturnObj();
		aaro.setResultFlag(false);
		tmp = JsonUtil.writeValue(aaro);
		respMap = new HashMap<String, String>();
		respMap.put("result", tmp);
		respMap.put("status", "200");
		doReturn(respMap).when(httpClient).postJson(eq("http://localhost:8100/svc-gov/app/getAppAuthCode"), anyString(), anyInt(), any(Header[].class));
		
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("demo--yh-test-svc", "localhost", 12);
		VersionQueryResp resp = service.send(req);
		assertNull(resp);
		assertNull(uacService.getAppToken());
		verify(httpClient,times(0)).postJson(eq("http://localhost:8100/svc-gov"), anyString(), anyInt(), any(Header[].class));
	}
	
}
