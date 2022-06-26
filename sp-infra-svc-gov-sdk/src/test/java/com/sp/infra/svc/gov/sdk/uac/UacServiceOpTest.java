package com.sp.infra.svc.gov.sdk.uac;

import com.sp.infra.svc.gov.sdk.auth.uac.UacService;
import com.sp.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;


/**
 * 与UAC做集成测试使用。
 * 
 * 
 * @author luchao 2019-10-21
 *
 */
@Ignore
public class UacServiceOpTest {


	@Before
	public void setUp() throws Exception {
		AppRegConfig cfg;
		AppRegContext ctx;
		cfg = new AppRegConfig();
		cfg.setAppKey("demo--sp-test-svc");
		cfg.setAppSecret("rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc");
		cfg.setAppAuthUrl("http://localhost:8100/svc-gov/app");
		ctx = new AppRegContext(cfg);
		
		BeanRegistry sc = BeanRegistry.getInstance();
		sc.register(ctx);
		sc.register(new UacService(ctx));
		
		HttpClientProxyImpl httpClient = new HttpClientProxyImpl();
		sc.register(HttpClientProxy.class.getName(), httpClient);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_login() {
        BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);

        //执行登录或刷新app token
        String token = uacService.getAppToken();
        AccessTokenCommand tokenCmd0 = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "appTokenRespInfo");
        System.out.println("Get token after login: " + tokenCmd0);

        removeRedis(token);
	}
	
	@Test
	public void test_refresh() {
		String token = "svc-gov-app-token-428630176021221376";
		removeRedis(token);
		BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        
		AccessTokenCommand tokenCmd0 = new AccessTokenCommand();
		tokenCmd0.setExpireTime(System.currentTimeMillis());
		tokenCmd0.setAccessToken(token);
		TestReflectionUtils.setValue(uacService, "appTokenRespInfo", tokenCmd0);

        token = uacService.getAppToken();
        
        AccessTokenCommand tokenCmd1 = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "uacToken");
        
        System.out.println("Get token aftre refresh, cmd0 : " + tokenCmd0);
        System.out.println("Get token aftre refresh, cmd1 : " + tokenCmd1);
	}

	private void removeRedis(String token) {
		String key="demo--sp-test-svc-" + "rPuKYUvnb6xYGSqXOzhwd7IDU1WaeKQc";

		Set<HostAndPort> nodes = new HashSet();
		nodes.add(new HostAndPort("10.251.76.39", 7001));
		nodes.add(new HostAndPort("10.251.76.21", 7002));
		nodes.add(new HostAndPort("10.251.76.22", 7003));
		nodes.add(new HostAndPort("10.251.76.39", 7004));
		nodes.add(new HostAndPort("10.251.76.21", 7005));
		nodes.add(new HostAndPort("10.251.76.22", 7006));

		JedisCluster jedisCluster = new JedisCluster(nodes);
		long ret = jedisCluster.del(key);
		if (ret == 0) {
			System.out.println("NO token key deleted.");
			return;
		}

		key = "base-svc-gov-dev_svc-gov-app-token-428630176021221376";
		ret = jedisCluster.del(key);
		if (ret == 0) {
			System.out.println("NO app key deleted.");
			return;
		}
	}
}
