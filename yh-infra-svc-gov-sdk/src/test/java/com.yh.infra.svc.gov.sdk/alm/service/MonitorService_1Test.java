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
import com.yh.infra.svc.gov.sdk.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.slf4j.Logger;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * 只做prefetch方法的测试
 * 
 * @author 钱超 2019-01-02
 *
 */
public class MonitorService_1Test {

	MonitorService service;


	@Mock
	FusingProxyService fusingProxyService;

	@Mock
	Logger mockLogger;
	Logger oriLogger;
	
	
	@Captor
	private ArgumentCaptor<MonitorLogMessage> argument;

	MonitorGlobalContext ctx;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		AppRegConfig pc = TestVoUtil.voAppRegConfig("localhost", "127.0.0.1", "0.0.1");
		ctx = new MonitorGlobalContext(new AlmConfig(), pc);
		service = new MonitorService(ctx, fusingProxyService);
		doReturn(true).when(mockLogger).isDebugEnabled();
	}

	@After
	public void tearDown() throws Exception {
    	if (oriLogger != null)
            TestReflectionUtils.setStaticValue(MonitorService.class, "logger", oriLogger, Logger.class);
	}

	@Test
	public void test_preFetchData_stop_monitor() {
		oriLogger = TestReflectionUtils.setStaticValue(MonitorService.class, "logger", mockLogger, Logger.class);
		
		// 准备配置数据, 
		ctx.stopMonitor();
		InvokeContext ic = prepareIC();
		AppConfig ac = prepareAC();
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		service.preFetchData(ic);
		assertTrue(ctx.ifMonitorStoped());
		verify(mockLogger, times(1)).debug(eq("stop monitor data."));
	}

	/**
	 * 没有生成日志。 没有匹配的entry
	 */
	@Test
	public void test_preFetchData_And_no_log_generated() {
		// 准备mock
		InvokeContext ic = prepareIC();
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		ac.getEntries().get(2).setClassName("not_exist_class");// 改为不存在的， 导致匹配不上。
		
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		
		service.preFetchData(ic);
		assertEquals(0, ic.getBizLogCmdList().size());
	}

	/**
	 * 一个entry:
	 *   1.有多个biz 与之 绑定。 match计算，有的匹配， 有的不匹配  <br/>
	 *   2. 方法所在类，与方法定义类不一致。  <br/>
	 *   
	 *   10, 11，  这2个链路都用到了createOrder方法，但是10不符合， 11符合。<br/>
	 *   11中只有一个node符合。
	 */
	@Test
	public void test_preFetchData_multi_biz_matched() {
		// 准备mock
		InvokeContext ic = prepareIC();
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		service.preFetchData(ic);
		assertEquals(1, ic.getBizLogCmdList().size());
	}

	/**
	 * inlog的表达式为空
	 */
	@Test
	public void test_preFetchData_empty_inlog_expression() {
		// 准备mock
		InvokeContext ic = prepareIC();
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		ac.getMonitor().getNodes().get(0).setInLogExp("");
		ac.getMonitor().getNodes().get(1).setInLogExp("");
		ac.getMonitor().getNodes().get(2).setInLogExp("");
		
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		service.preFetchData(ic);
		assertTrue(ic.getNodeInLogMap().size() == 0);
	}

	
	/**
	 * biz match表达式不是 boolean，应该返回false。 无法匹配
	 */
	@Test
	public void test_preFetchData_invalid_biz_match_expression() {
		// 准备mock
		InvokeContext ic = prepareIC();
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		ac.getMonitor().getNodes().get(2).setMatchExp("'string type'");
		ac.getMonitor().getTransformNodes().clear();
		
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		service.preFetchData(ic);
	}

	/**
	 * biz match表达式为空， 但是SEQ表达式有值。 结果应该是一样的。
	 */
	@Test
	public void test_preFetchData_biz_match_is_null_AND_seq_match_is_not_null() {
		// 准备mock
		InvokeContext ic = prepareIC();
		
		// 准备配置数据, 
		AppConfig ac = prepareAC();
		ac.getMonitor().getNodes().get(0).setSeqMatchExp(ac.getMonitor().getNodes().get(0).getMatchExp());
		ac.getMonitor().getNodes().get(0).setMatchExp(null);
		ac.getMonitor().getNodes().get(1).setSeqMatchExp(ac.getMonitor().getNodes().get(1).getMatchExp());
		ac.getMonitor().getNodes().get(1).setMatchExp("");
		ac.getMonitor().getNodes().get(2).setSeqMatchExp(ac.getMonitor().getNodes().get(2).getMatchExp());
		ac.getMonitor().getNodes().get(2).setMatchExp("");
		
		// 更新版本
		MonitorRulesData vd = TestVoUtil.voMonitorRulesData(ac);		
		ctx.updateRulesData(vd);
		
		service.preFetchData(ic);
		assertEquals(1, ic.getNodeInLogMap().size());
		assertEquals(1, ic.getBizLogCmdList().size());
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
		
		
		ret.setClzEntry(ClazzUtil.getMethodFullName(OrderServiceImpl.class, m));
		ret.setDefClzEntry(ClazzUtil.getMethodFullName(OrderServiceImpl.class, m));
		
		return ret;
	}

	private AppConfig prepareAC() {
		AppConfig appConfig = TestVoUtil.voAppConfig("test_agent_pg", true, 1);
		appConfig.setLifecycle(true);
		
		// 3个 方法
		Entry entry = TestVoUtil.voEntry(21, OrderService.class.getName(), "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);
//		entry = TestVoUtil.voEntry(22, DummyService.class.getName(), "createOrder", "String,Integer", "");
//		appConfig.getEntries().add(entry);
		entry = TestVoUtil.voEntry(23, OrderServiceImpl.class.getName(), "createOrder", "String,Integer", "");
		appConfig.getEntries().add(entry);

		/**
		 * 维度10， 使用 OrderService.createOrder
		 * 
		 * 
		 */
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(10, "returnOrder", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));
		Node node = TestVoUtil.voNode(20, 10, 21, 3,  null,"#P1.startsWith(\"RETURN\")", "#IN[P1]", "pf-re-code", "", "", "", 20);
		appConfig.getMonitor().getNodes().add(node);
		TransformNode tnode = TestVoUtil.voTransformNode(40, 10, 21, "#P1.startsWith(\"RETURN\")", "#IN[P1]",
				"pf-re-code", "#OUT", "pacs-re-code");
		appConfig.getMonitor().getTransformNodes().add(tnode);

		/**
		 * 维度11, 使用DummyService.createOrder  和 OrderServiceImpl.createOrder
		 */
		appConfig.getMonitor().getFactors().add(TestVoUtil.voBizFactor(11, "order", "", 1, SdkCommonConstant.WORK_MODE_NORMAL, SdkCommonConstant.WORK_MODE_NORMAL));
		node = TestVoUtil.voNode(33, 11, 22, 3,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code", "", "", "", 20);
		appConfig.getMonitor().getNodes().add(node);
		node = TestVoUtil.voNode(32, 11, 23, 2,  null,"#P1.startsWith(\"TB\")", "#IN[P1]", "pf-code",
				"'pf code is ' + #P1", "'pacs code is ' + #OUT", "#EXCEPTION.getMessage().substring(0,10)", 20);
		appConfig.getMonitor().getNodes().add(node);
		tnode = TestVoUtil.voTransformNode(41, 11, 21, "#P1.startsWith(\"TB\")", "#IN[P1]",
				"pf-code", "#OUT", "pacs-code");
		appConfig.getMonitor().getTransformNodes().add(tnode);
		
		return appConfig;
	}


}
