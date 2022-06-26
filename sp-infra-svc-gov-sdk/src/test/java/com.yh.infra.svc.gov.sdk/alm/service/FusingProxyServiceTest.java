package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.sp.infra.svc.gov.sdk.alm.command.ResponseStatusCommand;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.context.ResponseStatusEnum;
import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.util.TestReflectionUtils;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.apache.http.Header;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class FusingProxyServiceTest {

    private FusingProxyService fusingProxyService;
    private MonitorGlobalContext ctx;
    private ClientAndServer mockServer;
    
    @Mock
    HttpClientProxy httpClient;
    @Mock
    private Logger mockLogger;
    private Logger oriLogger = null;
    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);

		AppConfig ac = prepareAC();

		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);
        ctx = new MonitorGlobalContext(new AlmConfig(), TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1"));
        ctx.updateRulesData(vd);
        
        fusingProxyService = new FusingProxyService(ctx, httpClient);
        oriLogger = null;
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop(); 
    	if (oriLogger != null)
    		TestReflectionUtils.setStaticValue(FusingProxyService.class, "logger", oriLogger, Logger.class);
    }

    @Test
    public void test_AddLog() {
    	MonitorLogMessage lmc = new MonitorLogMessage(SdkCommonConstant.LOG_TYPE_MONITOR);
    	lmc.setCfgVersion(101);
        fusingProxyService.addLog(lmc);

        List<LogMessage> list = new ArrayList<LogMessage>();
        ctx.pollLogs(list, SdkCommonConstant.LOG_TYPE_MONITOR, 100);
        assertEquals(1, list.size());
        assertEquals(101, ((MonitorLogMessage)list.get(0)).getCfgVersion().intValue());
    }

    @Test
    public void test_SendLog_Success() throws InterruptedException {
    	LogMessage lmc = TestVoUtil.voMonitorLogMessage("UT-APP1", 12, 11, 123123, 123, "MAIN-1", "LOCALHOST");
    	
		Map<String, String> retMap = new HashMap<String, String>(); 
		retMap.put("result", "{\"isSuccess\": true}");
		retMap.put("status", "200");
		when(httpClient.postJson(anyString(), anyString(), anyInt(), any(Header[].class))).thenReturn(retMap);
    	
        fusingProxyService.sendLog(Collections.singletonList(lmc), SdkCommonConstant.LOG_TYPE_MONITOR);
        
        ResponseStatusCommand command = ctx.getStatusCommand(1);
        assertNotNull(command);
        assertEquals("SUCCESS",command.getStatus().name());
    }

    @Test
    public void test_SendLog_Reject() throws InterruptedException {
    	LogMessage lmc = TestVoUtil.voMonitorLogMessage("UT-APP1", 12, 11, 123123, 123, "MAIN-1", "LOCALHOST");
    	
        ctx.getFusingStatus().open();
        fusingProxyService.sendLog(Collections.singletonList(lmc), SdkCommonConstant.LOG_TYPE_MONITOR);

        ResponseStatusCommand command = ctx.getStatusCommand(1);
        assertEquals("REJECT",command.getStatus().name());
    }

    @Test
    public void test_WriteLog_Url_Empty() throws InterruptedException {
    	oriLogger = TestReflectionUtils.setStaticValue(FusingProxyService.class, "logger", mockLogger, Logger.class);

        AppConfig ac = prepareAC();
        ac.getCommonCfgs().get(0).setCfgValue("");
        MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
        ctx.updateRulesData(vd);

		List<LogMessage> l = new ArrayList<LogMessage>();
        l.add(TestVoUtil.voMonitorLogMessage("UT-APP1", 12, 11, 123, 11, "", ""));
        fusingProxyService.sendLog(l, SdkCommonConstant.LOG_TYPE_MONITOR);
        
        verify(httpClient,times(0)).postJson(anyString(),anyString(),anyInt(),any(Header[].class));
        verify(mockLogger, times(1)).error(eq("LOG-ARCHIVE url is emtpy."));
    }

    @Test
    public void test_SendLog_No_Result() throws InterruptedException {
    	oriLogger = TestReflectionUtils.setStaticValue(FusingProxyService.class, "logger", mockLogger, Logger.class);

    	assertNull(ctx.getStatusCommand(1));
        setHttpResponse("","/json1", 0);
        
        AppConfig ac = prepareAC();
        ac.getCommonCfgs().get(0).setCfgValue("http://localhost:1080/json1");
        MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
        List<LogMessage> l = new ArrayList<LogMessage>();
        l.add(TestVoUtil.voMonitorLogMessage("UT-APP1", 12, 11, 123, 11, "", ""));
        fusingProxyService.sendLog(l, SdkCommonConstant.LOG_TYPE_MONITOR);
        
        assertEquals( ResponseStatusEnum.FAIL,ctx.getStatusCommand(1).getStatus());
        verify(mockLogger, times(2)).error(startsWith("JSON parse fail"));
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
		
		appConfig.getCommonCfgs().add(TestVoUtil.voCommonConfig(SdkCommonConstant.PG_COMMON_CFG_SYS_LOG_ARCH, "archurl"));
				
		return appConfig;
	}


}