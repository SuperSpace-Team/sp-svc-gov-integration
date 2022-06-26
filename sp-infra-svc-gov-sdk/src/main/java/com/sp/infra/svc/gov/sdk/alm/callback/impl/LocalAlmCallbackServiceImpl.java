/**
 *
 */
package com.sp.infra.svc.gov.sdk.alm.callback.impl;

import com.sp.infra.svc.gov.agent.agent.AgentInstallProcessor;
import com.sp.infra.svc.gov.sdk.alm.command.BizEntryRule;
import com.sp.infra.svc.gov.sdk.alm.command.MonitorRulesData;
import com.sp.infra.svc.gov.sdk.alm.command.NodeExpressionCommand;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.util.VersionDataBuilder;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.command.cfg.Entry;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luchao
 * @date 2021/4/25 5:46 下午
 */
public class LocalAlmCallbackServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(LocalAlmCallbackServiceImpl.class.getName());

	private BaseResponseEntity<VersionQueryResp> validateContext(Map<String, Object> data) {
		BaseResponseEntity<VersionQueryResp> ret = new BaseResponseEntity<>();
		ret.setIsSuccess(false);

		VersionQueryResp resp = JsonUtil.readValueSafe(String.valueOf(data.get(SdkCommonConstant.CB_MAP_CONFIG_RESP)), VersionQueryResp.class);
		if (logger.isDebugEnabled())
			logger.debug("begin to validate version. new version is " + resp.getVersion());
		if (resp.getCode() == SdkCommonConstant.RESP_STATUS_VERSION_NOT_SUPPORTED) {
			logger.error("current sdk version is not supported, need upgrade. ");
			return ret;
		}

		if (resp.getCode() != SdkCommonConstant.RESP_STATUS_UPDATE 
				&& resp.getCode() != SdkCommonConstant.RESP_STATUS_LIFECYCLE_DISABLED 
				&& resp.getCode() != SdkCommonConstant.RESP_STATUS_NO_UPDATE 
				&& resp.getCode() != SdkCommonConstant.RESP_STATUS_UPDATE_DISABLED) {
			// 为了兼容性考虑， 不识别的 状态码 有可能是新版本的 状态。
			// 禁止更新版本
			logger.error("UNKNOWN status code. " + resp.getCode());
			return ret;
		}
		ret.setIsSuccess(true);
		ret.setData(resp);
		return ret;
	}

	private void logEntryInfo(Map<String, Map<Integer, BizEntryRule>> entryMap) {
		if (logger.isDebugEnabled()) {
			logger.debug("finished generate entry map, size : " + entryMap.size());
			for (Map.Entry<String, Map<Integer, BizEntryRule>> entry : entryMap.entrySet()) {
				Map<Integer, BizEntryRule> emap = entry.getValue();
				logger.debug("entry '{}' contains biz-factor: {}", entry.getKey(), emap.keySet().toString());
			}
		}
	}

	private void logNodeInfo(Map<String, NodeExpressionCommand> expMap, Map<String, Map<Integer, BizEntryRule>> entryMap) {
		if (logger.isDebugEnabled()) {
			logger.debug("finished generate expresson cache, size : " + entryMap.size());
			for (String node : expMap.keySet()) {
				logger.debug("expresson. node " + node);
			}
		}
	}

	public boolean validate(Map<String, Object> data) {
		BaseResponseEntity<VersionQueryResp> bre = validateContext(data);
		if (!bre.getIsSuccess())
			return false;
		VersionQueryResp resp = bre.getData();
		if ((resp.getCode() == SdkCommonConstant.RESP_STATUS_NO_UPDATE) || (resp.getCode() == SdkCommonConstant.RESP_STATUS_UPDATE_DISABLED)) {
			logger.info("do not update version. resp is " + resp.getCode());
			return true;
		}

		AppConfig cfg = JsonUtil.readValueSafe(resp.getConfig(), AppConfig.class);

		// 既然可以更新了。 那appconfig就不能为null。
		if (cfg == null) {
			logger.error("AppConfig is null. " + resp.getConfig());
			return false;
		}

		if (resp.getCode() == SdkCommonConstant.RESP_STATUS_LIFECYCLE_DISABLED) {
			// 是节点禁用而不是app禁用。
			logger.info("do not monitor. lifecycle disabled. resp is " + resp.getCode());
			cfg.getMonitor().getNodes().clear();
			cfg.getMonitor().getTransformNodes().clear();
		}

		MonitorRulesData newRules = new MonitorRulesData();

		// 正常更新。
		newRules.setAppCfg(cfg);
		newRules.setVersion(cfg.getVersion());

		Map<String, Map<Integer, BizEntryRule>> entryMap = VersionDataBuilder.buildEntryMap(cfg);
		newRules.setEntryMap(entryMap);

		// debug, 打印entry信息
		logEntryInfo(entryMap);

		Map<String, NodeExpressionCommand> expMap = VersionDataBuilder.buildExpressionMap(cfg);
		newRules.setExpCache(expMap);

		// debug, 打印entry信息
		logNodeInfo(expMap, entryMap);

		Map<String, Object> dataCache = VersionDataBuilder.buildDataCache(cfg);
		newRules.setDataCache(dataCache);
		if (logger.isDebugEnabled())
			logger.debug("finished generate data cache, size : " + dataCache.size());

		
		// 自定义的node、tnode
		newRules.setCustomizeNodeMap(VersionDataBuilder.buildCustomizeNodeMap(cfg));
		newRules.setCustomizeTnodeMap(VersionDataBuilder.buildCustomizeTnodeMap(cfg));
		
		data.put(SdkCommonConstant.CB_MAP_MONITOR_RULES_DATA, newRules);

		AgentInstallProcessor processor = BeanRegistry.getInstance().getBean(AgentInstallProcessor.class);
		if (processor == null) {
			logger.error("system error. cannot find the AgentInstallProcessor");
			return false;
		}

		logger.info("finished validating version. new version is " + resp.getVersion());

		return true;
	}

	public void process(Map<String, Object> data) {
		// 取得sdk发来的配置。
		MonitorGlobalContext context = BeanRegistry.getInstance().getBean(MonitorGlobalContext.class);

		VersionQueryResp resp = JsonUtil.readValueSafe(String.valueOf(data.get(SdkCommonConstant.CB_MAP_CONFIG_RESP)), VersionQueryResp.class);
		logger.info("begin update version. new version is " + resp.getVersion());

		if ((resp.getCode() == SdkCommonConstant.RESP_STATUS_NO_UPDATE) || (resp.getCode() == SdkCommonConstant.RESP_STATUS_UPDATE_DISABLED)) {
			logger.info("do not need to update. resp is " + resp.getCode());
			return;
		}
		
		// 这个地方应该不会进入， 因为validate中对这种情况， 会返回false， 上层拿到false，不会调用process方法了。
		if (resp.getCode() != SdkCommonConstant.RESP_STATUS_UPDATE && resp.getCode() != SdkCommonConstant.RESP_STATUS_LIFECYCLE_DISABLED) {
			// 为了兼容性考虑， 不识别的 状态码 有可能是新版本的 状态。
			// 禁止更新版本
			logger.error("UNKNOWN status code. " + resp.getCode());
			return;
		}

		MonitorRulesData newRules = (MonitorRulesData) data.get(SdkCommonConstant.CB_MAP_MONITOR_RULES_DATA);
		if (newRules == null) {
			logger.error("system error. cannot find the rules data. " + resp.toString());
			return;
		}
		
		if (resp.getCode() == SdkCommonConstant.RESP_STATUS_LIFECYCLE_DISABLED) {
			// 是节点禁用而不是app禁用。
			logger.info("stop monitor.resp is " + resp.getCode());
			context.stopMonitor();
		} else {
			// 对于 非禁用监控 的情况， 一定是监控的。所以要start monitor
			// 主要是为了解决 之前禁用， 现在启用时。 stopMonitor 标记恢复的问题。
			context.startMonitor();
		}
		// reset agent

		AgentInstallProcessor processor = BeanRegistry.getInstance().getBean(AgentInstallProcessor.class);
		if (processor == null) {
			logger.error("system error. cannot find the AgentInstallProcessor");
			return;
		}
		
		processor.reload(convertEntry(newRules.getAppCfg().getEntries()), newRules.getAppCfg().getVersion());

		logger.info("finished reloading log config. version: " + newRules.getAppCfg().getVersion());

		// reload完成再更新版本， 因为MonitorRulesData要更新,
		// 否则MonitorRulesData清空后，理论上仍然会有拦截， LOG cache又会进去数据。
		// 这时进去的数据，使用旧的埋点和拦截规则，但使用了新的 版本号。 基于新版本的数据就会被污染。
		// 反之，先reload，再更新，污染的是旧版本数据，影响应该比较小。
		// 另外，对于新增的节点。如果先更新配置，这时候新节点的字节码增强没做，无法拦截。
		// 逻辑上不合理。 就是新版本的日志，有部分节点日志上传了。有部分节点没有拦截到。
		context.updateRulesData(newRules);
	}
	private Map<String, List<String>> convertEntry(List<Entry> entries) {
		Map<String, List<String>> entryMap = new ConcurrentHashMap<String, List<String>>();
		for (Entry e : entries) {
			if (entryMap.get(e.getClassName()) == null) {
				List<String> list = new ArrayList<String>();
				list.add(e.getMethodName());
				entryMap.put(e.getClassName(), list);
			} else {
				entryMap.get(e.getClassName()).add(e.getMethodName());
			}
		}
		return entryMap;
	}

}
