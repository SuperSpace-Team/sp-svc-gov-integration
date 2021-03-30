///**
// *
// */
//package com.yh.infra.svc.gov.sdk.config;
//
//import org.springframework.beans.factory.annotation.Value;
//
//import com.yh.infra.svc.gov.sdk.constant.Constants;
//
///**
// * @author luchao  2020-06-22
// *
// */
//public class AlmProperties {
//	@Value("${svc-gov-sdk.alm.enabled:true}")
//	private boolean enabled = true;
//
//	/** 监控/转换日志**/
//	@Value("${svc-gov-sdk.alm.logSendPollingInterval:1}")
//	private int logSendPollingInterval = Constants.PG_LOG_SEND_POLLING_INTERVAL;
//	@Value("${svc-gov-sdk.alm.logCacheCapacity:10000}")
//	private int logCacheCapacity = Constants.PG_LOG_CACHE_SIZE_DEFAULT;
//	@Value("${svc-gov-sdk.alm.logBatchSize:200}")
//	private int logBatchSize = Constants.PG_LOG_BATCH_MAX_SIZE;
//	@Value("${svc-gov-sdk.alm.logSenderThread:5}")
//	private int logSenderThread = Constants.PG_LOG_SENDER_THREAD_MAX_SIZE;
//	/** 熔断管理 **/
//	@Value("${svc-gov-sdk.alm.fuseCoolTimeout:30}")
//	private int fuseCoolTimeout = Constants.PG_FUSE_COOL_TIMEOUT;
//	@Value("${svc-gov-sdk.alm.fuseThreshold:60}")
//	private int fuseThreshold = Constants.PG_FUSE_THRESHOLD;
//	@Value("${svc-gov-sdk.alm.fuseCheckWindow:30}")
//	private int fuseCheckWindow = Constants.PG_FUSE_CHECK_TIME_WINDOW;
//	@Value("${svc-gov-sdk.alm.fuseMsgQueueMaxSize:10000}")
//	private int fuseMsgQueueMaxSize = Constants.PG_FUSE_MSG_MAX_SIZE;
//
//	public boolean isEnabled() {
//		return enabled;
//	}
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//	public int getLogSendPollingInterval() {
//		return logSendPollingInterval;
//	}
//	public void setLogSendPollingInterval(int logSendPollingInterval) {
//		this.logSendPollingInterval = logSendPollingInterval;
//	}
//	public int getLogCacheCapacity() {
//		return logCacheCapacity;
//	}
//	public void setLogCacheCapacity(int logCacheCapacity) {
//		this.logCacheCapacity = logCacheCapacity;
//	}
//	public int getLogBatchSize() {
//		return logBatchSize;
//	}
//	public void setLogBatchSize(int logBatchSize) {
//		this.logBatchSize = logBatchSize;
//	}
//	public int getLogSenderThread() {
//		return logSenderThread;
//	}
//	public void setLogSenderThread(int logSenderThread) {
//		this.logSenderThread = logSenderThread;
//	}
//	public int getFuseCoolTimeout() {
//		return fuseCoolTimeout;
//	}
//	public void setFuseCoolTimeout(int fuseCoolTimeout) {
//		this.fuseCoolTimeout = fuseCoolTimeout;
//	}
//	public int getFuseThreshold() {
//		return fuseThreshold;
//	}
//	public void setFuseThreshold(int fuseThreshold) {
//		this.fuseThreshold = fuseThreshold;
//	}
//	public int getFuseCheckWindow() {
//		return fuseCheckWindow;
//	}
//	public void setFuseCheckWindow(int fuseCheckWindow) {
//		this.fuseCheckWindow = fuseCheckWindow;
//	}
//	public int getFuseMsgQueueMaxSize() {
//		return fuseMsgQueueMaxSize;
//	}
//	public void setFuseMsgQueueMaxSize(int fuseMsgQueueMaxSize) {
//		this.fuseMsgQueueMaxSize = fuseMsgQueueMaxSize;
//	}
//	@Override
//	public String toString() {
//		return "AlmProperties [enabled=" + enabled + ", logSendPollingInterval=" + logSendPollingInterval + ", logCacheCapacity=" + logCacheCapacity
//				+ ", logBatchSize=" + logBatchSize + ", logSenderThread=" + logSenderThread + ", fuseCoolTimeout=" + fuseCoolTimeout + ", fuseThreshold="
//				+ fuseThreshold + ", fuseCheckWindow=" + fuseCheckWindow + ", fuseMsgQueueMaxSize=" + fuseMsgQueueMaxSize + "]";
//	}
//
//}
