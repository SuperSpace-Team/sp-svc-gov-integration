/**
 * 
 */
package com.sp.infra.svc.gov.sdk.init.callback;

import com.sp.infra.svc.gov.sdk.init.context.AppRegContext;

/**
 * 组件初始化接口
 * @author luchao  2020-04-24
 *
 */
public interface ComponentInit {
	/**
	 * 组件初始化加入IOC容器
	 * @param appRegContext
	 * @return
	 */
	boolean init(AppRegContext appRegContext);

	/**
	 * 从IOC容器清除组件
	 * @param appRegContext
	 */
	void clean(AppRegContext appRegContext);
}
