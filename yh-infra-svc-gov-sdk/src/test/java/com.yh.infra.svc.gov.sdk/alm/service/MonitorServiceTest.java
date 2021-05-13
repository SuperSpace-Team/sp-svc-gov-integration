/**
 * 
 */
package com.yh.infra.svc.gov.sdk.alm.service;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.context.InvokeContext;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.MethodWrapper;
import com.yh.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.yh.infra.svc.gov.sdk.command.cfg.Entry;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.OrderService;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * 
 * 
 * @author luchao 2019-01-02
 *
 */
public class MonitorServiceTest {

	MonitorService service;

	@Mock
	FusingProxyService fusingProxyService;

	@Captor
	private ArgumentCaptor<MonitorLogMessage> argument;

	MonitorGlobalContext ctx;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		AppRegConfig pc = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
		ctx = new MonitorGlobalContext(new AlmConfig(), pc);

		service = new MonitorService(ctx, fusingProxyService);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test_preFetchData_success() {
		
		AppConfig ac = prepareAC();
		
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);

		InvokeContext ic = prepareIC();
		service.preFetchData(ic);

		Map<Integer, List<String>> logMap = ic.getNodeInLogMap();
		List<MonitorLogMessage> lmcList = ic.getBizLogCmdList();

		List<String> loglist = logMap.get(31);
		assertEquals(1, loglist.size());
		assertEquals("pf code is TB10033", loglist.get(0));
		assertEquals(1, lmcList.size());
		assertEquals(11, lmcList.get(0).getBizCode().intValue());
	}

	
	@Test
	public void test_postHandle_success() {
		AppConfig ac = prepareAC();
		
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);

		InvokeContext ic = prepareIC();
		ic.setReturned("PACS2567");
		service.postHandle(ic);

		verify(fusingProxyService, times(1)).addLog(argument.capture());
		MonitorLogMessage lmc = argument.getValue();
		assertEquals("pf code is TB10033", lmc.getMonitorLogList().get(0).getInLog());
		assertEquals("TB10033", lmc.getMonitorLogList().get(0).getKey());
		assertEquals(31, lmc.getMonitorLogList().get(0).getCode());

		assertEquals("TB10033", lmc.getTransformLogList().get(0).getSrcKey());
		assertEquals("PACS2567", lmc.getTransformLogList().get(0).getTargetKey());
		assertEquals(41, lmc.getTransformLogList().get(0).getCode());
	}

	@Test
	public void test_postHandle_exception() {
		AppConfig ac = prepareAC();
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);

		InvokeContext ic = prepareIC();
		ic.setReturned("PACS2567");
		ic.setThrowable(new Exception("PF23452244 FAILED."));
		service.postHandle(ic);

		verify(fusingProxyService, times(1)).addLog(argument.capture());
		MonitorLogMessage lmc = argument.getValue();
		assertEquals("pf code is TB10033", lmc.getMonitorLogList().get(0).getInLog());
		assertEquals("TB10033", lmc.getMonitorLogList().get(0).getKey());
		assertEquals("PF23452244", lmc.getMonitorLogList().get(0).getExceptionLog());
		assertEquals(31, lmc.getMonitorLogList().get(0).getCode());

		assertEquals("TB10033", lmc.getTransformLogList().get(0).getSrcKey());
		assertEquals("PACS2567", lmc.getTransformLogList().get(0).getTargetKey());
		assertEquals(41, lmc.getTransformLogList().get(0).getCode());
	}
	/**
	 * 多个返回值， 拆单场景， 数组方式返回值
	 */
	@Test
	public void test_postHandle_multi_suborder_array() {
		AppConfig ac = prepareAC2();
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);

		InvokeContext ic = prepareIC();
		String[] strs = { "PACS2567", "PACS5555" };
		ic.setReturned(strs);
		service.postHandle(ic);

		verify(fusingProxyService, times(1)).addLog(argument.capture());
		List<MonitorLogMessage> lmc = argument.getAllValues();
		assertEquals(1, lmc.size());
		assertEquals(1, lmc.get(0).getMonitorLogList().size());
		assertEquals("pf code is TB10033", lmc.get(0).getMonitorLogList().get(0).getInLog());
		assertEquals("pacs code is PACS2567,PACS5555", lmc.get(0).getMonitorLogList().get(0).getOutLog());
		assertEquals("TB10033", lmc.get(0).getMonitorLogList().get(0).getKey());
		assertEquals(31, lmc.get(0).getMonitorLogList().get(0).getCode());

		assertEquals(2, lmc.get(0).getTransformLogList().size());
		assertEquals("TB10033", lmc.get(0).getTransformLogList().get(0).getSrcKey());
		assertEquals("PACS5555 aaa", lmc.get(0).getTransformLogList().get(1).getTargetKey());
		assertEquals(41, lmc.get(0).getTransformLogList().get(0).getCode());
		assertEquals("TB10033", lmc.get(0).getTransformLogList().get(1).getSrcKey());
		assertEquals("PACS2567 aaa", lmc.get(0).getTransformLogList().get(0).getTargetKey());
		assertEquals(41, lmc.get(0).getTransformLogList().get(1).getCode());
	}

	/**
	 * 多个返回值， 拆单场景， collection方式返回值
	 */
	@Test
	public void test_postHandle_multi_suborder_collection() {
		AppConfig ac = prepareAC2();
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);

		InvokeContext ic = prepareIC();
		String[] strs = { "PACS2567", "PACS5555" };
		ic.setReturned(strs);
		service.postHandle(ic);

		verify(fusingProxyService, times(1)).addLog(argument.capture());
		List<MonitorLogMessage> lmc = argument.getAllValues();
		assertEquals(1, lmc.size());
		assertEquals(1, lmc.get(0).getMonitorLogList().size());
		assertEquals("pf code is TB10033", lmc.get(0).getMonitorLogList().get(0).getInLog());
		assertEquals("pacs code is PACS2567,PACS5555", lmc.get(0).getMonitorLogList().get(0).getOutLog());
		assertEquals("TB10033", lmc.get(0).getMonitorLogList().get(0).getKey());
		assertEquals(31, lmc.get(0).getMonitorLogList().get(0).getCode());

		assertEquals(2, lmc.get(0).getTransformLogList().size());
		assertEquals("TB10033", lmc.get(0).getTransformLogList().get(0).getSrcKey());
		assertEquals("PACS2567 aaa", lmc.get(0).getTransformLogList().get(0).getTargetKey());
		assertEquals(41, lmc.get(0).getTransformLogList().get(0).getCode());
		assertEquals("TB10033", lmc.get(0).getTransformLogList().get(1).getSrcKey());
		assertEquals("PACS5555 aaa", lmc.get(0).getTransformLogList().get(1).getTargetKey());
		assertEquals(41, lmc.get(0).getTransformLogList().get(1).getCode());
	}

	
	private InvokeContext prepareIC() {
		InvokeContext ret = new InvokeContext(1);
		MethodWrapper m = Mockito.mock(MethodWrapper.class);
		ret.setMethod(m);
		
		ret.setTargetClass(OrderService.class);
		
		Class<?>[] argTypes = {String.class,Integer.class};
		doReturn(argTypes).when(m).getParameterTypes();
		
		doReturn("createOrder").when(m).getName();
		
		Object paras[] = new Object[2];
		paras[0] = "TB10033";
		paras[1] = 23;
		ret.setArgs(paras);
		
		ret.setReturned("haha");
		ret.setThrowable(null);
		
		
		
		Node node = TestVoUtil.voNode(31, 11, 21, 2,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		ret.getBizNodeMap().put(11, node);
		
		List<String> loglist = Arrays.asList("pf code is TB10033");
		ret.getNodeInLogMap().put(31, loglist);
		
		List<MonitorLogMessage> lmcList = Arrays
				.asList(TestVoUtil.voMonitorLogMessage("test_sdk_pg", 233, 11, 1, 1, "local-thread", "localhost"));
		ret.getBizLogCmdList().addAll(lmcList);

		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("P1", "TB10033");
		inputData.put("P2", "23");
		ret.getInputData().putAll(inputData);

		ret.getBizTransMap().put(11, TestVoUtil.voTransformNode(41, 11, 22, "a", "a", "a", "a", "a"));
		return ret;
	}

	private AppConfig prepareAC() {
		AppConfig appConfig = TestVoUtil.voAppConfig("test_sdk_pg", true, 1);
		appConfig.setLifecycle(true);
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(11, "order", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));
		
		Entry entry = TestVoUtil.voEntry(21, OrderService.class.getName(), "createOrder", "String,Integer", "");
		Node node = TestVoUtil.voNode(31, 11, 21, 2, null, "#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		TransformNode tnode = TestVoUtil.voTransformNode(41, 11, 21, "#P1.startsWith(\"TB\")", "#IN[P1]",
				"pf-code", "#OUT", "pacs-code");

		appConfig.getEntries().add(entry);
		appConfig.getMonitor().getNodes().add(node);
		appConfig.getMonitor().getTransformNodes().add(tnode);
		
		return appConfig;
	}

	private AppConfig prepareAC2() {
		AppConfig appConfig = TestVoUtil.voAppConfig("test_sdk_pg", true, 1);
		
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(11, "order", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));

		Entry entry = TestVoUtil.voEntry(21, OrderService.class.getName(), "createOrder", "String,Integer", "");
		Node node = TestVoUtil.voNode(31, 11, 21, 2, null, "#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "", 20);
		TransformNode tnode = TestVoUtil.voTransformNode(41, 11, 21, "#P1.startsWith(\"TB\")", "#IN[P1]",
				"pf-code", "#OUT.![#this + ' aaa'] ", "pacs-code");

		appConfig.getEntries().add(entry);
		appConfig.getMonitor().getNodes().add(node);
		appConfig.getMonitor().getTransformNodes().add(tnode);
		
		return appConfig;
	}
	
}
