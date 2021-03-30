package com.yh.infra.svc.gov.sdk.platform.baseservice.task.manager.log;

import com.yh.infra.svc.gov.sdk.exception.BusinessException;

import java.util.Date;
import java.util.List;


/**
 *
 * task 日志收集接口
 * 1、dubbo实现:
 * <dubbo:reference  url=""  id="taskLogApiExecuteManager" interface="com.yh.infra.platform.baseservice.task.manager.log.TaskLogApiExecuteManager" check="false"/>
 *
 * 2、http实现:
 * TaskLogApiExecuteManagerAdapter
 *
 * 3、MQ实现 TODO
 * @author zan.ma
 */
public interface TaskLogApiExecuteManager {

    /**
     *
     * @param registerGroup
     * @param taskPlanId
     * @param planTriggerTime
     * @param processInfo
     * @param errorInfo
     * @param result
     * @throws BusinessException
     */
    void addTaskRunLogRealTime(String registerGroup, Long taskPlanId, Date planTriggerTime, String processInfo, String errorInfo, Boolean result) throws BusinessException;

    /**
     * addTaskRunLogNotRealTime
     *
     * @param registerGroup        任务编号 必填
     * @param taskPlanId      任务执行计划id 必填
     * @param planTriggerTime 计划触发时间 必填
     * @param processList     process 执行过程list 选填
     * @param errorInfoList   异常信息list 选填
     * @param result          执行结果 true成功 false失败 必填

     * @throws BusinessException
     * @Description:非实时 添加日志记录
     * @author long.cheng
     */
    void addTaskRunLogNotRealTime(String registerGroup, Long taskPlanId, Date planTriggerTime, List<String> processList, List<String> errorInfoList, Boolean result) throws BusinessException;

}
