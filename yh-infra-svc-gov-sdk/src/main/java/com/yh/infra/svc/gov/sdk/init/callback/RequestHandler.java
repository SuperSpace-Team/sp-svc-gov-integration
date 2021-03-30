package com.yh.infra.svc.gov.sdk.init.callback;

/**
 * 定时请求处理器
 * 在发送30s心跳消息时会被调用
 * @author luchao 2020-04-14
 *
 */
public interface RequestHandler {
	String getKey();
	String getValue();
}
