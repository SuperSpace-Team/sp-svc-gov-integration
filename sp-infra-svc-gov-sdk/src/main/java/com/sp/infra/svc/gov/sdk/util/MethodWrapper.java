/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util;

import java.lang.reflect.Method;

/**
 * @author luchao  2019-03-15
 *
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
