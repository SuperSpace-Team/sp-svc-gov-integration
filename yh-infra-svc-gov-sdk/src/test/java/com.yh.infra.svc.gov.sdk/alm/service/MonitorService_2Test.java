/**
 * 
 */
package com.yh.infra.svc.gov.sdk.alm.service;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.context.InvokeContext;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.util.MethodWrapper;
import com.yh.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.yh.infra.svc.gov.sdk.command.cfg.Entry;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
//import com.yh.infra.svc.gov.sdk.retry.dummy.DummyService;
import com.yh.infra.svc.gov.sdk.util.OrderService;
import com.yh.infra.svc.gov.sdk.util.OrderServiceImpl;
import com.yh.infra.svc.gov.sdk.util.TestVoUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * 只做postHandle方法的测试
 * 
 * @author luchao 2019-01-02
 *
 */
public class MonitorService_2Test {

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

	/**
	 * 生成日志的时候， 表达式计算结果 抛出异常。
	 */
	@Test
	public void test_postHandle_error_during_generating_log() {
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		ac.getMonitor().getNodes().get(2).setKeyExp("#DUMMY.toString()");
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		// 准备InvokeContext
		InvokeContext ic = prepareIC();
		
		service.postHandle(ic);
		verify(fusingProxyService, times(0)).addLog(any(MonitorLogMessage.class));
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
		
		
		
		Node node = TestVoUtil.voNode(32, 11, 21, 2,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		ret.getBizNodeMap().put(11, node);
		
		List<String> loglist = Arrays.asList("pf code is TB10033");
		ret.getNodeInLogMap().put(32, loglist);
		
		List<MonitorLogMessage> lmcList = Arrays
				.asList(TestVoUtil.voMonitorLogMessage("test_sdk_pg", 233, 11, 1, 1, "local-thread", "localhost"));
		ret.getBizLogCmdList().addAll(lmcList);
		
		return ret;
	}

	private AppConfig prepareAC() {
		AppConfig appConfig = TestVoUtil.voAppConfig("test_sdk_pg", true, 1);
		
		Entry entry = TestVoUtil.voEntry(21, OrderService.class.getName(), "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);
//		entry = TestVoUtil.voEntry(22, DummyService.class.getName(), "createOrder", "String,Integer", "");
//		appConfig.getEntries().add(entry);
		entry = TestVoUtil.voEntry(23, OrderServiceImpl.class.getName(), "createOrder", "String,Integer", "");
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
				"'getMonitor().pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		appConfig.getMonitor().getNodes().add(node);
		tnode = TestVoUtil.voTransformNode(41, 11, 21, "#P1.startsWith(\"TB\")", "#IN[P1]",
				"pf-code", "#OUT", "pacs-code");
		appConfig.getMonitor().getTransformNodes().add(tnode);
		
		return appConfig;
	}


}
