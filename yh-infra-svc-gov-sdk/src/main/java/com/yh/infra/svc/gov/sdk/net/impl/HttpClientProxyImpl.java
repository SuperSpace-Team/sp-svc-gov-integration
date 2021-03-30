/**
 * 
 */
package com.yh.infra.svc.gov.sdk.net.impl;

import com.yh.infra.svc.gov.sdk.command.HttpClientResponse;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Http代理实现类
 * @author: luchao
 * @date: Created in 2021/3/24 4:53 下午
 */
public class HttpClientProxyImpl implements HttpClientProxy {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientProxyImpl.class);

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param content
	 * @param timeout
	 * @param headers
	 * @return
	 */
	@Override
	public Map<String, String> postJson(String url, String content, int timeout, Header headers[]) {
		// 不需要try catch， 因为内层已经做了。
		HttpClientResponse ret = HttpJsonClient.postJsonData(url, content, timeout, headers);
		
		if (logger.isDebugEnabled()) {
			logger.debug("request: {}", content);
			logger.debug("response: {}", ret);
		}
		
		return convertMap(ret);
	}

	/**
	 * 转换响应报文体
	 * @param httpResponse
	 * @return
	 */
	private Map<String, String> convertMap(HttpClientResponse httpResponse) {
		Map<String, String>  resultMap= new HashMap<>();
        resultMap.put("status", httpResponse.getStatusCode() == null? null:Integer.toString(httpResponse.getStatusCode()));
        resultMap.put("result", httpResponse.getEntity());
        resultMap.put("error", httpResponse.getError());
		return resultMap;
	}

}
