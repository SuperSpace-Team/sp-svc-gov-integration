package com.yh.infra.svc.gov.sdk.alm.config;


import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;

/**
 * @author luchao
 * @date 2021/4/25 5:58 下午
 */
public class AlmConfig {
	/** 监控/转换日志**/
	private int logSendPollingInterval = SdkCommonConstant.PG_LOG_SEND_POLLING_INTERVAL;
	private int logCacheCapacity = SdkCommonConstant.PG_LOG_CACHE_SIZE_DEFAULT;
	private int logBatchSize = SdkCommonConstant.PG_LOG_BATCH_MAX_SIZE;
	private int logSenderThread = SdkCommonConstant.PG_LOG_SENDER_THREAD_MAX_SIZE;
	/** 熔断管理 **/
	private int fuseCoolTimeout = SdkCommonConstant.PG_FUSE_COOL_TIMEOUT;
	private int fuseThreshold = SdkCommonConstant.PG_FUSE_THRESHOLD;
	private int fuseCheckWindow = SdkCommonConstant.PG_FUSE_CHECK_TIME_WINDOW;
	private int fuseMsgQueueMaxSize = SdkCommonConstant.PG_FUSE_MSG_MAX_SIZE;

	public int getLogSendPollingInterval() {
		return logSendPollingInterval;
	}
	public void setLogSendPollingInterval(int logSendPollingInterval) {
		this.logSendPollingInterval = logSendPollingInterval;
	}
	public int getLogCacheCapacity() {
		return logCacheCapacity;
	}
	public void setLogCacheCapacity(int logCacheCapacity) {
		this.logCacheCapacity = logCacheCapacity;
	}
	public int getLogBatchSize() {
		return logBatchSize;
	}
	public void setLogBatchSize(int logBatchSize) {
		this.logBatchSize = logBatchSize;
	}
	public int getFuseCoolTimeout() {
		return fuseCoolTimeout;
	}
	public void setFuseCoolTimeout(int fuseCoolTimeout) {
		this.fuseCoolTimeout = fuseCoolTimeout;
	}
	public int getFuseThreshold() {
		return fuseThreshold;
	}
	public void setFuseThreshold(int fuseThreshold) {
		this.fuseThreshold = fuseThreshold;
	}
	public int getFuseCheckWindow() {
		return fuseCheckWindow;
	}
	public void setFuseCheckWindow(int fuseCheckWindow) {
		this.fuseCheckWindow = fuseCheckWindow;
	}
	public int getFuseMsgQueueMaxSize() {
		return fuseMsgQueueMaxSize;
	}
	public void setFuseMsgQueueMaxSize(int fuseMsgQueueMaxSize) {
		this.fuseMsgQueueMaxSize = fuseMsgQueueMaxSize;
	}
	public int getLogSenderThread() {
		return logSenderThread;
	}
	public void setLogSenderThread(int logSenderThread) {
		this.logSenderThread = logSenderThread;
	}
}
