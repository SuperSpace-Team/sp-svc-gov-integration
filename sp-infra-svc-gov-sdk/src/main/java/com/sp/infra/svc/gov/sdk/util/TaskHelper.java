package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TaskHelper {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskHelper.class);
    /**
     * _本地线程对象-->写入日志时get
     */
    public static ThreadLocal<Map<String, Object>> threadLocalCache = new ThreadLocal<Map<String, Object>>();

    public static final String KEY_REGISTER_GROUP = "registerGroup";
    public static final String KEY_TASK_PLAN_ID = "taskPlanId";
    public static final String KEY_TASK_PLAN_TRIGGER_TIME = "planTriggerTime";
    public static final String KEY_TASK_LOG_LEVEL_LIST = "logLevelList";
    public static final String KEY_TASK_CONTENT_TYPE = "Content-type";

    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    private static final int MESSAGE_MAX_LENGTH = 200;
    private static final int EXCEPTION_MAX_LENGTH = 3900;
    private static final String NULL_ERROR = "无异常栈信息(此条手动)";
    private static final String CAUSE_CAPTION = "Caused by: ";

    private TaskHelper() {
        throw new IllegalAccessError("TaskHelper is an util class!");
    }
    /**
     * _保存线程信息
     */
    public static void setThreadLocalCache(String registerGroup, Long taskPlanId, Date planTriggerTime, List<String> logLevelList) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put(KEY_REGISTER_GROUP, registerGroup);
        map.put(KEY_TASK_PLAN_ID, taskPlanId);
        map.put(KEY_TASK_PLAN_TRIGGER_TIME, planTriggerTime);
        map.put(KEY_TASK_LOG_LEVEL_LIST, logLevelList);
        // set本地线程对象
        TaskHelper.threadLocalCache.set(map);
    }

    /**
     * _保存HttpHeader信息
     */
    public static Map<String, String> buildMapCache(String registerGroup, Long taskPlanId, Date planTriggerTime, List<String> logLevelList, String contentType) {
        Map<String, String> map = new HashMap<String, String>(16);
        map.put(KEY_REGISTER_GROUP, registerGroup);
        map.put(KEY_TASK_PLAN_ID, String.valueOf(taskPlanId));
        map.put(KEY_TASK_PLAN_TRIGGER_TIME, DateTimeHelper.toString(planTriggerTime));
        map.put(KEY_TASK_LOG_LEVEL_LIST, CollectionUtils.List2String(logLevelList)); // DEBUG,INFO,WARN,ERROR
        map.put(KEY_TASK_CONTENT_TYPE, contentType);

        return map;
    }

    /**
     * @title 收集日志
     * @param level 当前日志级别
     * @param message 异常信息
     * @param stackTrace 异常堆栈
     */
    public static void appendLog(String level, String message, Throwable stackTrace) {
        BeanRegistry sc = BeanRegistry.getInstance();
        TaskCallBackManager taskCallBackManager = sc.getBean(TaskCallBackManager.class);

        // 本地任务执行对象
        Map<String, Object> map = TaskHelper.threadLocalCache.get();
        if (null == map) {
            logger.warn("Thread local map is null!");
            return;
        }

        //获取任务计划id
        Object taskPlanIdCache = map.get(TaskHelper.KEY_TASK_PLAN_ID);
        if (null == taskPlanIdCache) {
            return;
        }

        Long taskPlanId = (Long) taskPlanIdCache;

        // 注册中心判断
        String registerGroup = (String) map.get(TaskHelper.KEY_REGISTER_GROUP);
        // 获取计划触发时间
        Date planTriggerTime = (Date) map.get(TaskHelper.KEY_TASK_PLAN_TRIGGER_TIME);
        // 获取日志级别列表
        @SuppressWarnings("unchecked")
        List<String> logLevelList = (List<String>) map.get(TaskHelper.KEY_TASK_LOG_LEVEL_LIST);


        // 获取当前日志级别
        if (logLevelList.contains(level)) {
            message = "[" + DateTimeHelper.toString(new Date()) + "]" + getExceptionMessage(message);
            String stackInfo = null;
            // 异常日志
            if (ERROR.equals(level)) {
                stackInfo = (null == stackTrace) ? NULL_ERROR : getExceptionAllinformation(stackTrace);
            }
            taskCallBackManager.addLog(registerGroup, taskPlanId, planTriggerTime, message, stackInfo, null);
        }

    }

    /**
     * @desc 异常堆栈输出
     * @return
     */
    public static String getExceptionAllinformation(Throwable throwable) {
        String fullStackTrace  = throwable.toString()+"\r\n";
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement s : trace) {
            fullStackTrace += "\tat " + s + "\r\n";
        }
        if (fullStackTrace.length() >= EXCEPTION_MAX_LENGTH) {
            //如果超过数据库定义长度,将做一层截取
            return fullStackTrace.substring(0, EXCEPTION_MAX_LENGTH);
        }
        return fullStackTrace;
    }
    /**
     * @desc 异常堆栈输出
     * @return
     */
    public static String getExceptionAllinformation(Throwable causedBy,Throwable throwable) {
        if (null == throwable) {
            return "";
        }
        if (null != causedBy) {
            return CAUSE_CAPTION + causedBy + ":\n" + getExceptionAllinformation(throwable);
        } else {
            return CAUSE_CAPTION + throwable.getClass().getName() + ":\n" + getExceptionAllinformation(throwable);
        }

    }


    /**
     * @desc 消息
     * @param e
     * @return
     */
    public static String getExceptionMessage(Throwable e) {
        if (e == null) {
            return "";
        }
        return getExceptionMessage(e.getMessage());
    }
    /**
     * @desc 消息
     * @param message
     * @return
     */
    public static String getExceptionMessage(String message) {
        if (null == message) {
            message = "";
        }
        if (message.length() >= MESSAGE_MAX_LENGTH) {
            return message.substring(0, MESSAGE_MAX_LENGTH);
        }

        return message;
    }

}
