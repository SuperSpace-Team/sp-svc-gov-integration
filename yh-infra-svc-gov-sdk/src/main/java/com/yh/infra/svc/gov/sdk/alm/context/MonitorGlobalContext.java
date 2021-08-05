package com.yh.infra.svc.gov.sdk.alm.context;

import com.yh.infra.svc.gov.sdk.alm.command.*;
import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.alm.constant.FusingStatusEnum;
import com.yh.infra.svc.gov.sdk.command.cfg.LogConfig;
import com.yh.infra.svc.gov.sdk.command.cfg.LogTarget;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author luchao
 * @date 2021/4/25 5:55 下午
 */
public class MonitorGlobalContext {
    private static final Logger logger = LoggerFactory.getLogger(MonitorGlobalContext.class);
    /**
     * 配置信息。全部的。 *
     */
    private AppRegConfig pgConfig = null;
    private AlmConfig almConfig = null;
    /**
     * 用于监控/转换日志的缓存。LinkedBlockingQueue本身是线程安全的， 不需要特殊处理。
     */
    private LinkedBlockingQueue<MonitorLogMessage> almLogQueue = null;

    /**
     * 用于发送监控/转换日志的状态的缓存。LinkedBlockingQueue本身是线程安全的， 不需要特殊处理。
     */
    private LinkedBlockingQueue<ResponseStatusCommand> statusQueue = null;

    /**
     * 发送日志的熔断状态
     */
    private FusingStatus fusingStatus = null;
    
    /**
     *  包含全链路监控和探索任务的 所有缓存数据。
     */
    private MonitorRulesData rulesData = null;
    /**
     * 节点的禁用启用状态。
     *
     */
    private volatile boolean lifecycle = true;

    public MonitorGlobalContext(AlmConfig config, AppRegConfig pgConfig) {
        this.almConfig = config;
        this.pgConfig = pgConfig;
        fusingStatus = new FusingStatus(config.getFuseCheckWindow());
        almLogQueue = new LinkedBlockingQueue<>(config.getLogCacheCapacity());
        statusQueue = new LinkedBlockingQueue<>(config.getFuseMsgQueueMaxSize());
    }

    /**********
     * 监控日志相关
     **************************************************************************/
    public void addLog(LogMessage log) {
    	boolean added = false;
    	if(log.getLogType() == SdkCommonConstant.LOG_TYPE_MONITOR) {
    		added = almLogQueue.offer((MonitorLogMessage)log);
    		if (! added) {
    			logger.warn("alm log has been abandoned. queue size: " + almLogQueue.size());
    		}
    	}
    }

    public void pollLogs(Collection<LogMessage> target, int type, int size) {
    	if (type == SdkCommonConstant.LOG_TYPE_MONITOR)
    		almLogQueue.drainTo(target, size);
    }

    /**********
     * 熔断相关
     **************************************************************************/
    /**
     * 这个是给发送程序用的。
     *
     * @return
     */
    public boolean isBrokerOpen() {
        return fusingStatus.getStatus() == FusingStatusEnum.OPEN;
    }

    /**
     * 这个是给熔断管理器用的。
     *
     * @return
     */
    public FusingStatus getFusingStatus() {
        return fusingStatus;
    }

    public void addStatus(ResponseStatusCommand status) {
    	if (logger.isDebugEnabled())
    		logger.debug("add a new status: " + status);
        boolean ret = statusQueue.offer(status);
        if (! ret) {
        	logger.warn("cannot add status event to queue. " + status.getStatus());
        }
    }

    public ResponseStatusCommand getStatusCommand(int to) {
        ResponseStatusCommand ret = null;
        try {
            ret = statusQueue.poll(to, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error in polling message.", e);
            Thread.currentThread().interrupt();
        }
        return ret;
    }

    /**
     * 是否停止监控, true为停止
     *
     * @return
     */
    public boolean ifMonitorStoped() {
        // 如果没有配置数据， 不做监控。
        if ((rulesData == null) || (rulesData.getAppCfg() == null))
            return true;
        // 如今节点禁用， 或者app禁用， 都 不做监控
        return (!lifecycle) || (!rulesData.getAppCfg().getLifecycle());
    }

    public void stopMonitor() {
        lifecycle = false;
    }

    public void startMonitor() {
        lifecycle = true;
    }

    /**********
     * 版本管理相关
     ******************************************************************/
    public List<LogTarget> getLogTarget() {
        if (rulesData.getAppCfg() == null) {
            return Collections.emptyList();
        }
        return rulesData.getAppCfg().getMonitor().getLogTargets();
    }

    public String getSdkVersion() {
        return pgConfig.getSdkVersion();
    }

    public String getCommonCfg(String key) {
        if (rulesData.getAppCfg() == null) {
            return null;
        }
        return rulesData.getCommonCfg(key);
    }

    public String getLog4jUrl(String type) {

        if ((rulesData == null) || (rulesData.getAppCfg() == null))
            return null;

        List<LogConfig> cfglist = rulesData.getAppCfg().getLogCfgs();
        if (cfglist != null) {
            for (LogConfig cfg : cfglist) {
                if (type.equals(cfg.getLogType())) {
                    return cfg.getFileUrl();
                }
            }
        }

        return null;
    }

    public boolean ifSimpleMode(Integer bizCode) {
        // 如果没有配置数据， 只做精简模式监控。
        if (rulesData.getAppCfg() == null) {
            logger.error("NO configuration found. " + bizCode);
            return true;
        }

        Integer m = rulesData.getWorkMode(bizCode);
        // 如果没有配置数据， 只做精简模式监控。
        if (m == null) {
            logger.error("NO bizfactor found. " + bizCode);
            return true;
        }
        return m.equals(SdkCommonConstant.WORK_MODE_SIMPLE);
    }

    public int getCurrentVersion() {
    	if (rulesData == null)
    		return SdkCommonConstant.PG_VERSION_EMPTY_VERSION;
        return rulesData.getAppCfg().getVersion();
    }

    public void updateRulesData(MonitorRulesData rulesData) {
        this.rulesData = rulesData;
    }
    public Map<String, Map<Integer, BizEntryRule>> getRulesData() {
    	if (rulesData == null)
    		return null;
        return rulesData.getEntryMap();
    }

    /************************** 监控规则相关 *********************/
    public Map<Integer, BizEntryRule> getRules(String entry) {
    	if (StringUtils.isEmpty(entry))
    		return null;
        return rulesData.getRules(entry);
    }

    public NodeExpressionCommand getExpression(int type, int code) {
        return rulesData.getExpression(type + "-" + code);
    }
    
    public AlmConfig getAlmConfig() {
        return almConfig;
    }

    /***********
     * 全局配置
     ******************************/
    public AppRegConfig getPgConfig() {
        return pgConfig;
    }

    public String getAppId() {
    	if (rulesData == null)
    		return null;
        return rulesData.getAppCfg().getAppId();
    }

    /**
     * 自定义接口相关
     * 
     */
    
    public Node getCustomizeNode(int code) {
        if (rulesData == null || rulesData.getCustomizeNodeMap() == null) {
            return null;
        }
    	return rulesData.getCustomizeNodeMap().get(code);
    }
    public TransformNode getCustomizeTnode(int code) {
        if (rulesData == null || rulesData.getCustomizeTnodeMap() == null) {
            return null;
        }
    	return rulesData.getCustomizeTnodeMap().get(code);
    }
}
