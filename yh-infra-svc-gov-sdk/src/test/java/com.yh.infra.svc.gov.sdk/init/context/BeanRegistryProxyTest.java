package com.yh.infra.svc.gov.sdk.init.context;

import com.yh.infra.svc.gov.sdk.init.callback.RequestHandler;
import com.yh.infra.svc.gov.sdk.testhelper.DemoService1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BeanRegistryProxyTest {

	@Before
	public void setUp() throws Exception {
		BeanRegistry.getInstance().register(new AppRegContext(null));
	}

	@After
	public void tearDown() throws Exception {
		AppRegContext ctx = BeanRegistry.getInstance().getBean(AppRegContext.class);
		ctx.setNewCallback(false);
	}

	@Test
	public void test() throws Exception {
		AppRegContext ctx = BeanRegistry.getInstance().getBean(AppRegContext.class);
    	assertFalse(ctx.isNewCallback());
		
		BeanRegistryProxy.add(DemoService1.class, new DemoService1());
    	assertFalse(ctx.isNewCallback());
    	
    	BeanRegistryProxy.add(RequestHandler.class, new DemoRequestHandler());
    	assertTrue(ctx.isNewCallback());
    	
	}

	class DemoRequestHandler implements RequestHandler {

		@Override
		public String getKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getValue() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
