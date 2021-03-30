package com.yh.svc.gov.test.springboot1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/***
 * @Class: LogAspect
 * @Description: 日志统一处理类
 * @author: angus
 * @date: 2019/5/8 19:14
 */

@Aspect
@Component
@Order(1)
public class LogAspect {
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

	/**
	 * 指定切点 匹配
	 */
	@Pointcut("execution(* com.yh.svc.gov.test.springboot1.controller.Entry2Controller.*(..))")
	public void webLog() {
	}

	/**
	 * 前置通知，方法调用前被调用
	 * 
	 * @param joinPoint
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		logger.info("-----方法所在包:{} ", signature.getDeclaringTypeName() + "." + signature.getName());
	}

}
