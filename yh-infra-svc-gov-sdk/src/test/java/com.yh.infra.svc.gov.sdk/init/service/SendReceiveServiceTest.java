
package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
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

    HttpClientProxy httpClient;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		cfg = new AppRegConfig();
		cfg.setAppKey("demo--yh-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		cfg.setGovPlatformUrl("http://localhost:8100/svc-gov/version/query");
		cfg.setSdkVersion("1.0.0-SNAPSHOT");

		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);

		httpClient = new HttpClientProxyImpl();
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		
		Map<String, String> respMap = new HashMap<String, String>();
		String respStr;
		
		AccessTokenCommand token;
		BaseResponseEntity baseResponseEntity = new BaseResponseEntity();
		baseResponseEntity.setIsSuccess(true);
		baseResponseEntity.setData("svc-gov-app-token-428630176021221376");
		respStr = JsonUtil.writeValue(baseResponseEntity);
		respMap.put("result", respStr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("http://localhost:8100/svc-gov/app/getAppAuthCode"), anyString(),
				anyInt(), any(Header[].class))).thenReturn(respMap);
		
		respMap = new HashMap<String, String>();
		token = new AccessTokenCommand();
		token.setAccessToken("svc-gov-app-token-428630176021221376");
		token.setExpireTime(312312312l);
		respStr = JsonUtil.writeValue(token);
		baseResponseEntity = new BaseResponseEntity();
		baseResponseEntity.setIsSuccess(true);
		baseResponseEntity.setData(respStr);
		respStr = JsonUtil.writeValue(baseResponseEntity);
		respMap.put("result", respStr);
		respMap.put("status", SdkCommonConstant.HTTP_STATUS_OK);
		when(httpClient.postJson(eq("http://localhost:8100/svc-gov/app/login"), anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);

		service = new SendReceiveService(ctx, new UacService(ctx));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Send_VersionQueryReq_success() {
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("demo--yh-test-svc", "localhost", 12);
		VersionQueryResp resp = service.send(req);
		assertEquals(1, resp.getCode().intValue());
	}
}

