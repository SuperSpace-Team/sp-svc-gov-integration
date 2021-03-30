package com.yh.infra.svc.gov.sdk.task.runcontrol;

import java.util.Date;

/**
 * 
 * @title: 定时任务入口
 * @Description:
 * @since: 2016年5月9日
 * @copyright: Copyright (c) 2016
 * @modifyDate:
 * @version: 1.1.0
 * @author long.cheng
 *
 */
public interface TriggerTaskManager {

    /**
     * bean方式触发service的定时任务
     * @Description:通用任务执行的方法，等待触发器调用，并且调用真实的任务方法
     * @param beanName spring_bean 的 name
     * @param methodName 方法名称
     * @param args 参数 可为空
     * @param taskCode 定时任务编码
     * @param taskPlanId 计划任务id
     * @param planTriggerTime 计划触发时间（非补偿类型的任务，执行方忽略此字段）
     * @param logLevel 日志级别
     * @return
     * @author zan.ma
     * @since 1.1.4
     */
    Date triggerTask(String registerGroup, String beanName, String methodName, Object args, String taskCode, Long taskPlanId, Date planTriggerTime, String logLevel);

    
    
    /**
     * 回调http服务
     * @param registerGroup
     * @param taskCode
     * @param httpUrl
     * @param args
     * @param httpRequestMethod
     * @param contentType
     * @param taskPlanId
     * @param planTriggerTime
     * @param logLevel
     * @return
     * @since 2.1
     */
    Date triggerHttpTask(String registerGroup, String taskCode, String httpUrl, Object args, Integer httpRequestMethod, String contentType, Long taskPlanId, Date planTriggerTime, String logLevel);
}
