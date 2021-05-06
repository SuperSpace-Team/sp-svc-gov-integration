package com.yh.infra.svc.gov.sdk.alm.daemon;

import com.yh.infra.svc.gov.sdk.alm.command.LogMessage;
import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.alm.service.SendLogService;
import com.yh.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.yh.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class LogSenderManagerTest {
    private MonitorLogSender logSender;
    private MonitorGlobalContext ctx;
    private AppRegConfig appRegConfig;
    private Thread logThread = null;
    
    @Mock
    private SendLogService logService;
    @Captor
    private ArgumentCaptor<List<LogMessage>> argument;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        appRegConfig = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        ctx = new MonitorGlobalContext(new AlmConfig(), appRegConfig);
        // app config 设置 log target
        AppConfig ac = new AppConfig();
        List<LogTarget> lts = new ArrayList<LogTarget>();
        lts.add(TestVoUtil.voLogTarget(101, true, false, "info"));
        ac.getMonitor().setLogTargets(lts);
        
        MonitorRulesData nv = new MonitorRulesData();
        nv.setAppCfg(ac);
        ctx.updateRulesData(nv);

        logSender = new MonitorLogSender(ctx, logService);
        logThread = new Thread(logSender);
        logThread.start();
    }

    @After
    public void tearDown() throws Exception {
        logSender.setExit();
        logThread.join();
    }

    @Test
    public void test_SetExit() throws InterruptedException {
        logSender.setExit();
        logThread.join();
        Assert.assertFalse(logThread.isAlive());

    }


    @Test
    public void test_Run() {
        MonitorLogMessage lmc = new MonitorLogMessage(SdkCommonConstant.LOG_TYPE_MONITOR);
        lmc.setCfgVersion(101);
        ctx.addLog(lmc);
        ThreadUtil.sleep(2000);
        verify(logService, times(1)).writeLog(argument.capture());

    }

}