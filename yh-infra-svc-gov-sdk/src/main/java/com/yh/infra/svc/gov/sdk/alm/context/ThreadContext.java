/**
 * 
 */
package com.yh.infra.svc.gov.sdk.alm.context;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 每个线程的上下文。
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:24 下午
 */
public class ThreadContext {
	private static final ThreadLocal<Deque<InvokeContext>> stackTrace = new ThreadLocal<>();

	private ThreadContext(){
		throw new IllegalStateException("Utility class");
	}

	public static Deque<InvokeContext> getStackTrace() {
		Deque<InvokeContext> s = stackTrace.get();
		if (s == null) {
			stackTrace.set(new LinkedList<InvokeContext>());
			s = stackTrace.get();
		}
		return s;
	}
}
