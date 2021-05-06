package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 集成测试用。 直连实际的网关。 正常需要ignore
 * 
 * 
 * @author 钱超  2019-02-24
 *
 */

@Ignore
public class SendReceiveServiceOpTest {
	
	SendReceiveService service;
	AppRegConfig cfg;
	AppRegContext ctx;
	HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
	
	@Before
	public void setUp() throws Exception {
		cfg = new AppRegConfig();
		cfg.setAppKey("PAY_SERVICE1");
		cfg.setAppSecret("12345678");
		cfg.setUacUrl("http://10.101.6.66:1205/api");
		cfg.setSecretUrl("http://10.101.6.66:1205/api");
		cfg.setGovPlatformUrl("http://10.101.6.66:1205/api/pg/version/query");
		
		ctx = new AppRegContext(cfg);
		ctx.setCurrentVersion(22);
		
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(HttpClientProxy.class.getName(), httpClient);
		
		service = new SendReceiveService(ctx, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_Send_VersionQueryReq_need_relogin() {
		
		AccessTokenCommand token = new AccessTokenCommand();
		token.setExpireTime(System.currentTimeMillis() + 500000);
		token.setAccessToken("A-12b25fe3");
		
		
		VersionQueryReq req = TestVoUtil.voVersionQueryReq("PAY_SERVICE", "10.45.71.18", -1);
		VersionQueryResp resp = service.send(req);
		assertEquals(1, resp.getCode().intValue());
	}

	
}
