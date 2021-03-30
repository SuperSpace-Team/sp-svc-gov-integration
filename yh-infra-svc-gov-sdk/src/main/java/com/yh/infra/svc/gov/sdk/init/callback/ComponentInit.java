/**
 * 
 */
package com.yh.infra.svc.gov.sdk.init.callback;

import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;

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
	public boolean init(AppRegContext appRegContext);

	/**
	 * 从IOC容器清除组件
	 * @param appRegContext
	 */
	public void clean(AppRegContext appRegContext);
}
