package com.sp.infra.svc.gov.sdk.alm.client;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.command.cfg.*;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LogServiceTest {

	@Before
	public void setUp() throws Exception {
		AppRegConfig config = new AppRegConfig();
		MonitorGlobalContext context = new MonitorGlobalContext(new AlmConfig(), config);
		BeanRegistry.getInstance().register(context);
		BeanRegistry.getInstance().register(SdkCommonConstant.ALM_EMBEDDED_TYPE, SdkCommonConstant.ALM_TYPE_SVC_GOV_SDK);
		BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);

        context.updateRulesData(TestVoUtil.voMonitorRulesData(createAc()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_mLog() {
		
		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
				
		LogService.mLog(23, "TB123", "inlog1", "outlog1");
		
		List<LogMessage> logList = new ArrayList<>(2);
		context.pollLogs(logList, SdkCommonConstant.LOG_TYPE_MONITOR, 2);
		assertEquals(1, logList.size());
		
	}
	@Test
	public void test_tLog() {
		
		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);
				
		LogService.tLog(32, "TB123", "PAC123");
		
		List<LogMessage> logList = new ArrayList<>(2);
		context.pollLogs(logList, SdkCommonConstant.LOG_TYPE_MONITOR, 2);
		assertEquals(1, logList.size());
		
	}

    private AppConfig createAc() {
    	AppConfig appConfig = TestVoUtil.voAppConfig("UT-APP1",true,1) ;

        LogTarget logTarget = TestVoUtil.voLogTarget(11, false, true, "info");
        appConfig.getMonitor().setLogTargets(Collections.singletonList(logTarget));
        appConfig.setLifecycle(true);

        BizFactor bf = TestVoUtil.voBizFactor(11, "", "", 0, 0, 0);
        appConfig.getMonitor().setFactors(Collections.singletonList(bf));

        TransformNode tnode = TestVoUtil.voTransformNode(32, 11, 1, "test_pg_match_exp", "", "", "", "");
        tnode.setType(2);
        appConfig.getMonitor().setTransformNodes(Collections.singletonList(tnode));

        Node node = TestVoUtil.voNode(23, 11, 1, 2, null, "test_pg_node_match_exp", "", "", "", "", "", 20);
        node.setType(2);
        appConfig.getMonitor().setNodes(Collections.singletonList(node));
        return appConfig;
    }
}
