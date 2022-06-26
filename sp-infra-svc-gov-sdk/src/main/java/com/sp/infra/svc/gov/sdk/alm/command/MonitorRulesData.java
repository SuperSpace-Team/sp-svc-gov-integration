package com.sp.infra.svc.gov.sdk.alm.command;

import com.sp.infra.svc.gov.sdk.command.cfg.AppConfig;
import com.sp.infra.svc.gov.sdk.command.cfg.Node;
import com.sp.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 版本数据. 会被多个线程访问，取得entry的数据。 所以需要线程同步。
 * 
 * @author luchao
 * @date 2021/4/25 5:49 下午
 */
public class MonitorRulesData {
	private static final Logger logger = LoggerFactory.getLogger(MonitorRulesData.class.getName());
	
	private int version = -1;
	
	/**
	 * 每个入口对应的解析规则。 key: className.methodName(parameters)
	 * 
	 * 内嵌的map的key是bizcode, 本方法在业务链路中的node和tnode。一般只有1个node， 但是也可能是多个node。
	 * 
	 */
	private Map<String, Map<Integer, BizEntryRule>> entryMap;
	/**
	 * 监控/转换数据的spring el expression cache. 监控数据是0-开头加code，转换数据是1-开头加code。需要做线程同步
	 */
	private Map<String, NodeExpressionCommand> expCache = new HashMap<String, NodeExpressionCommand>();
	/**
	 * 数据缓存
	 */
	private Map<String, Object> dataCache = new HashMap<String, Object>();
	
	/**
	 * 自定义监控节点。
	 */
	private Map<Integer, Node> customizeNodeMap = new HashMap<>();
	/**
	 * 自定义转换节点。
	 */
	private Map<Integer, TransformNode>  customizeTnodeMap = new HashMap<>();
	
	
	
	/**
	 * 当前使用的版本数据
	 */
	private AppConfig appCfg = null;


	public AppConfig getAppCfg() {
		return appCfg;
	}

	public void setAppCfg(AppConfig appCfg) {
		this.appCfg = appCfg;
	}

	public int getVersion() {
		return version;
	}

	public Map<Integer, BizEntryRule> getRules(String entry) {
		Map<Integer, BizEntryRule> ret = null;
		if (entryMap != null)
			ret = entryMap.get(entry);
		return ret;
	}
	public NodeExpressionCommand getExpression(String entry) {
		return expCache.get(entry);
	}

	public String getCommonCfg(String key) {
		Map<String, String> common = (Map<String, String>) dataCache.get("COMMON_CFG");
		if (common == null) {
			logger.error("no common cfg cache found. {}, {}", version, key);
			return null;
		}
		return common.get(key);
	}
	
	public Integer getWorkMode(Integer bizCode) {
		Map<Integer, Integer> workModeMap = (Map<Integer, Integer>) dataCache.get("CLIENT_WORK_MODE");
		if (workModeMap == null) {
			logger.error("no work mode cache found. {}, {}", version, bizCode);
			return SdkCommonConstant.WORK_MODE_SIMPLE;
		}
		return workModeMap.get(bizCode);
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setEntryMap(Map<String, Map<Integer, BizEntryRule>> entryMap) {
		this.entryMap = entryMap;
	}

	public void setExpCache(Map<String, NodeExpressionCommand> expCache) {
		this.expCache = expCache;
	}

	public void setDataCache(Map<String, Object> cache) {
		this.dataCache = cache;
	}
	public Map<String, Map<Integer, BizEntryRule>> getEntryMap() {
		return entryMap;
	}

	public Map<String, NodeExpressionCommand> getExpCache() {
		return expCache;
	}

	public Map<String, Object> getDataCache() {
		return dataCache;
	}

	public Map<Integer, Node> getCustomizeNodeMap() {
		return customizeNodeMap;
	}

	public void setCustomizeNodeMap(Map<Integer, Node> customizeNodeMap) {
		this.customizeNodeMap = customizeNodeMap;
	}

	public Map<Integer, TransformNode> getCustomizeTnodeMap() {
		return customizeTnodeMap;
	}

	public void setCustomizeTnodeMap(Map<Integer, TransformNode> customizeTnodeMap) {
		this.customizeTnodeMap = customizeTnodeMap;
	}


	
}
