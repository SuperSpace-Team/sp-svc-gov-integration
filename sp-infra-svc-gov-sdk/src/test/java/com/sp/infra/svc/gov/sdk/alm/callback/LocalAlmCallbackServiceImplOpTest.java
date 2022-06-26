package com.sp.infra.svc.gov.sdk.alm.callback;

import com.sp.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.sp.infra.svc.gov.sdk.alm.callback.impl.LocalAlmCallbackServiceImpl;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 运维测试用。 
 * @author luchao  2019-04-15
 *
 */
@Ignore
public class LocalAlmCallbackServiceImplOpTest {
    private LocalAlmCallbackServiceImpl service;
    private MonitorGlobalContext context;

    @Mock
    AgentInstallProcessor mockProcessor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        AppRegConfig pc = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        context = new MonitorGlobalContext(new AlmConfig(), pc);
        service = new LocalAlmCallbackServiceImpl();
        
        BeanRegistry.getInstance().register(context);
        BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);
        BeanRegistry.getInstance().register(AgentInstallProcessor.class.getName(), mockProcessor);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_1() throws Exception {
        Map<String, Object> datamap = createMap();
        
        boolean ret = service.validate(datamap);
        assertEquals(-1 , context.getCurrentVersion());
    }
    private AppConfig createAc() {
    	String json = "{\"appId\":\"IOSP-OR-ST\",\"version\":58,\"lifecycle\":true,\"entries\":[{\"code\":22,\"className\":\"com.superspace.scm.primservice.orc.manager.inter.OrderSplitServiceManagerImpl\",\"methodName\":\"receiveOrderInfoListByMq\",\"inputParamType\":\"List\",\"description\":\"从mq中消费上游系统的订单\"}],\"monitor\":{\"factors\":[{\"code\":13,\"bizAlias\":\"RL-ORDER\",\"name\":\"魔镜-RL官网订单发货流程\",\"description\":\"RL官网订单发货流程\",\"volume\":2,\"clientMode\":2,\"serverMode\":2}],\"logCfgs\":[],\"nodes\":[{\"code\":16,\"bizCode\":13,\"entryCode\":22,\"seq\":141,\"seqMatchExp\":null,\"matchExp\":null,\"keyExp\":\"#IN[P1].![#this.platformCode]\",\"keyTransAlias\":\"UNEX-CODE\",\"inLogExp\":\"'IOSP将路由结果反馈至HUB3'\",\"outLogExp\":null,\"errorLogExp\":null,\"threshold\":null}],\"transformNodes\":[],\"logTargets\":[{\"bizCode\":13,\"logFile\":false,\"logArch\":true,\"level\":\"INFO\"}]},\"commonCfgs\":[{\"cfgKey\":\"SYS_LOG_ARCH\",\"cfgValue\":\"http://10.101.6.66:1205/api/log/monitorData\"}]}";
    	return JsonUtil.readValueSafe(json, AppConfig.class);
    }

    private Map<String, Object> createMap() {
        VersionQueryResp queryResp = new VersionQueryResp();
        queryResp.setCode(SdkCommonConstant.RESP_STATUS_UPDATE);
        queryResp.setVersion(58);
        queryResp.setConfig(JsonUtil.writeValueSafe(createAc()));
    	Map<String, Object> data = new HashMap<>();
    	data.put("CB_MAP_CONFIG_RESP", JsonUtil.writeValueSafe(queryResp));
        return data;
    }

}