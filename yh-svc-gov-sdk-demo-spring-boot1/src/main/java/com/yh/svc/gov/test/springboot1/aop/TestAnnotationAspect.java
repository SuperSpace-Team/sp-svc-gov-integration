/**
 * 
 */
package com.yh.svc.gov.test.springboot1.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 仅限于在返回值是BaseResponseEntity的方法中使用。
 * 
 * @author luchao 2018-12-31
 *
 */
@Aspect
@Component
public class TestAnnotationAspect {
	
	@SuppressWarnings("rawtypes")
	@Around(value = "@annotation(pgEx)")
	public Object around(ProceedingJoinPoint pjp, TestAnnotation pgEx) throws Throwable {
		System.out.println("======enter the TestAnnotationAspect!!!!   " + pgEx.value());  //NOSONAR
		return pjp.proceed();
	}
}