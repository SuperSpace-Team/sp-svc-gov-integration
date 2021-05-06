/**
 * 
 */
package com.yh.infra.svc.gov.sdk.util;

import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author luchao  2020-04-29
 *
 */
public class DummySignature implements MethodSignature {

	private Method m;
	public DummySignature(Method method) {
		m = method;
	}
	
	@Override
	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toLongString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getModifiers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Class getDeclaringType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeclaringTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class[] getParameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class[] getExceptionTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getReturnType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Method getMethod() {
		return m;
	}

}
