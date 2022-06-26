/*
 * TaskPlanExecuteManager.java Created On 2017年12月17日
 * Copyright(c) 2017 SuperSpace Inc.
 * ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.platform.baseservice.task.manager.task;

import java.util.Date;

/**
 * TaskPlanExecuteManager
 * 任务执行时间收集
 * 1、dubbo接口实现
 * <dubbo:reference url=""  id="taskPlanExecuteManager" interface="com.sp.infra.platform.baseservice.task.manager.task.TaskPlanExecuteManager" check="false"/>
 *
 * 2、http接口实现
 * TaskPlanExecuteManagerAdapter
 *
 * 3、MQ实现 TODO
 *
 * @time: 下午5:27:23
 * @author mazan
 */
public interface TaskPlanExecuteManager {

    /**
     * 任务执行调用: 更新任务执行时间
     * updateTaskPlan
     * @Description:根据表后缀名更新相关信息
     * @param registerGroup 注册中心
     * @param taskPlanId 计划任务id
     * @param runRealTime 实际执行时间
     * @param taskPlanTime 计划触发时间 （生成表后缀）
     * @author long.cheng
     */
    void updateTaskPlan(String registerGroup, Long taskPlanId, Date runRealTime, Date taskPlanTime);
}

