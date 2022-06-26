package com.sp.infra.svc.gov.sdk.alm.interceptor;

import com.sp.infra.svc.gov.agent.alm.MethodInterceptor;
import com.sp.infra.svc.gov.sdk.alm.context.InvokeContext;
import com.sp.infra.svc.gov.sdk.alm.context.MonitorGlobalContext;
import com.sp.infra.svc.gov.sdk.alm.context.ThreadContext;
import com.sp.infra.svc.gov.sdk.alm.service.MonitorService;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.util.ClazzUtil;
import com.sp.infra.svc.gov.sdk.util.CollectionUtils;
import com.sp.infra.svc.gov.sdk.util.MethodWrapper;
import com.sp.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Deque;

/**
 * 用于拦截方法的实际执行类。
 * 所有的处理逻辑，应该是在MethodAdvice中的。
 * 由于MethodAdvice是作为字节码，插入到原始类中的。
 * 所以logger等的使用， 打印出的类和位置，以原始类的。不利于排查。
 * 所以单独抽到一个独立的类中。
 *
 * @author luchao
 * @date 2021/4/25 8:27 下午
 */
public class MethodInterceptorImpl implements MethodInterceptor {
	public static final Logger logger = LoggerFactory.getLogger(MethodInterceptorImpl.class.getName());
	
	private static final int SUCCESS = 0;
	private static final int RETURN = 1;

	/**
	 * 方法调用前的工作， 只有监控。
	 * clazz是instance的class。有可能跟method的所属class不一致。
	 * 
	 * @param clazz 
	 * @param method
	 * @param args
	 */
	@Override
	public void enter(Class<?> clazz, Method method, Object[] args) {
		String clzName = clazz.getName(); 
		Class<?> defClz = method.getDeclaringClass();
		String methodName = method.getName();
		InvokeContext currentIc = null;
		MonitorGlobalContext context = null;
		try {
			logger.info("MethodAdvice.enter. advice method : {} , {}", clzName, methodName);
			
			BeanRegistry br = BeanRegistry.getInstance();
			// 取得同一个线程内的拦截信息堆栈。
			Deque<InvokeContext> icStack = ThreadContext.getStackTrace();
			
			context = br.getBean(MonitorGlobalContext.class);
			// 这个地方不需要判断context的NPE， 因为MonitorGlobalContext 是在client 的init的时候注册的。
			//  如果client没有init，不会进行任何方法的拦截。就不会进入到这儿。
			currentIc = new InvokeContext(context.getCurrentVersion());
			currentIc.setArgs(args);
			currentIc.setTargetClass(clazz);
			currentIc.setMethod(new MethodWrapper(method));
			currentIc.setClzEntry(ClazzUtil.getMethodFullName(clazz, currentIc.getMethod()));
			currentIc.setDefClzEntry(ClazzUtil.getMethodFullName(defClz, currentIc.getMethod()));

			// 尝试取得取得parent的ic
			// 返回第一个元素，但不删除
			InvokeContext parent = icStack.peekFirst();
			if (parent == null) {
				if (logger.isDebugEnabled()) 
					logger.debug("Enter.1st call of the thread. " + Thread.currentThread().getId());
				// 新线程， 第一次进入。
				setSeq(currentIc, null);
			} else {
				//检查版本号是否匹配，不匹配说明有根据上一个版本的形成的 拦截 数据， 清空。
				if (parent.getVersion() != context.getCurrentVersion()) {
					logger.warn("Enter.clear the stack, call of the thread is the 1st. " + Thread.currentThread().getId());
					icStack.clear();
					// 新线程， 第一次进入。
					setSeq(currentIc, null);
				} else {
					if (logger.isDebugEnabled()) 
						logger.debug("Enter.NON-1st call of the thread. " + Thread.currentThread().getId());
					setSeq(currentIc, parent);
				}
			}
			// 即使不需要处理，也要加入stack。否则，exit里面pop出来的就是另一个方法的ic了。 enter压入，exit弹出，要对应。
			icStack.push(currentIc);

			MonitorService service = br.getBean(MonitorService.class);
			currentIc.setDeal(service.preFetchData(currentIc));
			if (logger.isDebugEnabled()) 
				logger.debug("Enter.finished the monitor task. advice method : {} , {}  . deal result:{}", clazz.getName(), method.getName(), currentIc.isDeal());
		} catch (Exception e) {
			//这个失败  不是探索任务的。 是 监控的。
			logger.error("Enter.system error during monitor process in enter. " + clzName + "." + methodName, e);
			// 如果发生异常，本方法的整个监控过程要放弃
			if(currentIc != null) {
				currentIc.setDeal(false);
			}

		}
	}

	/**
	 * 设置transid, seq，类似于APM的trace ID, span ID。
	 * 
	 */
	private static void setSeq(InvokeContext ic, InvokeContext parent) {
		if (parent == null) {
			//本线程的第一个监控方法。第一个span
			ic.setTransId(StringUtils.getTransId());
			ic.setSeq(1);
			ic.setLastSeq(1);
		} else {
			// 有parent span的时候， seq加1， lastseq同样。
			ic.setTransId(parent.getTransId());
			ic.setSeq(parent.getLastSeq() + 1);
			ic.setLastSeq(ic.getSeq());
		}
	}
	
	/**
	 * 执行方法调用完成后的操作， 包括监控和探索
	 * 
	 * @param method
	 * @param args
	 * @param returned
	 * @param throwable
	 */
	@Override
	public void exit(Method method, Object[] args, Object returned, Throwable throwable) {
		String methodName = method.getName();
		Deque<InvokeContext> icStack = null;
		InvokeContext currentIc = null; //NOSONAR
		try {
			if (logger.isDebugEnabled())  //NOSONAR
				logger.debug("Exit. advice method : " + method.toGenericString()); //NOSONAR
			
			// 验证数据
			if (validate(method) == RETURN)
				return;
			
			icStack = ThreadContext.getStackTrace();
			currentIc = icStack.pop();
			
			// 处理监控任务
			doMonitorJobAfterCall(icStack, currentIc, args, returned, throwable);
		} catch (Throwable e) { //NOSONAR
			logger.error("Exit. system error in exit. " + method.getDeclaringClass() + "." + methodName, e);
		}
	}

	/**
	 * 执行监控任务。
	 * 
	 * @param icStack
	 * @param currentIc
	 * @param args
	 * @param returned
	 * @param throwable
	 */
	private static void doMonitorJobAfterCall(Deque<InvokeContext> icStack, InvokeContext currentIc, Object[] args, Object returned, Throwable throwable) {
		if (!icStack.isEmpty()) {
			InvokeContext parent = icStack.peekFirst();
			parent.setLastSeq(currentIc.getLastSeq()); // 将本身lastseq计入parent。
		}
		if (!currentIc.isDeal()) {
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("Exit. do not need to deal with this method. " + currentIc.getClzEntry()); //NOSONAR
		} else {
			BeanRegistry br = BeanRegistry.getInstance();
			MonitorService service = br.getBean(MonitorService.class);
			currentIc.setArgs(args);
			currentIc.setReturned(returned);
			currentIc.setThrowable(throwable);
			service.postHandle(currentIc);
			if (logger.isDebugEnabled()) //NOSONAR
				logger.debug("Exit. finished monitor process. " + currentIc.getClzEntry()); //NOSONAR
		}
	}
	
	/**
	 * 校验类和方法，是否可以正常处理。
	 * 
	 * @param method
	 * @return
	 */
	private static int validate(Method method) {
		Class<?> clazz = method.getDeclaringClass();
		BeanRegistry br = BeanRegistry.getInstance();
		MonitorGlobalContext context = br.getBean(MonitorGlobalContext.class);
		
		Deque<InvokeContext> icStack = ThreadContext.getStackTrace();
		if (CollectionUtils.isEmpty(icStack)) {
			logger.error("Exit. the stack is empty.");
			return RETURN;
		}

		InvokeContext ic = icStack.peekFirst();
		
		/**
		 * 检查版本号是否匹配<br/>
		 * 可能的情况是：<br/>
		 * 1. pre的时候，要监控， 所以stack放入一个IC。但是配置更新了， 这个方法不拦截了，不会过post方法了。 这样
		 * stack中就多了一个数据。<br/>
		 * 2. 上一个情况的反向。post方法会发现没有对应的IC数据。
		 */
		if (ic.getVersion() != context.getCurrentVersion()) {
			logger.warn("Exit. different version. clear the stack. {}, {}", ic.getVersion(), context.getCurrentVersion());
			icStack.clear();
			return RETURN;
		}

		String exitEntry = ClazzUtil.getMethodFullName(clazz, new MethodWrapper(method));
		//检查 enter/exit是不是同一个方法调用。 
		if (! exitEntry.equals(ic.getDefClzEntry())) {
			logger.error("Exit. enter/exit entries are different. " + exitEntry + " -- " + ic.getDefClzEntry());
			icStack.clear();
			return RETURN;
		}
		
		return SUCCESS;
	}

}
