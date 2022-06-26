package com.sp.infra.svc.gov.sdk.init.service;

import com.sp.infra.svc.gov.sdk.auth.uac.UacService;
import com.sp.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 集成测试用。 直连实际的网关。 正常需要ignore
 * 
 * 
 * @author luchao  2019-02-24
 *
 */

@Ignore
public class SendReceiveServiceOpTest {
	
	SendReceiveService service;
	AppRegConfig cfg;
	AppRegContext ctx;
	HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
	UacService uacService;
	
	@Before
	public void setUp() throws Exception {
		cfg = new AppRegConfig();
		cfg.setAppKey("demo--sp-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		cfg.setGovPlatformUrl("http://localhost:8100/svc-gov/version/query");
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);

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
	public void test_Send_VersionQueryReq_need_relogin() {
		AccessTokenCommand token = new AccessTokenCommand();
		token.setExpireTime(System.currentTimeMillis() + 500000);
		token.setAccessToken("svc-gov-app-token-428630176021221376");
		
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("demo--sp-test-svc", "10.67.84.149", -1);
		req.setSdkVersion("1.0.0-SNAPSHOT");
		VersionQueryResp resp = service.send(req);
		assertEquals(1, resp.getCode().intValue());
	}
}
