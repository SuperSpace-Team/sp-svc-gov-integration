package com.yh.infra.svc.gov.sdk.init.daemon;

import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.init.service.ConfigService;
import com.yh.infra.svc.gov.sdk.init.service.SendReceiveService;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.yh.infra.svc.gov.sdk.util.WaitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class VersionCheckerTest {

    private VersionChecker versionChecker;
    private AppRegContext appRegContext;
    private AppRegConfig appRegConfig;

    @Mock
    SendReceiveService sendReceiveService ;
    @Mock
    ConfigService configService;

    @Captor
    private ArgumentCaptor<VersionQueryResp> argResp;


    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;
    

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        VersionQueryResp vqr = new VersionQueryResp();
        vqr.setConfig("test_content");
        when(sendReceiveService.send(any(VersionQueryReq.class))).thenReturn(vqr);

        oriLogger = null;
        BeanRegistry.getInstance().register(SdkCommonConstant.SDK_INITIALIZED_FLAG, true);
        
        appRegConfig = new AppRegConfig();
        // test 拉取版本间隔1s
        appRegConfig.setVersionPullInterval(1);
        
        appRegContext = new AppRegContext(appRegConfig);
        versionChecker = new VersionChecker(appRegContext,sendReceiveService,configService);
    }

    @After
    public void tearDown() throws Exception {
    	if (oriLogger != null)
    		TestReflectionUtils.setStaticValue(VersionChecker.class, "logger", oriLogger);
        versionChecker.setExit();
        versionChecker.join();
    }

    @Test
    public void test_SetExit() throws InterruptedException {
        versionChecker.start();
        WaitUtil.wait(1000);
        versionChecker.setExit();
        WaitUtil.wait(2000);
        Assert.assertFalse(versionChecker.isAlive());
    }

    @Test
    public void test_Run() {
    	versionChecker.start();
//        ThreadUtil.sleep(2000);
        WaitUtil.wait(2000);
        verify(configService,atLeast(1)).updateVersion(argResp.capture());
        Assert.assertEquals("test_content", argResp.getValue().getConfig());
    }

    @Test
    public void test_Run_new_listener() {
    	oriLogger = TestReflectionUtils.setStaticValue(VersionChecker.class, "logger", mockLogger);
    	appRegContext.setCurrentVersion(23);
    	appRegContext.setNewCallback(true);
    	versionChecker.start();
//        ThreadUtil.sleep(2000);
        WaitUtil.wait(2000);
        verify(mockLogger, times(1)).info(eq("found new callback. reset version to -1"));
        
    }

}