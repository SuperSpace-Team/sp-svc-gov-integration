package com.sp.infra.svc.gov.sdk.alm.callback;

import com.sp.infra.svc.gov.sdk.alm.callback.impl.AgentAlmCallbackServiceImpl;
import com.sp.infra.svc.gov.sdk.alm.callback.impl.LocalAlmCallbackServiceImpl;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.callback.CallbackService;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author luchao
 * @date 2021/4/25 5:43 下午
 */
public class AlmCallbackProxyImpl implements CallbackService {
	private static final Logger logger = LoggerFactory.getLogger(AlmCallbackProxyImpl.class);
	private LocalAlmCallbackServiceImpl localCallback = new LocalAlmCallbackServiceImpl();

	@Override
	public synchronized boolean validate(Map<String, Object> cbDataMap) {
		if (logger.isDebugEnabled()) {
			logger.debug("begin to validate the config. {}", cbDataMap);
		}

		Boolean almEnabled =  BeanRegistry.getInstance().getBean(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR);

		if (almEnabled == null || (! almEnabled)) {
			logger.info("ALM is disabled.");
			return true;
		}
		Boolean inited = BeanRegistry.getInstance().<Boolean>getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
		if (inited == null) {
			logger.warn("alm has not initialized.");
			return false;
		}

		String embeddedType = BeanRegistry.getInstance().<String>getBean(SdkCommonConstant.ALM_EMBEDDED_TYPE);
		if (logger.isDebugEnabled())
			logger.debug("use service governance SDK embedded ALM.");
		return localCallback.validate(cbDataMap);
	}

	public void process(Map<String, Object> data) {
		if (logger.isDebugEnabled())
			logger.debug("begin to process the config. {}", data);
		Boolean almEnabled =  BeanRegistry.getInstance().<Boolean>getBean(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR);
		if (almEnabled == null || (! almEnabled)) {
			logger.info("ALM is disabled.");
			return;
		}
		Boolean inited = BeanRegistry.getInstance().<Boolean>getBean(SdkCommonConstant.ALM_INITIALIZED_FLAG);
		if (inited == null) {
			logger.warn("alm has not initialized.");
			return;
		}

		String embeddedType = BeanRegistry.getInstance().<String>getBean(SdkCommonConstant.ALM_EMBEDDED_TYPE);
		localCallback.process(data);
	}

	@Override
	public String getCallbackName() {
		return "AlmCallbackProxyImpl";
	}

}
