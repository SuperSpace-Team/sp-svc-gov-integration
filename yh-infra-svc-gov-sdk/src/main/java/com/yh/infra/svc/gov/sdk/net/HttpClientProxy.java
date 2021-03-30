/**
 * 
 */
package com.yh.infra.svc.gov.sdk.net;

import org.apache.http.Header;

import java.util.Map;

/**
 * @description: Http代理
 * @author: luchao
 * @date: Created in 2021/3/24 4:53 下午
 */
public interface HttpClientProxy {
	/**
	 * post data is in JSON format.
	 * 
	 * @param url
	 * @param content
	 * @param timeout
	 * @param headers
	 * @return
	 */
	Map<String, String> postJson(String url, String content, int timeout, Header headers[]);
	
}
