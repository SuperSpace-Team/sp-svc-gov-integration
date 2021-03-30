package com.yh.infra.svc.gov.sdk.task.support.impl;

import com.yh.infra.svc.gov.sdk.platform.baseservice.task.manager.log.TaskLogApiExecuteManager;
import com.yh.infra.svc.gov.sdk.platform.baseservice.task.manager.task.TaskPlanExecuteManager;
import com.yh.infra.svc.gov.sdk.command.TaskLogCommand;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskCallBackManagerImpl implements TaskCallBackManager {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskCallBackManagerImpl.class);

    /**
     * 存放日志信息
     * 初始化1024
     */
    private static Map<Long, TaskLogCommand> logMap = Collections.synchronizedMap(new HashMap<Long, TaskLogCommand>(1024));

    /**
     * 手动单例
     * @author MSH8244
     *
     */
    private static class SingletonHolder {
        private final static TaskCallBackManager instance = new TaskCallBackManagerImpl();
    }
    public static TaskCallBackManager getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 任务执行调用
     * updateTaskPlan
     *
     * @param registerGroup 注册中心
     * @param taskPlanId    计划任务id
     * @param runRealTime   实际执行时间
     * @param taskPlanTime  计划触发时间 （生成表后缀）
     * @Description:根据表后缀名更新相关信息
     * @author long.cheng
     */
    @Override
    public void updateTask(String registerGroup, Long taskPlanId, Date runRealTime, Date taskPlanTime) {
		if (logger.isDebugEnabled())
			logger.debug("TASK[{}]updateTask", taskPlanId);
        try{
            // 日志收集器接口
            BeanRegistry sc = BeanRegistry.getInstance();
            TaskPlanExecuteManager taskPlanExecuteManager = sc.getBean(TaskPlanExecuteManager.class);
            if (null == taskPlanExecuteManager) {
                logger.error("taskPlanExecuteManager not initialized, so exit");
                return;
            }

            // 任务发送方式
            Integer taskPlanUpdateConfig = sc.<Integer>getBean(SdkCommonConstant.KEY_TASK_PLAN_UPDATE_CONFIG);
            if (taskPlanUpdateConfig.equals(SdkCommonConstant.TASK_PLAN_SYNC)) {
                taskPlanExecuteManager.updateTaskPlan(registerGroup, taskPlanId, runRealTime, taskPlanTime);
            } else {
                ThreadUtil.taskPlanThreadPool.execute(new PlanSendRunnable(registerGroup, taskPlanId, taskPlanTime, runRealTime, taskPlanExecuteManager));
            }
        }  catch (Exception e) {
            logger.error("Task PlanId=" + taskPlanId +" update runRealTime error", e);
        }
    }

    /**
     * 任务日志发送
     * 异步非阻塞
     * @param registerGroup
     * @param taskPlanId
     * @param planTriggerTime
     * @param processInfo
     * @param errorInfo
     * @param result
     */
    @Override
    public void addLog(String registerGroup, Long taskPlanId, Date planTriggerTime, String processInfo, String errorInfo, Boolean result) {
        logger.info("TASK[{}] addLog:{}; errorInfo:{} ; result:{}", taskPlanId, processInfo, errorInfo, result);
        try {
            // 日志收集器接口
            BeanRegistry sc = BeanRegistry.getInstance();
            TaskLogApiExecuteManager taskLogApiExecuteManager = sc.getBean(TaskLogApiExecuteManager.class);

            if (null == taskLogApiExecuteManager) {
                logger.error("taskLogApiExecuteManager not initialized, so exit");
                return;
            }

            // 日志收集方式
            Integer taskLogUpdateConfig = sc.<Integer>getBean(SdkCommonConstant.KEY_TASK_LOG_UPDATE_CONFIG);
            if (null == taskLogUpdateConfig) {
                taskLogUpdateConfig = SdkCommonConstant.TASK_LOG_NOT_REALTIME;
            }
            
            // 实时日志
            if (SdkCommonConstant.TASK_LOG_REALTIME.equals(taskLogUpdateConfig)) {
                TaskLogCommand cmd = new TaskLogCommand();
                cmd.setProcessList(processInfo);
                cmd.setErrorInfoList(errorInfo);
                cmd.setResult(result);

                ThreadUtil.taskLogThreadPool.execute(new LogSendRunnable(taskLogUpdateConfig, registerGroup, taskPlanId, planTriggerTime, taskLogApiExecuteManager, cmd));
            }
            // 非实时日志
            else {
                TaskLogCommand cmd = logMap.get(taskPlanId);
                if (null == cmd) {
                    cmd = new TaskLogCommand();
                    logMap.put(taskPlanId, cmd);
                }

                if(!StringUtils.isBlank(processInfo)) {
                    cmd.setProcessList(processInfo);
                }
                if(!StringUtils.isBlank(errorInfo)) {
                    cmd.setErrorInfoList(errorInfo);
                }
                if (null != result) {
                    // 读取cmd
                    TaskLogCommand cmdSend = logMap.get(taskPlanId);
                    cmdSend.setResult(result);
                    ThreadUtil.taskLogThreadPool.execute(new LogSendRunnable(taskLogUpdateConfig, registerGroup, taskPlanId, planTriggerTime, taskLogApiExecuteManager, cmdSend));
                    //推送之后清空内存相应日志信息
                    logMap.remove(taskPlanId);
                }
            } // end if
        } catch (Exception e) {
            logger.error("Task PlanId=" + taskPlanId +" send message error", e);
        }
    }


    /**
     * 异步任务计划发送
     */
    class PlanSendRunnable implements Runnable {

        private String registerGroup; // 集群
        private Long taskPlanId; // 任务执行计划ID
        private Date planTriggerTime; // 任务触发时间
        private Date runRealTime;
        private TaskPlanExecuteManager taskPlanExecuteManager;
        public PlanSendRunnable(String registerGroup, Long taskPlanId, Date planTriggerTime, Date runRealTime,
                                TaskPlanExecuteManager taskPlanExecuteManager) {
            this.registerGroup = registerGroup;
            this.taskPlanId = taskPlanId;
            this.planTriggerTime = planTriggerTime;
            this.runRealTime = runRealTime;
            this.taskPlanExecuteManager = taskPlanExecuteManager;
        }

        /**
         * 任务计划更新
         */
        @Override
        public void run() {
            try {
                this.taskPlanExecuteManager.updateTaskPlan(registerGroup,taskPlanId, runRealTime, planTriggerTime);
            } catch (Exception e) {
                logger.error("计划异步发送失败", e);
            }
        }
    }

    /**
     * 异步日志发送
     */
    class LogSendRunnable implements Runnable{
        private Integer taskLogConfig; // 发送方式
        private String registerGroup; // 集群
        private Long taskPlanId; // 任务执行计划ID
        private Date planTriggerTime; // 任务触发时间

        private TaskLogApiExecuteManager taskLogApiExecuteManager; // service
        private TaskLogCommand taskLogCommand; // 日志实体

        //构造函数
        public LogSendRunnable(Integer taskLogConfig, String registerGroup,
                               Long taskPlanId, Date planTriggerTime,
                               TaskLogApiExecuteManager taskLogApiExecuteManager,
                               TaskLogCommand taskLogCommand) {
            this.taskLogConfig = taskLogConfig;
            this.registerGroup = registerGroup;
            this.taskPlanId = taskPlanId;
            this.planTriggerTime = planTriggerTime;
            this.taskLogApiExecuteManager = taskLogApiExecuteManager;
            this.taskLogCommand = taskLogCommand;
        }

        /**
         * 日志发送
         */
        @Override
        public void run() {
            try {
                // 实时日志
                if (SdkCommonConstant.TASK_LOG_REALTIME.equals(taskLogConfig)) {
                    String processInfo = taskLogCommand.getProcessList().get(0);
                    String errorInfo = taskLogCommand.getErrorInfoList().get(0);
                    Boolean result = taskLogCommand.getResult();
                    taskLogApiExecuteManager.addTaskRunLogRealTime(registerGroup, taskPlanId, planTriggerTime, processInfo, errorInfo, result);
                }
                // 非实时
                else {
                    taskLogApiExecuteManager.addTaskRunLogNotRealTime(registerGroup,taskPlanId, planTriggerTime, taskLogCommand.getProcessList(), taskLogCommand.getErrorInfoList(), taskLogCommand.getResult());
                }
            } catch (Exception e) {
                logger.error("日志异步发送失败" + taskPlanId, e);
            }

        }
    }

}
