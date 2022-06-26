package com.sp.infra.svc.gov.metrics.aspect;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.sp.infra.svc.gov.metrics.annotation.MetricsLog;
import com.sp.infra.svc.gov.metrics.constant.Constants;
import com.sp.infra.svc.gov.metrics.context.ExpressionCache;
import com.sp.infra.svc.gov.metrics.context.MetricsContext;
import com.sp.infra.svc.gov.metrics.meter.MetricsHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class MetricsLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(MetricsLogAspect.class);
	private static final Logger metricsLogger = LoggerFactory.getLogger("MetricsLogger");

	@Autowired
	private MetricsHelper helper;

	// 保存指标的注册状态。已经注册过的，保存在set中。
	private volatile Set<String> registerStatus = Collections.synchronizedSet(new HashSet<>());
	private ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
	
	private static final ThreadLocal<MetricsContext> metricsCtx = new ThreadLocal<>();

	
//	@Around(value = "@annotation(loggerBean)")
//	public void around(ProceedingJoinPoint point, MetricsLog loggerBean) throws Throwable {
//		logger.info("enter the around. {}", point.getSignature().toString());
//		Object args[] = point.getArgs();
//		point.proceed(args);
//	}
	
	
	/**
	 * 注册一个timer指标。如果已经注册过则不再注册。<br/>
	 * 
	 * @param name
	 * @param tags
	 */
	private void registerTimer(String name, String tags[]) {
		ReadLock rlock = locker.readLock();
		rlock.lock();

		if (! registerStatus.contains(name)) {
			rlock.unlock();
			WriteLock wlock = locker.writeLock();
			wlock.lock();
			// 重新取一次。确保前面取之后，没有被更新过。
			if (! registerStatus.contains(name)) {
				registerStatus.add(name);
				helper.registerTimer(name, new double[]{0.5, 0.9, 0.99}, tags);
			}
			wlock.unlock();
		} else {
			rlock.unlock();
		}
	}
	
	@Before(value = "@annotation(loggerBean)")
	public void before(JoinPoint point, MetricsLog loggerBean) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("enter the before. {}", point.getSignature().toString());
			}

			//记录方法开始时间
			MetricsContext mc = new MetricsContext();
			mc.setTime(System.currentTimeMillis());
			metricsCtx.set(mc);
		} catch (Exception e) {
			logger.error("system error in before. ", e);
		}
	}

	@AfterReturning(value = "@annotation(loggerBean)", returning = "retObj")
	public void after(JoinPoint point, MetricsLog loggerBean, Object retObj) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("enter the after. {}", point.getSignature().toString());
			}

			EvaluationContext ctx = buildElContext(point.getArgs(), retObj);
			// 评估方法执行结果是否是失败。
			boolean result = getResult(loggerBean, ctx, false);
			
			// 更新指标
			updateMetrics(point, loggerBean, result, ctx);
			// 打印日志
			printLog(ctx, point, loggerBean);
		} catch (Exception e) {
			logger.error("system error in after. ", e);
		} finally {
			// 清理上下文
			metricsCtx.remove();
		}
	}

	@AfterThrowing(value = "@annotation(loggerBean)", throwing = "ex")
	public void throwError(JoinPoint point, MetricsLog loggerBean, Throwable ex) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("enter the throwError. {}", point.getSignature().toString());
			}

			EvaluationContext ctx = buildElContext(point.getArgs(), null);
			
			// 更新指标
			updateMetrics(point, loggerBean, true, ctx);
			// 打印日志
			printLog(ctx, point, loggerBean);
		} catch (Exception e) {
			logger.error("system error in after. ", e);
		}
	}
	
	/**
	 * 判断方法执行是否成功。 <br/>
	 * 
	 * @param loggerBean
	 * @param ctx
	 * @param except
	 * @return
	 */
	private boolean getResult(MetricsLog loggerBean, EvaluationContext ctx, boolean except) {
		String failExp = null;
		if (loggerBean.stdApi()) {
			failExp = "! #RETURN.success";
		} else {
			failExp = loggerBean.failExp();
		}
		
		return getExpressionValue(failExp, ctx, except);
	}

	/**
	 * 计算tagsExp，并拆分为不同的tag。
	 * 
	 * @param ctx
	 * @param tagsExp
	 * @return
	 */
	private String[] parseTags(EvaluationContext ctx, String tagsExp) {
		if (! StringUtils.hasLength(tagsExp)) {
			return null;
		}
		String tagStr = getExpressionValue(tagsExp, ctx, null);
		if (! StringUtils.hasLength(tagStr)) {
			return null;
		}
		
		String ret[] = tagStr.split(",");
		
		// 必须为偶数
		if ((ret.length & 1) == 1) {
			logger.error("invalid tags value. [{}], [{}]", tagsExp, tagStr);
			return null;
		}
		
		return ret;
	}
	
	/**
	 * 构建指标需要的tags
	 * 
	 * @param point
	 * @param loggerBean
	 * @param failed
	 * @param ctx
	 * @return
	 */
	private String[] buildTagArray(JoinPoint point, MetricsLog loggerBean, boolean failed, EvaluationContext ctx ) {
		// 计算tenantCode
		String tenantCode = getExpressionValue(loggerBean.tenantExp(), ctx, "");
		
		// 计算其他tags
		String tags[] = parseTags(ctx, loggerBean.tagsExp());
		
		
		String extTags[] = new String[8 + (tags == null? 0:tags.length)];
		extTags[0] = "result";
		extTags[1] = failed ? "fail" : "success";
		extTags[2] = "class";
		extTags[3] = point.getTarget().getClass().getName();
		extTags[4] = "method";
		extTags[5] = point.getSignature().getName();
		extTags[6] = "tenant";
		extTags[7] = tenantCode;
		if (tags != null) {
			for(int i = 8;i < extTags.length;i ++) {
				extTags[i] = tags[i - 8];
			}
		}
		return extTags;
	}
	
	/**
	 * 更新指标。计算方法耗时
	 * 
	 * 
	 * @param point
	 * @param loggerBean
	 * @param failed
	 * @param ctx
	 */
	private void updateMetrics(JoinPoint point, MetricsLog loggerBean, boolean failed, EvaluationContext ctx) {
		String counter = loggerBean.name() + ".counter";
		String timer = loggerBean.name() + ".timer";
		MetricsContext mc = metricsCtx.get();

		// 计算方法耗时
		long end = System.currentTimeMillis();
		mc.setTime(end - mc.getTime());
		
		String extTags[] = buildTagArray(point, loggerBean, failed, ctx);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" the tag array is: {}", Arrays.asList(extTags));
		}
		
		helper.updateCounter(counter, 1D, extTags);
		registerTimer(timer, extTags);
		helper.updateTimer(timer, mc.getTime(), TimeUnit.MILLISECONDS, extTags);
	}
	/**
	 * 构建EL 使用的context
	 * 
	 * @param args
	 * @param retObj
	 * @return
	 */
	private EvaluationContext buildElContext(Object[] args, Object retObj) {
		EvaluationContext context = new StandardEvaluationContext();
		for (int idx = 0; idx < args.length; idx++) {
			context.setVariable(Constants.AOP_PARAMETER_PREFIX + (idx+1), args[idx]);
		}
		if (retObj != null) {
			context.setVariable(Constants.AOP_PARAMETER_RETURN, retObj);
		}
		return context;
	}
	
	/**
	 * 根据EL表达式，计算结果。 
	 *  
	 * @param <T>
	 * @param expStr
	 * @param ctx
	 * @param defaultValue
	 * @return
	 */
	private <T> T getExpressionValue(String expStr, EvaluationContext ctx, T defaultValue) {
		
		if (! StringUtils.hasLength(expStr)) {
			return defaultValue;
		}
		
		Expression exp = (Expression) ExpressionCache.getInstance().getExpression(expStr);
		try {
			return (T) exp.getValue(ctx);
		} catch (EvaluationException e) {
			logger.error("Expresion failed. {}", expStr, e);
			return defaultValue;
		}
	}
	
	
	/**
	 * 根据日志内容表达式，打印日志。
	 * 
	 * 
	 * @param ctx
	 * @param point
	 * @param loggerBean
	 */
	private void printLog(EvaluationContext ctx, JoinPoint point, MetricsLog loggerBean) {
		String result = getExpressionValue(loggerBean.logExp(), ctx, null);
		if (StringUtils.hasLength(result)) {
			MetricsContext mc = metricsCtx.get();
			MDC.put("metrics.time", Long.toString(mc.getTime()));
			MDC.put("metrics.class", point.getTarget().getClass().getName());
			MDC.put("metrics.method", point.getSignature().getName());

			if (loggerBean.logLevel().equalsIgnoreCase(Constants.LOG_DEBUG)) {
				metricsLogger.debug(result);
			} else if (loggerBean.logLevel().equalsIgnoreCase(Constants.LOG_WARN)) {
				metricsLogger.warn(result);
			} else if (loggerBean.logLevel().equalsIgnoreCase(Constants.LOG_ERROR)) {
				metricsLogger.error(result);
			} else {
				metricsLogger.info(result);
			}

			MDC.remove("metrics.time");
			MDC.remove("metrics.class");
			MDC.remove("metrics.method");
		}
	}
}
