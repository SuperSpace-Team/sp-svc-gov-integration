package com.sp.infra.svc.gov.sdk.alm.init;

import com.sp.infra.svc.gov.agent.agent.AgentBeanRegistry;
import com.sp.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.sp.infra.svc.gov.agent.alm.MethodInterceptor;
import com.sp.infra.svc.gov.sdk.alm.callback.AlmCallbackProxyImpl;
import com.sp.infra.svc.gov.sdk.alm.callback.impl.LocalAlmCallbackServiceImpl;
import com.sp.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.daemon.FusingManager;
import com.sp.infra.svc.gov.sdk.alm.daemon.MonitorLogSender;
import com.sp.infra.svc.gov.sdk.alm.interceptor.MethodInterceptorImpl;
import com.sp.infra.svc.gov.sdk.alm.service.FusingProxyService;
import com.sp.infra.svc.gov.sdk.alm.service.MonitorService;
import com.sp.infra.svc.gov.sdk.alm.service.SendLogService;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.callback.CallbackService;
import com.sp.infra.svc.gov.sdk.init.callback.ComponentInit;
import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.sp.infra.svc.gov.sdk.util.ClazzUtil;
import com.sp.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luchao
 * @email lucaho1@superspace.cn
 * @date 2021/5/14 3:30 下午
 */
public class AlmComponentInit implements ComponentInit {
	private static final Logger logger = LoggerFactory.getLogger(AlmComponentInit.class);

	private ExecutorService monitorPool = null;
	private List<MonitorLogSender> senderList = new ArrayList<>();

	@Override
	public boolean init(AppRegContext pgContext) {
		Boolean almEnabled = BeanRegistry.getInstance().getBean(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR);
		if (almEnabled == null || (!almEnabled)) {
			logger.info("ALM is disabled.");
			return true;
		}

		BeanRegistry.getInstance().register(SdkCommonConstant.ALM_EMBEDDED_TYPE, "svc-gov-sdk");
		boolean ret = initLocalAlm(pgContext);

		if (!ret) {
			logger.error("ALM HAS NOT been initialized. ALM is disabled!");
			BeanRegistry.getInstance().register(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR, false);
		} else {
			AlmCallbackProxyImpl acp = new AlmCallbackProxyImpl();

			// 注册agent的callback的代理类
			BeanRegistry.getInstance().add(CallbackService.class, acp);
			logger.info("ALM component initialized successfully!");
		}

		//永远返回true,不影响其他组件的初始化。
		return true;
	}

	/**
	 * 治理sdk集成监控的版本
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
				logger.warn("Cannot get AgentBeanRegistry.getBean method.");
				return false;
			}

			BaseResponseEntity bre = new BaseResponseEntity();
			if (!ClazzUtil.invoke(bre, method, AgentInstallProcessor.class.getName())) {
				logger.warn("Cannot call AgentBeanRegistry.getBean to get AgentInstallProcessor instance.");
				return false;
			}

			// 将AgentInstallProcessor  注册到治理SDK的BeanRegistry中，方便使用。
			if (bre.getData() == null) {
				//如果没有AgentInstallProcessor
				logger.warn("Cannot find the AgentInstallProcessor in agent class loader.");
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

	@Override
	public void clean(AppRegContext pgContext) {
		BeanRegistry.getInstance().register(SdkCommonConstant.ALM_INITIALIZED_FLAG, false);
		localClean();
	}

	/**
	 * SDK初始化ALM
	 * @param config
	 */
	private synchronized void localInit(AppRegConfig config) {
		logger.info("Local alm begin to initialize..");
		BeanRegistry br = BeanRegistry.getInstance();

		//如果已经初始化成功,不做处理
		Boolean init = (Boolean) br.getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
		if ((init != null) && init) {
			logger.warn("ALM already initialized. There must be some error here!!!");
			return;
		}

		AlmConfig almconfig = br.getBean(AlmConfig.class);
		if (almconfig == null) {
			almconfig = new AlmConfig();
			br.register(almconfig);
		}

		//数据校验
		if (almconfig.getLogCacheCapacity() < SdkCommonConstant.PG_LOG_CACHE_SIZE_MINIMUM) {
			almconfig.setLogCacheCapacity(SdkCommonConstant.PG_LOG_CACHE_SIZE_MINIMUM);
		}

		if (almconfig.getLogBatchSize() > almconfig.getLogCacheCapacity()) {
			almconfig.setLogBatchSize(almconfig.getLogCacheCapacity());
		}

		if (almconfig.getFuseCheckWindow() < SdkCommonConstant.PG_FUSE_CHECK_TIME_WINDOW_MINIMUM) {
			almconfig.setFuseCheckWindow(SdkCommonConstant.PG_FUSE_CHECK_TIME_WINDOW_MINIMUM);
		}

		//初始化发送ALM日志的线程池给MonitorLogSender
		monitorPool = Executors.newFixedThreadPool(almconfig.getLogSenderThread() + 1);

		//初始化可用的网络 连接器
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

		//启动熔断
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
