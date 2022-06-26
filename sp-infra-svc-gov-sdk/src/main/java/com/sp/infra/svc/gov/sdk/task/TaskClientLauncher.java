/**
 * Copyright (c) 2019 SuperSpace All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SuperSpace.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with SuperSpace.
 *
 * SUPERSPACE MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUPERSPACE SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.sp.infra.svc.gov.sdk.task;

import com.sp.infra.svc.gov.sdk.platform.baseservice.task.manager.log.TaskLogApiExecuteManager;
import com.sp.infra.svc.gov.sdk.platform.baseservice.task.manager.task.TaskPlanExecuteManager;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.task.support.HttpTaskExecutor;
import com.sp.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import com.sp.infra.svc.gov.sdk.task.support.impl.HttpTaskExecutorImpl;
import com.sp.infra.svc.gov.sdk.task.support.impl.TaskCallBackManagerImpl;
import com.sp.infra.svc.gov.sdk.util.NetUtils;
import com.sp.infra.svc.gov.sdk.util.SpringUtil;
import com.sp.infra.svc.gov.sdk.util.ThreadUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 定时任务客户端控制，通过setter方法注入
 * 1、是否启用定时任务
 * 2、定时任务执行线程池
 * 3、定时任务日志收集方式
 *
 * xml或者@Bean 只需要实现这一个Launcher
 * 在xml中指定init-method="init"
 *
 * Lancher仅在初始化的时候执行
 */
public final class TaskClientLauncher {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskClientLauncher.class);

    private Boolean enabled = false;  // 开关， 是否启用sdk， 默认不开启
    private Integer taskPlanUpdateConfig; // 任务更新方式 1:同步 2:异步
    private Integer taskLogUpdateConfig; // 日志收集方式 1:实时 2:非实时
    
    
    private TaskCallBackManager taskCallBackManager; // 任务执行回调支持(有默认实现)
    private HttpTaskExecutor httpTaskExecutor; // Http任务调Http(有默认实现)

    private TaskPlanExecuteManager taskPlanExecuteManager; // 第三方注入实现
    private TaskLogApiExecuteManager taskLogApiExecuteManager; // 第三方注入实现

    /**
     * 初始化调用
     */
    public void init() {
        logger.info("Task Client Load Begin......");
        logger.info("Task Client SDK Enabled is [{}]", this.enabled);
        // 注册
        BeanRegistry sc = BeanRegistry.getInstance();
        sc.register(SdkCommonConstant.TASK_ENABLE_FLAG, this.enabled);

        // 启用，则注册
        if (!this.enabled) {
            logger.error("Task Client Launcher is disabled!");
            return;
        }
        
        // 初始化配置
        this.regConfig(sc);
        
        // 初始化任务执行Service
        this.regServices(sc);
        
        // 初始化线程池
        this.initThreadPool();
        
        logger.info("Task Client Load End");
    }

    /**
     * 设置全局配置
     * @param sc
     */
    private void regConfig(BeanRegistry sc) {
        logger.info("Task Client register config......");
        // 任务计划发送方式
        if (null == this.taskPlanUpdateConfig) {
            this.taskPlanUpdateConfig = SdkCommonConstant.TASK_PLAN_ASYNC;
        }
        // 任务日志发送方式
        if (null == this.taskLogUpdateConfig) {
            this.taskLogUpdateConfig  = SdkCommonConstant.TASK_LOG_NOT_REALTIME;
        }

        sc.register(SdkCommonConstant.KEY_TASK_PLAN_UPDATE_CONFIG, this.taskPlanUpdateConfig);
        sc.register(SdkCommonConstant.KEY_TASK_LOG_UPDATE_CONFIG, this.taskLogUpdateConfig);

        // 获取ip地址
        String ip = NetUtils.getExternalIp();
        if (StringUtils.isEmpty(ip)) {
        	ip = "127.0.0.1";
        }
        sc.register(SdkCommonConstant.TASK_DAEMON_IP, ip);
    }

    /**
     * 设置全局变量
     */
    private void regServices(BeanRegistry sc) {
        logger.info("Task Client register service......");
        // 检测xml配置
        this.checkInit();
        
        sc.register(TaskCallBackManager.class.getName(), this.taskCallBackManager);
        sc.register(HttpTaskExecutor.class.getName(), this.httpTaskExecutor);
        
        // concurrentHashMap value can not be null
        if (null != this.taskPlanExecuteManager) {
            sc.register(TaskPlanExecuteManager.class.getName(), this.taskPlanExecuteManager);
        }
        
        if (null != this.taskLogApiExecuteManager) {
            sc.register(TaskLogApiExecuteManager.class.getName(), this.taskLogApiExecuteManager);
        }

    }
    
    /**
     * 初始化回调接口
     */
    private void checkInit() {

        // spring注入
        if (!SpringUtil.checkApplicationContextInitialization()) {
            logger.error("TASK SpringUtil not initialized ! SPRING TASK will not effective");
        }

        // 任务执行计划
        if (null == this.taskPlanExecuteManager) {
            logger.warn("TASK Plan Executor not initialized! TASK result will not update.");
        }
        // 任务日志
        if (null == this.taskLogApiExecuteManager) {
            logger.warn("TASK Log Executor not initialized! TASK message will not update.");
        }

        // 日志收集器
        if (null == this.taskCallBackManager) {
            this.taskCallBackManager = TaskCallBackManagerImpl.getInstance();
        }
        // Http回调
        if (null == this.httpTaskExecutor) {
            this.httpTaskExecutor = HttpTaskExecutorImpl.getInstance();
        }
    }
    /**
     * 初始化线程池
     */
    private void initThreadPool() {
        logger.info("Task Client init threadpool......");
        ThreadUtil.initTaskThreadPool();
        ThreadUtil.initTaskPlanThreadPool();
        ThreadUtil.initTaskLogThreadPool();
        
    }
    
    //=========================== getters and setters ====================================//
	public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getTaskPlanUpdateConfig() {
        return taskPlanUpdateConfig;
    }
    public void setTaskPlanUpdateConfig(Integer taskPlanUpdateConfig) {
        this.taskPlanUpdateConfig = taskPlanUpdateConfig;
    }

    public Integer getTaskLogUpdateConfig() {
        return taskLogUpdateConfig;
    }
    public void setTaskLogUpdateConfig(Integer taskLogUpdateConfig) {
        this.taskLogUpdateConfig = taskLogUpdateConfig;
    }

    public TaskCallBackManager getTaskCallBackManager() {
        return taskCallBackManager;
    }

    public void setTaskCallBackManager(TaskCallBackManager taskCallBackManager) {
        this.taskCallBackManager = taskCallBackManager;
    }

    public HttpTaskExecutor getHttpTaskExecutor() {
        return httpTaskExecutor;
    }

    public void setHttpTaskExecutor(HttpTaskExecutor httpTaskExecutor) {
        this.httpTaskExecutor = httpTaskExecutor;
    }

    public TaskPlanExecuteManager getTaskPlanExecuteManager() {
        return taskPlanExecuteManager;
    }

    public void setTaskPlanExecuteManager(TaskPlanExecuteManager taskPlanExecuteManager) {
        this.taskPlanExecuteManager = taskPlanExecuteManager;
    }

    public TaskLogApiExecuteManager getTaskLogApiExecuteManager() {
        return taskLogApiExecuteManager;
    }

    public void setTaskLogApiExecuteManager(TaskLogApiExecuteManager taskLogApiExecuteManager) {
        this.taskLogApiExecuteManager = taskLogApiExecuteManager;
    }

}
