package com.sp.infra.svc.gov.sdk.init;

import com.sp.infra.svc.gov.agent.agent.AgentBeanRegistry;
import com.sp.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.sp.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.sp.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.init.daemon.VersionChecker;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.sp.infra.svc.gov.sdk.util.ThreadUtil;
import org.apache.http.Header;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class AppRegLauncherTest {
	AppRegLauncher client = new AppRegLauncher();

    HttpClientProxy httpClient = new HttpClientProxyImpl();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		client = new AppRegLauncher();
		client.setAppKey("demo--sp-test-svc");
		client.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		client.setUnionGatewayUrl("http://localhost:8100");
		client.setHttpClientProxy(httpClient);
		client.setEnabled(true);
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(SdkCommonConstant.SDK_INITIALIZED_FLAG, false);
		sc.register(HttpClientProxy.class.getName(), httpClient);

		Map<String, String> respMap = new HashMap<String, String>();
		String respStr;

		BaseResponseEntity aaro = new BaseResponseEntity();
		aaro.setIsSuccess(true);
		aaro.setData("svc-gov-app-token-426161524495749120");
		respStr = JsonUtil.writeValue(aaro);
		respMap.put("result", "{\"resultFlag\":true,\"data\":\"TOKEN123\",\"errorMsg\":null,\"errorCode\":0} ");
		respMap.put("status", "200");
//		when(httpClient.postJson(eq("http://localhost:8100/svc-gov/app/getAppAuthCode"),
//				anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);

		AccessTokenCommand token = new AccessTokenCommand();
		token.setAccessToken("svc-gov-app-token-426161524495749120");
		token.setExpireTime(85652463L);
		respStr = JsonUtil.writeValue(token);

		aaro = new BaseResponseEntity();
		aaro.setIsSuccess(true);
		aaro.setData(respStr);
		respStr = JsonUtil.writeValue(aaro);

		respMap = new HashMap();
		respMap.put("result", respStr);
		respMap.put("status", "200");
//		when(httpClient.postJson(eq("http://localhost:8100/svc-gov/app/login"),
//				anyString(), anyInt(), any(Header[].class))).thenReturn(respMap);
		AgentBeanRegistry.register(new AgentInstallProcessor(null, "1.0.0-SNAPSHOT"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Init() {
		client.setMonitorEnabled(false);
		client.init();
		
		ThreadUtil.sleep(3000);
		BeanRegistry sc = BeanRegistry.getInstance();
		VersionChecker versionChecker = sc.getBean(VersionChecker.class);
		versionChecker.setExit();
		
		ExecutorService adminPool = (ExecutorService)TestReflectionUtils.getValue(client, "adminPool");
		try {
			adminPool.shutdown();
			adminPool.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
}
