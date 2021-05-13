package com.yh.infra.svc.gov.sdk.alm.init;

import com.yh.infra.svc.gov.agent.agent.AgentBeanRegistry;
import com.yh.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.yh.infra.svc.gov.agent.alm.MethodInterceptor;
import com.yh.infra.svc.gov.sdk.alm.callback.AlmCallbackProxyImpl;
import com.yh.infra.svc.gov.sdk.alm.callback.impl.LocalAlmCallbackServiceImpl;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.yh.infra.svc.gov.sdk.alm.daemon.FusingManager;
import com.yh.infra.svc.gov.sdk.alm.daemon.MonitorLogSender;
import com.yh.infra.svc.gov.sdk.alm.interceptor.MethodInterceptorImpl;
import com.yh.infra.svc.gov.sdk.alm.service.FusingProxyService;
import com.yh.infra.svc.gov.sdk.alm.service.MonitorService;
import com.yh.infra.svc.gov.sdk.alm.service.SendLogService;
import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.callback.CallbackService;
import com.yh.infra.svc.gov.sdk.init.callback.ComponentInit;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.ClazzUtil;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:30 下午
 */
public class AlmComponentInit implements ComponentInit {
	private static final Logger logger = LoggerFactory.getLogger(AlmComponentInit.class);

	private ExecutorService monitorPool = null;
	private List<MonitorLogSender> senderList = new ArrayList<>();

	/**
	 * 判断是否agent中集成了alm
	 * 
	 * @return
	 */
	private boolean ifAlmExistInAgent() {
		// 找旧版的， agent中的 
		ClassLoader cl = getAgentClassLoader();
		Class clz = null;
		if (cl != null)
			clz = ClazzUtil.loadClass(SdkCommonConstant.AGENT_ALM_MONITOR_SERVICE_NAME, cl);
		if (clz == null)
			logger.info("failed to find the agent embeded MonitorService. ");
		else
			logger.info("find the agent embeded MonitorService successfully! ");
		return (clz != null);
	}

	@Override
	public boolean init(AppRegContext pgContext) {
		Boolean almEnabled = BeanRegistry.getInstance().getBean(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR);
		if (almEnabled == null || (!almEnabled)) {
			logger.info("ALM is disabled.");
			return true;
		}

		boolean ret = false;
		if (ifAlmExistInAgent()) {
			BeanRegistry.getInstance().register(SdkCommonConstant.ALM_EMBEDDED_TYPE, "agent");
			ret = initAgentAlm(pgContext);
		} else {
			BeanRegistry.getInstance().register(SdkCommonConstant.ALM_EMBEDDED_TYPE, "svc-gov-sdk");
			ret = initLocalAlm(pgContext);
		}
		if (! ret) {
			logger.error("ALM HAS NOT been initialized. ALM is disabled!");
			BeanRegistry.getInstance().register(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR, false);
		} else {
			AlmCallbackProxyImpl acp = new AlmCallbackProxyImpl();
			// 注册agent的 callback的代理类
			BeanRegistry.getInstance().add(CallbackService.class, acp);
			logger.info("ALM component initialized successfully !");
		}

		// 永远返回true，不影响其他组件的初始化。
		return true;
	}

	/**
	 * 治理sdk集成监控的版本。
	 * 
	 * @param pgContext
	 * @return
	 */
	private boolean initLocalAlm(AppRegContext pgContext) {
		logger.info("Begin to initialize ALM embeded in service governance SDK.");
		try {
			ClassLoader agentCl = getAgentClassLoader();
			if (agentCl == null) {
				logger.warn("Cannot find agent class loader.");
				return false;
			}

			Method method = ClazzUtil.getMethod(agentCl, SdkCommonConstant.AGENT_REGISTRY_CLASSPATH_NAME, SdkCommonConstant.GET_BEAN, String.class);
			if (method == null) {
				logger.warn("cannot get AgentBeanRegistry.getBean method.");
				return false;
			}
			BaseResponseEntity bre = new BaseResponseEntity();
			if (!ClazzUtil.invoke(bre, method, AgentInstallProcessor.class.getName())) {
				logger.warn("cannot call AgentBeanRegistry.getBean to get AgentInstallProcessor instance.");
				return false;
			}
			// 将AgentInstallProcessor  注册到治理SDK的 beanregistry中， 方便使用。
			if (bre.getData() == null) {
				// 如果没有AgentInstallProcessor
				logger.warn("cannot find the AgentInstallProcessor in agent class loader.");
				return false;
			}
			BeanRegistry.getInstance().register(bre.getData());
			AgentBeanRegistry.register(MethodInterceptor.class.getName(), new MethodInterceptorImpl());
			
			localInit(pgContext.getConfig());
			
			BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, true);
			return true;
		} catch (Exception e) {
			logger.error("failed to init alm. ", e);
		}
		return false;
	}

	private ClassLoader getAgentClassLoader() {
		ClassLoader agentCl = this.getClass().getClassLoader();
		Method method = null;

		// 找到agent的classloader。
		while (agentCl != null) {
			method = ClazzUtil.getMethod(agentCl, SdkCommonConstant.AGENT_REGISTRY_CLASSPATH_NAME, SdkCommonConstant.REGISTER, String.class, Object.class);
			if (method == null)
				agentCl = agentCl.getParent();
			else
				break;
		}
		return agentCl;
	}

	/**
	 * agent集成监控的版本。
	 * 
	 * @param pgContext
	 * @return
	 */
	private boolean initAgentAlm(AppRegContext pgContext) {
		logger.info("begin to initialize ALM embeded in agent.");
		try {
			ClassLoader agentCl = getAgentClassLoader();
			if (agentCl == null)
				return false;

			// 在agent的beanregistry中注册sdk 自己的class loader。
			Method method = ClazzUtil.getMethod(agentCl, SdkCommonConstant.AGENT_REGISTRY_CLASSPATH_NAME, SdkCommonConstant.REGISTER, String.class, Object.class);
			
			BaseResponseEntity bre = new BaseResponseEntity();
			if (!ClazzUtil.invoke(bre, method, SdkCommonConstant.SDK_CLASS_LOADER, this.getClass().getClassLoader())) {
				logger.warn("cannot call AgentBeanRegistry.register.");
				return false;
			}

			// 取得alm的class loader
			method = ClazzUtil.getMethod(agentCl, SdkCommonConstant.AGENT_REGISTRY_CLASSPATH_NAME, SdkCommonConstant.GET_BEAN, String.class);
			if (method == null) {
				logger.warn("cannot get AgentBeanRegistry.getBean method.");
				return false;
			}
			bre.reset();
			if (!ClazzUtil.invoke(bre, method, SdkCommonConstant.ALM_CLASS_LOADER)) {
				logger.warn("cannot call AgentBeanRegistry.getBean to get als's classloader.");
				return false;
			}
			ClassLoader almCl = (ClassLoader) bre.getData();
			if (almCl == null) {
				logger.warn("cannot get ALM ClassLoader.");
				return false;
			}
			// alm的classloader注册到本地。
			BeanRegistry.getInstance().register(SdkCommonConstant.ALM_CLASS_LOADER, almCl);
			
			// 取得alm的初始化类
			bre.reset();
			method = ClazzUtil.getMethod(almCl, SdkCommonConstant.ALM_REGISTRY_CLASSPATH_NAME, SdkCommonConstant.GET_BEAN, String.class);
			if (method == null) {
				logger.warn("cannot get ALM's BeanRegistry.getBean method.");
				return false;
			}
			if (!ClazzUtil.invoke(bre, method, SdkCommonConstant.ALM_INITIALIZER_CLASSPATH_NAME)) {
				logger.warn("cannot call ALM's BeanRegistry.getBean.");
				return false;
			}
			Object almInit = bre.getData();
			if (almInit == null) {
				logger.warn("cannot get ALM initializer.");
				return false;
			}

			// 执行alm的初始化
			bre.reset();
			method = ClazzUtil.getMethod(almCl, SdkCommonConstant.ALM_INITIALIZER_CLASSPATH_NAME, SdkCommonConstant.INIT, String.class);
			if (method == null) {
				logger.warn("cannot get alm init method.");
				return false;
			}
			bre.reset();
			if (!ClazzUtil.invoke(bre, almInit, method, pgContext.getConfigJson())) {
				logger.warn("cannot init alm.");
				return false;
			}
			
			if (! waitForInit()) {
				logger.error("agent ALM cannot initialized. ");
				return false;
			}
			
			return true;
		} catch (Exception e) {
			logger.error("failed to init alm. ", e);
		}
		return false;
	}

	@Override
	public void clean(AppRegContext pgContext) {
		BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, false);
		if (ifAlmExistInAgent()) {
			// 不需要做。 alm的初始化是自己clean的。
		} else {
			localClean();
		}
	}

	private boolean waitForInit() {
		Boolean initFlag = null;
		int waitSec = 10;  //最多等10s。等agent初始化完毕。
		do {
			ThreadUtil.sleep(1000);
			initFlag = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
			waitSec --;
		} while (initFlag == null || !initFlag || (waitSec > 0)); 
		return (initFlag != null && initFlag);
	}
	
	
	private synchronized void localInit(AppRegConfig config) {
		logger.info("local alm begin to initilize");

		BeanRegistry br = BeanRegistry.getInstance();

		// 如果已经初始化成功。 不做处理
		Boolean init = (Boolean) br.getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
		if ((init != null) && init) {
			logger.warn("ALM already initilized. there must be some error here!!!");
			return;
		}

		AlmConfig almconfig = br.getBean(AlmConfig.class);
		if (almconfig == null) {
			almconfig = new AlmConfig();
			br.register(almconfig);
		}

		// 数据校验。
		if (almconfig.getLogCacheCapacity() < SdkCommonConstant.PG_LOG_CACHE_SIZE_MINIMUM)
			almconfig.setLogCacheCapacity(SdkCommonConstant.PG_LOG_CACHE_SIZE_MINIMUM);
		if (almconfig.getLogBatchSize() > almconfig.getLogCacheCapacity())
			almconfig.setLogBatchSize(almconfig.getLogCacheCapacity());
		if (almconfig.getFuseCheckWindow() < SdkCommonConstant.PG_FUSE_CHECK_TIME_WINDOW_MINIMUM)
			almconfig.setFuseCheckWindow(SdkCommonConstant.PG_FUSE_CHECK_TIME_WINDOW_MINIMUM);


		// 线程数，给MonitorLogSender
		monitorPool = Executors.newFixedThreadPool(almconfig.getLogSenderThread() + 1);

		// 初始化可用的网络 连接器
		HttpClientProxy httpClient = new HttpClientProxyImpl();

		MonitorGlobalContext mgCtx = new MonitorGlobalContext(almconfig,config);
		FusingProxyService fusingService = new FusingProxyService(mgCtx, httpClient);
		MonitorService monitorService = new MonitorService(mgCtx, fusingService);
		SendLogService sendLogService = new SendLogService(mgCtx, fusingService);
		FusingManager fusingManager = new FusingManager(mgCtx);
		LocalAlmCallbackServiceImpl alm = new LocalAlmCallbackServiceImpl();

		br.register(monitorService);
		br.register(sendLogService);
		br.register(fusingService);
		br.register(fusingManager);
		br.register(mgCtx);
		br.register(alm);

		fusingManager.start();

		launchSender(almconfig, mgCtx, sendLogService);

	}

	/**
	 * 用于异常时的清理上下文环境
	 */
	private synchronized void localClean() {
		BeanRegistry br = BeanRegistry.getInstance();
		FusingManager fusingManager = br.getBean(FusingManager.class);

		if (fusingManager != null)
			fusingManager.setExit();
		for (MonitorLogSender sender : senderList) {
			sender.setExit();
		}
		
		senderList.clear();
		ThreadUtil.sleep(1000);
		if (monitorPool != null)
			monitorPool.shutdownNow();
	}

	private void launchSender(AlmConfig almconfig, MonitorGlobalContext mgCtx, SendLogService sendLogService) {
		for (int i = 0; i < almconfig.getLogSenderThread(); i++) {
			MonitorLogSender sender = new MonitorLogSender(mgCtx, sendLogService);
			senderList.add(sender);
			monitorPool.execute(sender);
		}
	}

}
