/**
 * 
 */
package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author luchao 2019-01-02
 *
 */
public class SendLogServiceTest {

	SendLogService service;
	
	@Mock
	FusingProxyService fps;
	
	AppRegConfig cfg;
	MonitorGlobalContext context;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		cfg = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
		context = new MonitorGlobalContext(new AlmConfig(), cfg);
		
		MonitorRulesData vd = new MonitorRulesData();
		vd.setAppCfg(prepareAC());
		context.updateRulesData(vd);

		service = new SendLogService(context, fps);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_WriteLog() {
		List<LogMessage> commandList = prepareLog();
		service.writeLog(commandList);
		verify(fps, times(2)).sendLog(any(List.class), anyInt());
		
	}
	
	@Test
	public void test_no_target_found() {
		List<LogMessage> commandList = prepareLog();
		MonitorRulesData vd = new MonitorRulesData();
		context.updateRulesData(vd);
		
		service.writeLog(commandList);
		verify(fps, times(0)).sendLog(any(List.class), anyInt());
		
	}
	
	
	
	private AppConfig prepareAC() {
		AppConfig appConfig = new AppConfig();
		appConfig.setAppId("test_sdk_pg");
		appConfig.setVersion(1);
		
		List<LogTarget> logtarget = new ArrayList<LogTarget>();
		logtarget.add(TestVoUtil.voLogTarget(11, true, true, "INFO"));
		logtarget.add(TestVoUtil.voLogTarget(12, true, true, "INFO"));
		appConfig.getMonitor().setLogTargets(logtarget);
		return appConfig;
	}

	private List<LogMessage> prepareLog() {
		List<LogMessage> ret = new ArrayList<LogMessage>();
		ret.add(TestVoUtil.voMonitorLogMessage("UA-APP1", 1, 11, 12312312L, 30, "LOCAL-1", "localhost"));
		ret.add(TestVoUtil.voMonitorLogMessage("UA-APP1", 1, 11, 22222222L, 30, "LOCAL-2", "localhost"));
		ret.add(TestVoUtil.voMonitorLogMessage("UA-APP1", 1, 12, 22222222L, 30, "LOCAL-2", "localhost"));
		return ret;
	}
	
}
