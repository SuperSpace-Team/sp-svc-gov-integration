package com.sp.infra.svc.gov.sdk.alm.init;

import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Map;

public class AlmComponentInitTest {
	private AlmComponentInit launcher = new AlmComponentInit();
	
    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;

    @Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		oriLogger = null;
		Map<String, Object> rmap = (Map<String, Object> )TestReflectionUtils.getValue(BeanRegistry.getInstance(), "beanRegistry");
		rmap.clear();

		BeanRegistry.getInstance().register(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR, true);
	}

	@After
	public void tearDown() throws Exception {
    	if (oriLogger != null)
            TestReflectionUtils.setStaticValue(AlmComponentInit.class, "logger", oriLogger, Logger.class);
	}

	/*@Test
	public void test_init_fail() {
//       	oriLogger = TestReflectionUtils.setStaticValue(AlmComponentInit.class, "logger", mockLogger, Logger.class);
       	PgConfig pgCfg = TestVoUtil.voPgConfig("localhost", "127.0.0.1", "0.0.1");
       	PgContext pgCtx = new PgContext(pgCfg);
       			
       	launcher.init(pgCtx);
       	Boolean ret = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
       	assertNull(ret);
	}*/

}
