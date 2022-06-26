package com.sp.infra.svc.gov.sdk.alm.service;

import com.sp.infra.svc.gov.sdk.alm.command.LogMessage;
import com.sp.infra.svc.gov.sdk.alm.command.ResponseStatusCommand;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.net.HttpClientProxy;
import com.sp.infra.svc.gov.sdk.util.CollectionUtils;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 所有需要使用熔断机制的 方法调用。 都需要在本service中做一个方法，以代理模式使用。
 *
 * @author luchao
 * @date 2021/4/25 8:31 下午
 */
public class FusingProxyService {
	private static final Logger logger = LoggerFactory.getLogger(FusingProxyService.class);
	
	private MonitorGlobalContext context;
    
    private HttpClientProxy httpClient;
    
	public FusingProxyService(MonitorGlobalContext ctx, HttpClientProxy httpClient) {
		context = ctx;
		this.httpClient = httpClient;
	}
	
	
	/**
	 * 日志入队列
	 * 
	 * @param log
	 */
	public void addLog(LogMessage log) {
		// 全链路日志， 有熔断控制。
		if ((log.getLogType() == SdkCommonConstant.LOG_TYPE_MONITOR) && context.isBrokerOpen()) {
			logger.warn("log sender is in broken status.");
			return;
		}
		context.addLog(log);
	}

	/**
	 * 发送日志。指定日志类型
	 * 
	 * @param logList
	 * @return
	 */
	public void sendLog(List<? extends LogMessage> logList, int logType) {
		if (logger.isDebugEnabled())
			logger.debug("received log command list. size: " + logList.size());

		if (CollectionUtils.isEmpty(logList))
			return;
		
		// 监控日志，熔断时不发送
		if ((logType == SdkCommonConstant.LOG_TYPE_MONITOR) && context.isBrokerOpen()) {
			context.addStatus(ResponseStatusCommand.reject());
			return;
		}
		
		sendLogToArch(logList, logType);
	}
	
	/**
	 * 实际发送日志数据
	 * 
	 * @param logList
	 * @param logType
	 */
	private void sendLogToArch(List<? extends LogMessage> logList, int logType) {
		if (logger.isDebugEnabled())
			logger.debug("begin to send message command. size : " + logList.size());

		// 取得日志发送的目的地url
		String url = context.getCommonCfg(SdkCommonConstant.PG_COMMON_CFG_SYS_LOG_ARCH);
		if (StringUtils.isEmpty(url)) {
			logger.error("LOG-ARCHIVE url is emtpy.");
			return;
		}
		
		// 构建发送的字符串
		String msgBody = JsonUtil.writeValueSafe(logList);
		if (StringUtils.isEmpty(msgBody)) {
			logger.error("cannot serialize object to json.");
			return;
		}
		if (logger.isDebugEnabled())
			logger.debug("begin to send message. url {}, data: {}", url, msgBody);

		// 暂定只retry一次。以retry的最终结果作为熔断的 依据。
		int retry = 2;
		while (retry > 0) {
			Map<String, String> retMap = httpClient.postJson(url, msgBody, SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
			if (checkResponse(retMap.get("error"), retMap.get("status"), retMap.get("result"))) {
				updateFusingStatus(ResponseStatusCommand.success(), logType);
				return;
			}
			retry--;
		}
		logger.error("send fail: " + url);
		updateFusingStatus(ResponseStatusCommand.fail(), logType);

	}
	
	/**
	 ** 检查反馈。
	 * @param error
	 * @param status
	 * @param result
	 * @return
	 */
	private boolean checkResponse(String error, String status, String result) {
		if (logger.isDebugEnabled())
			logger.debug("sending result: {}, {}, {} ", status, result, error);
		if (StringUtils.isEmpty(error)) {
			BaseResponseEntity bre = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
			if (bre == null) {
				logger.error("JSON parse fail. {} " + result);
			} else {
				boolean ret = SdkCommonConstant.HTTP_STATUS_OK.equals(status) && bre.getIsSuccess();
				if (ret) {
					return true;
				} else {
					logger.error("send failed. sending result: {}, {}, {} ", status, result, error);
				}
			}
		} else {
			logger.error("status:{}, error:{}", status, error);
		}
		return false;
	}
	
	/**
	 * 更新发送状态。
	 * 
	 * @param status
	 * @param logType
	 */
	private void updateFusingStatus(ResponseStatusCommand status, int logType) {
		// 如果是监控日志， 需要更新 发送状态。
		if (logType == SdkCommonConstant.LOG_TYPE_MONITOR)
			context.addStatus(status);
	}
}
