package com.yh.svc.gov.test.springboot1.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yh.svc.gov.test.springboot1.manager.BaseManager;

public abstract class AbstractManagerImpl<T> implements BaseManager<T> {
	private static final Logger logger = LoggerFactory.getLogger(AbstractManagerImpl.class);
	@Override
	public void genericTypeTest(T t) {
		System.out.println("received the  class :" + t.getClass().getName());
	}
	
	public void service(T t) {
		logger.info("enter the  AbstractManagerImpl!!!!");
		(new Exception()).printStackTrace();
		internalCall(t);
	}
    abstract public void internalCall(T t);

}
