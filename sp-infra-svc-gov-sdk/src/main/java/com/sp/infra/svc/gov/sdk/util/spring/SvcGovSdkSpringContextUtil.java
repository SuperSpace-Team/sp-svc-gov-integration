/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author luchao 2020-04-22
 *
 */

public class SvcGovSdkSpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SvcGovSdkSpringContextUtil.applicationContext = applicationContext;
	}
	public ApplicationContext getApplicationContext() {
		return SvcGovSdkSpringContextUtil.applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		if (! applicationContext.containsBean(name))
			return null;
		return (T) applicationContext.getBean(name);
	}
}
