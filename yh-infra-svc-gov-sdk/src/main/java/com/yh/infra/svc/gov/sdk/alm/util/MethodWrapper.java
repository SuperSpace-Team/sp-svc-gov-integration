/**
 * 
 */
package com.yh.infra.svc.gov.sdk.alm.util;

import java.lang.reflect.Method;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
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
