package com.yh.infra.svc.gov.sdk.alm.callback.impl;

import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.ClazzUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luchao
 * @date 2021/4/21
 */
public class AgentAlmCallbackServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(AgentAlmCallbackServiceImpl.class);

	private Method validateMethod = null;
	private Method processMethod = null;
	private Object almService = null;

	public synchronized boolean validate(Map<String, Object> cbDataMap) {
		//不需要判空。因为在init阶段会做。
		ClassLoader almClassLoader = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_CLASS_LOADER);
			
		boolean validate;
		try {
			if (almService == null) {
				Method m = ClazzUtil.getMethod(almClassLoader, SdkCommonConstant.ALM_REGISTRY_CLASSPATH_NAME,
						SdkCommonConstant.GET_BEAN, String.class);
				BaseResponseEntity bre = new BaseResponseEntity();
				if ((! ClazzUtil.invoke(bre, m, SdkCommonConstant.ALM_CALLBACK_CLASSPATH_NAME))
						|| (bre.getData() == null)){
					logger.warn("cannot get the alm bean registry.");
					cbDataMap.put(SdkCommonConstant.IGNORE_ALM, true);
					return true;
				}
				almService = bre.getData();	
				if (logger.isDebugEnabled())
					logger.debug("got the AlmCallbackServiceImpl. {}", almService);
			}
			if (validateMethod == null) {
				validateMethod = ClazzUtil.getMethod(almClassLoader, SdkCommonConstant.ALM_CALLBACK_CLASSPATH_NAME,
						SdkCommonConstant.VALIDATE, Map.class);
			}
			
			// 旧版的agent不支持 自定义节点。 所以要过滤掉。
			filterCustomizeNode(cbDataMap);
			
			if (validateMethod != null) {
				validate = (Boolean) validateMethod.invoke(almService, cbDataMap);
				logger.info("got the result from AlmCallbackServiceImpl.validate. {}", validate);
			} else {
				// 无法取得alm的 validate方法， 标记  alm 为忽略。
				logger.warn("cannot get the validate method !!!");
				cbDataMap.put(SdkCommonConstant.IGNORE_ALM, true);
				return true;
			}
		} catch (Exception e) {
			logger.warn("validate fail.", e);
			return false;
		}
		return validate;
	}

	/**
	 * 将可能的1.2版的数据结构， 过滤掉自定义节点， 变成1.1的。
	 * 
	 * @param cbDataMap
	 */
	private void filterCustomizeNode(Map<String, Object> cbDataMap) {
		VersionQueryResp resp = JsonUtil.readValueSafe(
				String.valueOf(cbDataMap.get(SdkCommonConstant.CB_MAP_CONFIG_RESP)), VersionQueryResp.class);
		if ((resp.getCode() == SdkCommonConstant.RESP_STATUS_NO_UPDATE)
				|| (resp.getCode() == SdkCommonConstant.RESP_STATUS_UPDATE_DISABLED)) {
			return ;
		}

		AppConfig cfg = JsonUtil.readValueSafe(resp.getConfig(), AppConfig.class);
		List<Node> nlist = new ArrayList<>();
		for (Node n: cfg.getMonitor().getNodes()) {
			if (n.getType() != SdkCommonConstant.ALM_NODE_TYPE_CUSTOMIZE) {
				nlist.add(n);
			}
		}
		cfg.getMonitor().setNodes(nlist);
		resp.setConfig(JsonUtil.writeValueSafe(cfg));
		cbDataMap.put(SdkCommonConstant.CB_MAP_CONFIG_RESP, JsonUtil.writeValueSafe(resp));
	}
	
	
	public void process(Map<String, Object> data) {
		try {
			Boolean ignore = (Boolean) data.get(SdkCommonConstant.IGNORE_ALM);
			if (ignore != null) {
				if (logger.isDebugEnabled())
					logger.debug("ignore the ALM.");
				return;
			}

			ClassLoader almClassLoader = BeanRegistry.getInstance().getBean(SdkCommonConstant.ALM_CLASS_LOADER);

			if (processMethod == null) {
				processMethod = ClazzUtil.getMethod(almClassLoader, SdkCommonConstant.ALM_CALLBACK_CLASSPATH_NAME, SdkCommonConstant.PROCESS, Map.class);
			}
			if (processMethod != null) {
				if (logger.isDebugEnabled())
					logger.debug("got the agent callback service method {}", processMethod);
				processMethod.invoke(almService, data);
				logger.info("call AlmCallbackServiceImpl.process successfully.");
			}
		} catch (Exception e) {
			logger.warn("error occurs when callback.process", e);
		}
	}
}
