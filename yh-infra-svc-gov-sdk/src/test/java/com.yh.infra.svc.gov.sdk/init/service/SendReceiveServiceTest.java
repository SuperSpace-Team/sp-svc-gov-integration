
package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.apache.http.Header;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class SendReceiveServiceTest {
	
	SendReceiveService service;
	AppRegConfig cfg;
	AppRegContext ctx;
	
	@Mock
    HttpClientProxy httpClient;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		
		cfg = new AppRegConfig();
		cfg.setAppKey("TEST-APP");
		cfg.setAppSecret("12345678");
		cfg.setAppAuthUrl("http://uac");
		cfg.setSecretUrl("http://uac/secret");
		cfg.setGovPlatformUrl("pgserver");
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);
		
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		
		Map<String, String> respMap = new HashMap<String, String>();
		String respStr;
		
		AccessTokenCommand token = new AccessTokenCommand();
		

		AccountAuthReturnObj aaro = new AccountAuthReturnObj();
		aaro.setResultFlag(true);
		aaro.setData("TOKEN123");
		respStr = JsonUtil.writeValue(aaro);
		respMap.put("result", respStr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("http://uac/appmember/member/encrypt/code"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);
		
		respMap = new HashMap<String, String>();
		token = new AccessTokenCommand();
		token.setAccessToken("TOKEN123");
		token.setExpireTime(312312312l);
		respStr = JsonUtil.writeValue(token);
		aaro = new AccountAuthReturnObj();
		aaro.setResultFlag(true);
		aaro.setData(respStr);
		respStr = JsonUtil.writeValue(aaro);
		respMap.put("result", respStr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("http://app/login"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);
		
		
		respMap = new HashMap<String, String>();
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		respMap.put("result", "12345678");
		when(httpClient.postJson(eq("http://uac/secret"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);

		
		respMap = new HashMap<String, String>();
		respMap.put("result", "{\"isSuccess\": true}");
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("logurl"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);		
		
		
		respMap = new HashMap<String, String>();
		String retstr = JsonUtil.writeValue(TestVoUtil.voVersionQueryResp(1, 12, ""));
		respMap.put("result", retstr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("pgserver"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);

		
		service = new SendReceiveService(ctx, new UacService(ctx));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Send_VersionQueryReq_success() {
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("UT-APP1", "localhost", 12);
		
		VersionQueryResp resp = service.send(req);
		assertEquals(1, resp.getCode().intValue());
	}

}

