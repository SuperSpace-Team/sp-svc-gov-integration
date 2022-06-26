package com.sp.infra.svc.gov.sdk.task.support;

import java.util.Date;

/**
 * 
 *@Title:
 *@Description: 任务回调
 *@author long.cheng
 *@Since:2016年8月4日
 *@Copyright:Copyright (c) 2014
 *@ModifyDate:
 *@Version:1.1.0
 */
public interface TaskCallBackManager {

	/**
	 * 任务执行调用
	 * updateTaskPlan
	 * @Description:根据表后缀名更新相关信息
	 * @param registerGroup 注册中心
	 * @param taskPlanId 计划任务id
	 * @param runRealTime 实际执行时间
	 * @param taskPlanTime 计划触发时间 （生成表后缀）
	 * @author long.cheng
	 */
	void updateTask(String registerGroup, Long taskPlanId, Date runRealTime, Date taskPlanTime);
	/**
	 * 任务日志发送
	 * @param registerGroup
	 * @param taskPlanId
	 * @param planTriggerTime
	 * @param processInfo
	 * @param errorInfo
	 * @param result
	 */
    void addLog(String registerGroup, Long taskPlanId, Date planTriggerTime, String processInfo, String errorInfo, Boolean result);


}
