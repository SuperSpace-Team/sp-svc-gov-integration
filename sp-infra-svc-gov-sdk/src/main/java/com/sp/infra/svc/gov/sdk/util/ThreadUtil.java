/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.task.config.TaskPoolProperties;

import java.util.concurrent.*;

/**
 * @author luchao 2018-12-19
 *
 */
public class ThreadUtil {
    
    public static ExecutorService taskThreadPool;
    public static ExecutorService taskPlanThreadPool;
    public static ExecutorService taskLogThreadPool;
    
    private ThreadUtil() {
    }
    
    
	/**
	 * 休眠
	 * @param milliseconds
	 */
	public static void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 命名ThreadFactory
 	 */
	 public static ThreadFactory getNamedThreadFactory(String name) {
	 	String nameFormat = name + "-ThreadPool-%d";
		 return new ThreadFactoryBuilder()
				 .setNameFormat(nameFormat).build();
	 }
	 
	 /**
	  * 初始化
	  */
	 public static void initTaskThreadPool() {
	     taskThreadPool = new ThreadPoolExecutor(
				 TaskPoolProperties.TASK_POOL_CORE_SIZE,
				 TaskPoolProperties.TASK_POOL_MAX_SIZE,
	            0L, TimeUnit.MILLISECONDS,
	            new LinkedBlockingQueue<Runnable>(TaskPoolProperties.TASK_BLOCKING_QUEUE_SIZE),
	            getNamedThreadFactory("task"),
	            new AbortPolicyWithReport("taskTrigger-ThreadPool"));
	 }
	 
	 
	 /**
	  * 初始化
	  */
	 public static void initTaskPlanThreadPool() {
	     taskPlanThreadPool = new ThreadPoolExecutor(
				 TaskPoolProperties.TASK_PLAN_POOL_CORE_SIZE,
				 TaskPoolProperties.TASK_PLAN_POOL_MAX_SIZE,
	            0L, TimeUnit.MILLISECONDS,
	            new LinkedBlockingQueue<Runnable>(TaskPoolProperties.TASK_PLAN_BLOCKING_QUEUE_SIZE),
	            getNamedThreadFactory("taskPlan"),
	            new AbortPolicyWithReport("taskPlan-ThreadPool"));
	 }
	 
	 /**
	  * 初始化
	  */
	 public static void initTaskLogThreadPool() {
	     taskLogThreadPool = new ThreadPoolExecutor(
				 TaskPoolProperties.TASK_LOG_POOL_CORE_SIZE,
				 TaskPoolProperties.TASK_LOG_POOL_MAX_SIZE,
             0L, TimeUnit.MILLISECONDS,
             new LinkedBlockingQueue<Runnable>(TaskPoolProperties.TASK_LOG_BLOCKING_QUEUE_SIZE),
             getNamedThreadFactory("taskPlan"),
             new AbortPolicyWithReport("taskLog-ThreadPool"));
	 }
	public static void sleepRandom(int limit) {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(limit));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
