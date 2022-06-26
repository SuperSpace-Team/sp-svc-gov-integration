package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.alm.command.*;
import com.sp.infra.svc.gov.sdk.alm.util.VersionDataBuilder;
import com.sp.infra.svc.gov.sdk.command.cfg.*;
import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.sp.infra.svc.gov.sdk.init.command.VersionQueryResp;
//import com.sp.infra.svc.gov.sdk.retry.context.TaskContext;
//import com.sp.infra.svc.gov.sdk.retry.vo.*;

import java.util.List;
import java.util.Map;

public class TestVoUtil {

	public static VersionQueryReq voVersionQueryReq(String appId, String host, int version) {
		VersionQueryReq vo = new VersionQueryReq();
		vo.setAppId(appId);
		vo.setCfgVersion(version);
		vo.setHostName(host);
		return vo;
	}

	public static VersionQueryResp voVersionQueryResp(int code, int version, String content) {
		VersionQueryResp vo = new VersionQueryResp();
		vo.setCode(code);
		vo.setVersion(version);
		vo.setConfig(content);
		return vo;
	}

	public static TransformNode voTransformNode(Integer code, Integer bizCode, Integer entryCode, String matchExp,
			String sourceKey, String sourceAlias, String targetKey, String targetAlias) {
		TransformNode vo = new TransformNode();
		vo.setBizCode(bizCode);
		vo.setCode(code);
		vo.setEntryCode(entryCode);
		vo.setMatchExp(matchExp);
		vo.setSourceAlias(sourceAlias);
		vo.setSourceKeyExp(sourceKey);
		vo.setTargetAlias(targetAlias);
		vo.setTargetKeyExp(targetKey);

		return vo;
	}

	public static Node voNode(Integer code, Integer bizCode, Integer entryCode, Integer seq, String seqMatchExp,
			String matchExp, String keyExp, String keyTransAlias, String inLogExp, String outLogExp, String errorLogExp,
			Integer threshold) {
		Node vo = new Node();
		vo.setBizCode(bizCode);
		vo.setCode(code);
		vo.setEntryCode(entryCode);
		vo.setErrorLogExp(errorLogExp);
		vo.setInLogExp(inLogExp);
		vo.setKeyExp(keyExp);
		vo.setKeyTransAlias(keyTransAlias);
		vo.setMatchExp(matchExp);
		vo.setOutLogExp(outLogExp);
		vo.setSeq(seq);
		vo.setSeqMatchExp(seqMatchExp);
		vo.setThreshold(threshold);

		return vo;
	}

	public static BizFactor voBizFactor(Integer code, String name, String description, Integer volume,
			Integer clientMode, Integer serverMode) {
		BizFactor vo = new BizFactor();
		vo.setCode(code);
		vo.setName(name);
		vo.setDescription(description);
		vo.setVolume(volume);
		vo.setClientMode(clientMode);
		vo.setServerMode(serverMode);
		return vo;
	}

	public static LogTarget voLogTarget(Integer bizCode, Boolean logFile, Boolean logArch, String level) {
		LogTarget vo = new LogTarget();
		vo.setBizCode(bizCode);
		vo.setLevel(level);
		vo.setLogArch(logArch);
		vo.setLogFile(logFile);
		return vo;
	}

	public static LogConfig voLogConfig(String fileUrl, String logType) {
		LogConfig vo = new LogConfig();
		vo.setFileUrl(fileUrl);
		vo.setLogType(logType);
		return vo;
	}

	public static CommonConfig voCommonConfig(String key, String value) {
		CommonConfig vo = new CommonConfig();
		vo.setCfgKey(key);
		vo.setCfgValue(value);
		return vo;
	}

	public static Entry voEntry(Integer code, String className, String methodName, String inputParamType,
			String description) {
		Entry vo = new Entry();
		vo.setClassName(className);
		vo.setCode(code);
		vo.setDescription(description);
		vo.setInputParamType(inputParamType);
		vo.setMethodName(methodName);
		return vo;
	}
//
//	public static RetryTaskVo voRetryTaskVo(String appKey, String strategyCode, String beanName, int status, int execTimes,
//			String uuid, String bizKey, String parameter, Date initExecTime, Date nextExecTime, Date timeout,
//			String serverHost, String serverIp, int defaultInterval, int isolateMode, int threads, int version) {
//		RetryTaskVo vo = new RetryTaskVo();
//		vo.setAppKey(appKey);
//		vo.setBeanName(beanName);
//		vo.setBizKey(bizKey);
//		vo.setDefaultInterval(defaultInterval);
//		vo.setExecTimes(execTimes);
//		vo.setInitExecTime(initExecTime);
//		vo.setIsolateMode(isolateMode);
//		vo.setNextExecTime(nextExecTime);
//		vo.setParameter(parameter);
//		vo.setServerHost(serverHost);
//		vo.setServerIp(serverIp);
//		vo.setStatus(status);
//		vo.setStrategyCode(strategyCode);
//		vo.setUuid(uuid);
//		vo.setThreads(threads);
//		vo.setTimeout(timeout);
//		vo.setVersion(version);
//		return vo;
//	}
//
//	public static RetryStrategy voRetryStrategy(String code, String appKey, int type, String expireTime, int maxRetry,
//			String name, boolean lifecycle, String retryInterval, String timePoints, String timeout) {
//		RetryStrategy vo = new RetryStrategy();
//		vo.setAppKey(appKey);
//		vo.setCode(code);
//		vo.setExpireTime(expireTime);
//		vo.setLifecycle(lifecycle);
//		vo.setMaxRetry(maxRetry);
//		vo.setName(name);
//		vo.setRetryInterval(retryInterval);
//		vo.setTimeout(timeout);
//		vo.setTimePoints(timePoints);
//		vo.setType(type);
//		return vo;
//	}
//
//	public static RetryConfig voRetryConfig(int taskBatchSize, int schedulerInterval, int sharedHystrixThreadPoolSize,
//			int maxThreadPoolSize) {
//		RetryConfig vo = new RetryConfig();
//		vo.setMaxThreadPoolSize(maxThreadPoolSize);
//		vo.setSchedulerInterval(schedulerInterval);
//		vo.setSharedHystrixThreadPoolSize(sharedHystrixThreadPoolSize);
//		vo.setTaskBatchSize(taskBatchSize);
//		return vo;
//	}

//	public static HystrixConfigVo getConfig(TaskContext ctx) {
//		int coresize = ctx.getTask().getThreads() > 0 ? ctx.getTask().getThreads()
//				: SdkCommonConstant.RETRY_HYSTRIX_DEFAULT_CORE_SIZE;
//
//		HystrixConfigVo ret = new HystrixConfigVo();
//		ret.setIsolateMode(ctx.getTask().getIsolateMode());
//		ret.setCommandName(ctx.getStrategy().getCode() + "_CMD");
//		ret.setCoreSize(coresize);
//		ret.setPoolKey(ctx.getTask().getThreads() > 0 ? ctx.getStrategy().getCode() : SdkCommonConstant.RETRY_THREAD_GROUP_KEY);
//		ret.setQueueSize(coresize);
//		return ret;
//	}
//
//	public static RetryLockVo voRetryLockVo(String appKey, String tag, int status, Date timeout, String serverHost,
//			String serverIp) {
//		RetryLockVo vo = new RetryLockVo();
//		vo.setAppKey(appKey);
//		vo.setServerHost(serverHost);
//		vo.setServerIp(serverIp);
//		vo.setStatus(status);
//		vo.setTag(tag);
//		vo.setTimeout(timeout);
//		return vo;
//	}
//
//	public static RetryCommandInfo voRetryCommandInfo(int command, long id, String uuid, RetryTaskVo taskInfo, long createTime) {
//		RetryCommandInfo vo = new RetryCommandInfo();
//		vo.setCommand(command);
//		vo.setId(id);
//		vo.setTaskInfo(taskInfo);
//		vo.setUuid(uuid);
//		vo.setCreateTime(createTime);
//		return vo;
//	}
//
//	public static TaskLogVo voTaskLogVo(String appKey, String uid, String bizKey, String strategyCode, int status,
//			String registerTime, String nextRetryTime, String serverHost, String serverIp, String retCode,
//			String retMsg, String operator) {
//		TaskLogVo vo = new TaskLogVo();
//		vo.setAppKey(appKey);
//		vo.setBizKey(bizKey);
//		vo.setEventTime(DateTimeHelper.now());
//		vo.setExecTimes(1);
//		vo.setNextRetryTime(nextRetryTime);
//		vo.setOperator(operator);
//		vo.setRegisterTime(registerTime);
//		vo.setRetCode(retCode);
//		vo.setRetMsg(retMsg);
//		vo.setServerHost(serverHost);
//		vo.setServerIp(serverIp);
//		vo.setStatus(status);
//		vo.setStrategyCode(strategyCode);
//		vo.setUuid(uid);
//
//
//		return vo;
//	}

	public static MonitorRulesData voMonitorRulesData(AppConfig cfg) {
		MonitorRulesData vo = new MonitorRulesData();
		vo.setAppCfg(cfg);
		vo.setVersion(cfg.getVersion());
		Map<String, Map<Integer, BizEntryRule>> entryMap = VersionDataBuilder.buildEntryMap(cfg);
		vo.setEntryMap(entryMap);

		Map<String, NodeExpressionCommand> expMap = VersionDataBuilder.buildExpressionMap(cfg);
		vo.setExpCache(expMap);

		Map<String, Object> dataCache = VersionDataBuilder.buildDataCache(cfg);
		vo.setDataCache(dataCache);
		
		// 自定义的node、tnode
		vo.setCustomizeNodeMap(VersionDataBuilder.buildCustomizeNodeMap(cfg));
		vo.setCustomizeTnodeMap(VersionDataBuilder.buildCustomizeTnodeMap(cfg));

		
		return vo;
	}

	public static MonitorLogMessage voMonitorLogMessage(String appId, Integer version, Integer bizCode, long timeStamp,
			long elaspedTime, String threadName, String hostName) {
		MonitorLogMessage vo = new MonitorLogMessage(SdkCommonConstant.LOG_TYPE_MONITOR);
		vo.setAppId(appId);
		vo.setBizCode(bizCode);
		vo.setElaspedTime(elaspedTime);
		vo.setHostName(hostName);
		vo.setThreadName(threadName);
		vo.setTimeStamp(timeStamp);
		vo.setCfgVersion(version);
		return vo;
	}


	public static BizEntryRule voBizEntryRule(List<Node> nodelist, List<TransformNode> transformNodeList) {
		BizEntryRule vo = new BizEntryRule();
		vo.setNodeList(nodelist);
		vo.setTransformNodeList(transformNodeList);
		return vo;
	}

	public static AppConfig voAppConfig(String appId, Boolean lc, int version) {
		AppConfig vo = new AppConfig();
		vo.setAppId(appId);
		vo.setVersion(version);
		return vo;
	}

	public static AppRegConfig voAppRegConfig(String hostname, String ip, String sdkVersion) {
		AppRegConfig vo = new AppRegConfig();
		vo.setHostName(hostname);
		vo.setIp(ip);
		vo.setSdkVersion(sdkVersion);
		return vo;
	}


	public static MonitorLogUnitCommand voMonitorLogCommand(int code, String key, String inLog, String outLog,
			String exceptionLog) {
		MonitorLogUnitCommand vo = new MonitorLogUnitCommand();
		return vo;
	}

	public static TransformLogUnitCommand voTransformLogCommand(int code, String srcKey, String targetKey) {
		TransformLogUnitCommand vo = new TransformLogUnitCommand();
		vo.setCode(code);
		vo.setSrcKey(srcKey);
		vo.setTargetKey(targetKey);
		return vo;
	}

//
//	public static RetryTaskCancelCmdVo voRetryTaskCancelCmdVo(String appKey, String uuid, String dbTag) {
//		RetryTaskCancelCmdVo vo = new RetryTaskCancelCmdVo();
//		vo.setAppKey(appKey);
//		vo.setUuid(uuid);
//		vo.setDbTag(dbTag);
//		return vo;
//	}

}
