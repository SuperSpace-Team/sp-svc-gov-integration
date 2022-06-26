/**
 * 
 */
package com.sp.infra.svc.gov.sdk.alm.util;

import java.lang.reflect.Method;

/**
 * @author luchao
 * @date 2021/4/25 8:32 下午
 */
public class MethodWrapper {
	private Method method;
	public MethodWrapper(Method method) {
		this.method = method;
	}
	
	public String getName() {
		return method.getName();
	}
	
	public Class<?>[] getParameterTypes() {
		return method.getParameterTypes();
	}

	public Method getMethod() {
		return method;
	}
	
}
