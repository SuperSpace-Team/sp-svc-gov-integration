package com.yh.infra.svc.gov.sdk.alm.callback;

import com.yh.infra.svc.gov.agent.agent.AgentBeanRegistry;
import com.yh.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.yh.infra.svc.gov.sdk.alm.callback.impl.LocalAlmCallbackServiceImpl;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.command.cfg.*;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 
 * @author luchao  2019-04-15
 *
 */
public class LocalAlmCallbackServiceImplTest {

    private LocalAlmCallbackServiceImpl service;
    private MonitorGlobalContext context;
    
    
    @Mock
    AgentInstallProcessor mockProcessor;
    
    @Mock
    private Logger mockLogger;
    
    private Logger oriLogger;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        AppRegConfig pc = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        context = new MonitorGlobalContext(new AlmConfig(), pc);
        service = new LocalAlmCallbackServiceImpl();
        oriLogger = null;
        
        BeanRegistry.getInstance().register(context);
        BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);
        BeanRegistry.getInstance().register(AgentInstallProcessor.class.getName(), mockProcessor);
        
        AgentBeanRegistry.register(AgentInstallProcessor.class.getName(), mockProcessor);
    }

    @After
    public void tearDown() throws Exception {
    	if (oriLogger != null) 
    		TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", oriLogger, Logger.class);

    }

    @Test
    public void test_No_UPDATE() throws Exception {
    	oriLogger = TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", mockLogger, Logger.class);

        VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_NO_UPDATE);
        Map<String, Object> datamap = createMap(vqr);
        
        boolean ret = service.validate(datamap);
        assertTrue(ret);
        service.process(datamap);
        
        assertEquals(-1 , context.getCurrentVersion());
        verify(mockLogger, times(1)).info(startsWith("do not need to update."));
    }

    @Test
    public void test_UPDATE_DISABLED() {
    	oriLogger = TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", mockLogger, Logger.class);

    	VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_UPDATE_DISABLED);
        Map<String, Object> datamap = createMap(vqr);
        boolean ret = service.validate(datamap);
        assertTrue(ret);
        verify(mockLogger, times(1)).info(startsWith("do not update version."));

        service.process(datamap);
        verify(mockLogger, times(1)).info(startsWith("do not need to update."));
        assertEquals(-1 , context.getCurrentVersion());
    }

    @Test
    public void test_LIFECYCLE_DISABLED() {
    	oriLogger = TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", mockLogger, Logger.class);

    	context.startMonitor();
    	VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_LIFECYCLE_DISABLED);
        Map<String, Object> datamap = createMap(vqr);
        context.updateRulesData(TestVoUtil.voMonitorRulesData(createAc()));
        assertFalse(context.ifMonitorStoped());
        boolean ret = service.validate(datamap);
        assertTrue(ret);
        assertFalse(context.ifMonitorStoped());
        service.process(datamap);
        assertTrue(context.ifMonitorStoped());
        assertTrue(context.getRulesData().isEmpty());
        verify(mockLogger, times(1)).info(startsWith("stop monitor.resp is"));
    }

    @Test
    public void test_VERSION_NOT_SUPPORTED() {
    	oriLogger = TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", mockLogger, Logger.class);

    	VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_VERSION_NOT_SUPPORTED);
        Map<String, Object> datamap = createMap(vqr);
        context.updateRulesData(TestVoUtil.voMonitorRulesData(createAc()));
        assertFalse(context.ifMonitorStoped());
        boolean ret = service.validate(datamap);
        assertFalse(ret);
        verify(mockLogger, times(1)).error(eq("current sdk version is not supported, need upgrade. "));
    }
    @Test
    public void test_unknown_response_code() {
    	oriLogger = TestReflectionUtils.setStaticValue(LocalAlmCallbackServiceImpl.class, "logger", mockLogger, Logger.class);

    	VersionQueryResp vqr = create();
        vqr.setCode(100);
        Map<String, Object> datamap = createMap(vqr);
        boolean ret = service.validate(datamap);
        assertFalse(ret);
        service.process(datamap);
        assertEquals(-1, context.getCurrentVersion());
        verify(mockLogger, times(2)).error(startsWith("UNKNOWN status code."));
    }

    @Test
    public void test_Normal() {
        VersionQueryResp vqr = create();
        vqr.setCode(SdkCommonConstant.RESP_STATUS_UPDATE);
        Map<String, Object> datamap = createMap(vqr);
        boolean ret = service.validate(datamap);
        assertTrue(ret);
        service.process(datamap);
        assertEquals(1, context.getCurrentVersion());
        // 验证日志
        assertTrue(context.getLogTarget().get(0).getLogArch());
        // load 监控信息
        assertTrue(context.getRules("pg_class.pg_method(pg_input_param)").size() == 1);
        assertEquals("test_pg_match_exp",context.getRules("pg_class.pg_method(pg_input_param)").get(11).getTransformNodeList().get(0).getMatchExp());
        assertEquals("test_pg_node_match_exp",context.getRules("pg_class.pg_method(pg_input_param)").get(11).getNodeList().get(0).getMatchExp());
    }
    
    private Map<String, Object> createMap(VersionQueryResp resp) {
    	Map<String, Object> ret = new HashMap<String, Object>();
    	ret.put(SdkCommonConstant.CB_MAP_CONFIG_RESP, JsonUtil.writeValueSafe(resp));
        return ret;
    }
    
    private AppConfig createAc() {
    	AppConfig appConfig = TestVoUtil.voAppConfig("test_pg",true,1) ;

        LogTarget logTarget = TestVoUtil.voLogTarget(11, false, true, "info");
        appConfig.getMonitor().setLogTargets(Collections.singletonList(logTarget));
        appConfig.setLifecycle(true);

        LogConfig log4j = TestVoUtil.voLogConfig("", "LOG4J");
        LogConfig log4j2 = TestVoUtil.voLogConfig("", "LOG4J2");
        appConfig.setLogCfgs(Arrays.asList(log4j, log4j2));

        CommonConfig commonConfig =  TestVoUtil.voCommonConfig("", "");
        appConfig.setCommonCfgs(Collections.singletonList(commonConfig));

        Entry entry = TestVoUtil.voEntry(1, "pg_class", "pg_method", "pg_input_param", "");
        appConfig.setEntries(Collections.singletonList(entry));

        BizFactor bf = TestVoUtil.voBizFactor(11, "", "", 0, 0, 0);
        appConfig.getMonitor().setFactors(Collections.singletonList(bf));

        TransformNode transformNode = TestVoUtil.voTransformNode(111, 11, 1, "test_pg_match_exp", "", "", "", "");
        appConfig.getMonitor().setTransformNodes(Collections.singletonList(transformNode));

        Node node = TestVoUtil.voNode(1111, 11, 1, 2, null, "test_pg_node_match_exp", "", "", "", "", "", 20);
        appConfig.getMonitor().setNodes(Collections.singletonList(node));
        return appConfig;
    }

    private VersionQueryResp create() {
        VersionQueryResp queryResp = new VersionQueryResp();
        queryResp.setConfig(JsonUtil.writeValueSafe(createAc()));
        return queryResp;
    }

}