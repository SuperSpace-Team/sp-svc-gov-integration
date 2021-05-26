
package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.callback.CallbackService;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.testhelper.DemoCallbackService;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


/**
 * 特殊情况处理
 * 
 * 
 * @author luchao  2019-02-15
 *
 */
public class ConfigServiceTest {

    private ConfigService service;
    private AppRegContext context;
    @Mock
    private CallbackService cbService; 
    
    @Mock
    private static Logger mockLogger;
    private Logger oriLogger = null;
    DemoCallbackService dcs = new DemoCallbackService();
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        AppRegConfig pc = new AppRegConfig();
        context = new AppRegContext(pc);
        service = new ConfigService(context);
        oriLogger = null;
        BeanRegistry.getInstance().add(CallbackService.class, dcs);
        BeanRegistry.getInstance().add(CallbackService.class, cbService);
    }

    @After
    public void tearDown() throws Exception {
    	Map<String, Object> beanRegistry = new ConcurrentHashMap<String, Object>();
    	TestReflectionUtils.setValue(BeanRegistry.getInstance(), "regBeansMap", beanRegistry);
    	if (oriLogger != null)
            TestReflectionUtils.setStaticValue(ConfigService.class, "logger", oriLogger);
    }

    /**
     * validate 返回false
     * @throws Exception 
     */
    @Test
    public void test_updateVersion_verify_fail() throws Exception {
        oriLogger = TestReflectionUtils.setStaticValue(ConfigService.class, "logger", mockLogger);

        VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_UPDATE);
        when(cbService.validate(anyMap())).thenReturn(false);
        
        service.updateVersion(vqr);
        verify(mockLogger, times(1)).warn(startsWith("Callback service"), any(), any());
    }

    /**
     * validate抛出exception
     * @throws Exception 
     */
    @Test
    public void test_updateVersion_verify_exception() throws Exception {
        oriLogger = TestReflectionUtils.setStaticValue(ConfigService.class, "logger", mockLogger);

        VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_UPDATE);
        Exception e = new RuntimeException("UNIT_TEST");
        when(cbService.validate(anyMap())).thenThrow(e);
        
        service.updateVersion(vqr);
        verify(mockLogger, times(1)).warn(eq("Error occurs when callback validations."), any(Throwable.class));
    }

    
    /**
     * process抛出exception
     * @throws Exception 
     */
    @Test
    public void test_updateVersion_process_exception() throws Exception {
        oriLogger = TestReflectionUtils.setStaticValue(ConfigService.class, "logger", mockLogger);

        VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_UPDATE);
        Exception e = new RuntimeException("UNIT_TEST");
        when(cbService.validate(anyMap())).thenReturn(true);
        doThrow(e).when(cbService).process(anyMap());
        
        assertEquals(SdkCommonConstant.PG_VERSION_INITIAL_VERSION, context.getCurrentVersion());
        service.updateVersion(vqr);
        assertEquals(11, context.getCurrentVersion());
        verify(mockLogger, times(1)).warn(startsWith("Error occurs when process for svc gov callback service"), any(Throwable.class));
    }


    private VersionQueryResp create() {
        VersionQueryResp queryResp = new VersionQueryResp();
        queryResp.setConfig("");
        queryResp.setVersion(11);
        return queryResp;
    }
}