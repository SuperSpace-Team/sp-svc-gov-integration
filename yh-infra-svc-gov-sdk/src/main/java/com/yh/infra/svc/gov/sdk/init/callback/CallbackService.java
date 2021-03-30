/**
 * 
 */
package com.yh.infra.svc.gov.sdk.init.callback;

import java.util.Map;

/**
 * 回调接口，各个模块需要实现，并注册到 sdk的自定义IOC容器-beanregistry中
 * 
 * @author luchao  2019-02-21
 *
 */
public interface CallbackService {

	/**
	 * 返回功能模块的名称。 需要唯一。
	 * @return
	 */
	String getCallbackName();

	/**
	 * 验证服务端传来的  版本/运行时  数据是否可用。如果不可用，需要返回false
	 * @param data
	 * @return
	 */
	boolean validate(Map<String, Object> data);

	/**
	 * 使用服务端传来的 版本/运行时  数据
	 * 
	 * @param data
	 */
	void process(Map<String, Object> data);
	
}
