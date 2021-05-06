package com.yh.infra.svc.gov.sdk.uac;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.Jedis;
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
		cfg.setAppKey("pg-app-demo1");
		cfg.setAppSecret("12345678");
		cfg.setUacUrl("http://uat-api-base.yonghui.cn/api");
		
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

        // 执行
        String token = uacService.getToken();
        AccessTokenCommand tokenCmd0 = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "uacToken");

        System.out.println("get token after login: " + tokenCmd0);

        removeRedis(token);
	}
	
	@Test
	public void test_refresh() {
		String token = "A-64255s";
		removeRedis(token);
		BeanRegistry br = BeanRegistry.getInstance();
        UacService uacService = br.getBean(UacService.class);
        
		AccessTokenCommand tokenCmd0 = new AccessTokenCommand();
		tokenCmd0.setExpireTime(System.currentTimeMillis());
		tokenCmd0.setAccessToken(token);
		TestReflectionUtils.setValue(uacService, "uacToken", tokenCmd0);

        token = uacService.getToken();
        
        AccessTokenCommand tokenCmd1 = (AccessTokenCommand)TestReflectionUtils.getValue(uacService, "uacToken");
        
        System.out.println("get token aftre refresh, cmd0 : " + tokenCmd0);
        System.out.println("get token aftre refresh, cmd1 : " + tokenCmd1);
	}

	private void removeRedis(String token) {
		String key="cus-web-uat_" + token;
		
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("ecs-uat-redis-01.yonghui.cn:27000");
		sentinels.add("ecs-uat-redis-02.yonghui.cn:27000");
		sentinels.add("ecs-uat-redis-03.yonghui.cn:27000");
		
		JedisSentinelPool redisPool = new JedisSentinelPool("ecs-uat-redis01",sentinels);
		Jedis jedis = null;
		jedis = redisPool.getResource();
		long ret = jedis.del(key);
		if (ret == 0) {
			System.out.println("NO token key deleted.");
			return;
		}			
		key = "cus-web-uat_pg-app-demo112345678";
		ret = jedis.del(key);
		if (ret == 0) {
			System.out.println("NO app key deleted.");
			return;
		}		
		jedis.disconnect();
		redisPool.destroy();
	}

}
