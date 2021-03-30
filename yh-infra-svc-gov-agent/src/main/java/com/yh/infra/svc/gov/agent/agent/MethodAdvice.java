
package com.yh.infra.svc.gov.agent.agent;

import java.lang.reflect.Method;

import com.yh.infra.svc.gov.agent.alm.MethodInterceptor;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

/**
 * 用于拦截 静态/非静态方法的类。
 * 
 * 
 * @author luchao  2019-04-15
 *
 */
public class MethodAdvice  {
	
	private MethodAdvice() {
	}

	@Advice.OnMethodEnter
	public static void enter(
			@Advice.This(optional = true, readOnly = true) Object thisObj,
			@Advice.Origin Method method,
			@Advice.AllArguments Object[] args) {

		try {
			Class<?> clz = method.getDeclaringClass();  //本instance的类型， 先设定为方法的定义类
			if (thisObj != null)
				clz = thisObj.getClass();  // 如果不是static方法， 则根据instance的类型进行重置。
			
			MethodInterceptor mi = AgentBeanRegistry.getBean(MethodInterceptor.class);
			if (mi != null)
				mi.enter(clz, method, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Advice.OnMethodExit(onThrowable = Throwable.class)
	public static void exit(
			@Advice.Origin Method method, 
			@Advice.AllArguments Object[] args,
			@Advice.Return(typing = Assigner.Typing.DYNAMIC) Object returned, 
			@Advice.Thrown Throwable throwable) {
		try {
			MethodInterceptor mi = AgentBeanRegistry.getBean(MethodInterceptor.class);
			if (mi != null)
				mi.exit(method, args, returned, throwable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
