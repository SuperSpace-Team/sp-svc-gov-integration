package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.context.ResponseStatusEnum;
import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.command.cfg.Entry;
import com.sp.infra.svc.gov.sdk.command.cfg.Node;
import com.sp.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


/**
 * 日志发送test-case。包含了logsender的测试
 */
public class SendLogService_2Test {
    SendLogService service;
    AppRegConfig cfg;
    MonitorGlobalContext context;
    private ClientAndServer mockServer;

    FusingProxyService fps;
    
    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;
    
    
    @Spy
    HttpClientProxy httpClient = new HttpClientProxyImpl();

    @Before
    public void setUp() throws Exception {
        mockServer = ClientAndServer.startClientAndServer(1080);
        MockitoAnnotations.initMocks(this);
        cfg = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
        context = new MonitorGlobalContext(new AlmConfig(), cfg);
        fps = new FusingProxyService(context, httpClient);
        service = new SendLogService(context,fps);
        oriLogger = null;
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop(); 
    	if (oriLogger != null)
    		TestReflectionUtils.setStaticValue(SendLogService.class, "logger", oriLogger, Logger.class);
    }


    @Test
    public void test_SendLog_network_timeout() throws InterruptedException {
    	oriLogger = TestReflectionUtils.setStaticValue(HttpJsonClient.class, "logger", mockLogger, Logger.class);

    	assertEquals(null,context.getStatusCommand(1));
        setHttpResponse("{\"error\": \"error\"}","/json2", 7);

        AppConfig ac = prepareAC();
        ac.getCommonCfgs().get(0).setCfgValue("http://localhost:1080/json2");
        MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		context.updateRulesData(vd);
        
        List<MonitorLogMessage> l = new ArrayList<MonitorLogMessage>();
        l.add(TestVoUtil.voMonitorLogMessage("UT-APP1", 12, 11, 123, 11, "", ""));
        service.writeLog(l);
        
        assertEquals(ResponseStatusEnum.FAIL, context.getStatusCommand(1).getStatus());
        verify(mockLogger, times(2)).warn(startsWith("post data failed"), anyString(), any());
    }


    private void setHttpResponse(String msgBody,String url, int delay){
        new MockServerClient("localhost", 1080)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(url)
                )
                .respond(

                        response()
                                .withDelay(TimeUnit.SECONDS,delay)
                                .withStatusCode(HttpStatusCode.OK_200.code())
                                .withHeaders(org.mockserver.model.Header.header("content-type", "application/json"))
                                .withBody(msgBody)

                );
    }
	private AppConfig prepareAC() {
		AppConfig appConfig = TestVoUtil.voAppConfig("test_sdk_pg", true, 1);
		
		Entry entry = TestVoUtil.voEntry(21, "com.sp.infra.svc.gov.sdk.util.OrderService", "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);
		entry = TestVoUtil.voEntry(22, "com.sp.infra.svc.gov.sdk.util.DummyService", "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);
		entry = TestVoUtil.voEntry(23, "com.sp.infra.svc.gov.sdk.util.OrderServiceImpl", "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);

		
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(10, "returnOrder", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));
		Node node = TestVoUtil.voNode(20, 10, 21, 3,  null,"#P1.startsWith(\"RETURN\")", "#IN[P1]", "pf-re-code", "", "", "", 20);
		appConfig.getMonitor().getNodes().add(node);
		TransformNode tnode = TestVoUtil.voTransformNode(40, 10, 21, "#P1.startsWith(\"RETURN\")", "#IN[P1]",
				"pf-re-code", "#OUT", "pacs-re-code");
		appConfig.getMonitor().getTransformNodes().add(tnode);

		
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(11, "order", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));
		node = TestVoUtil.voNode(33, 11, 22, 3,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code", "", "", "", 20);
		appConfig.getMonitor().getNodes().add(node);
		node = TestVoUtil.voNode(32, 11, 21, 2,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		appConfig.getMonitor().getNodes().add(node);
		tnode = TestVoUtil.voTransformNode(41, 11, 21, "#P1.startsWith(\"TB\")", "#IN[P1]",
				"pf-code", "#OUT", "pacs-code");
		appConfig.getMonitor().getTransformNodes().add(tnode);
		
		appConfig.getMonitor().getLogTargets().add(TestVoUtil.voLogTarget(11, false, true, "info"));
		
		appConfig.getCommonCfgs().add(TestVoUtil.voCommonConfig(SdkCommonConstant.PG_COMMON_CFG_SYS_LOG_ARCH, "http://localhost:1080/json"));
		
		
		return appConfig;
	}

}
