package com.yh.infra.svc.gov.sdk.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

public class DummyProceedingJoinPoint implements ProceedingJoinPoint {

	private Object[] args;
	private Signature sig;
	private Throwable exception;
	private Object returnObj;
	
	public DummyProceedingJoinPoint(Object[] args, Signature sig, Throwable exception, Object returnObj) {
		this.args = args;
		this.sig = sig;
		this.exception = exception;
		this.returnObj = returnObj;
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
	public Object getThis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getArgs() {
		return args;
	}

	@Override
	public Signature getSignature() {
		return sig;
	}

	@Override
	public SourceLocation getSourceLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StaticPart getStaticPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set$AroundClosure(AroundClosure arc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object proceed() throws Throwable {
		if (exception == null)
			return returnObj;
		else
			throw exception;
	}

	@Override
	public Object proceed(Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
