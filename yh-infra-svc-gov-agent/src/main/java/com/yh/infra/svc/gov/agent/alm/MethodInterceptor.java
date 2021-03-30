/**
 * 
 */
package com.yh.infra.svc.gov.agent.alm;

import java.lang.reflect.Method;

/**
 * @author luchao 2020-06-11
 *
 */
public interface MethodInterceptor {
	public void enter(Class<?> clazz, Method method, Object[] args);

	public void exit(Method method, Object[] args, Object returned, Throwable throwable);
}
