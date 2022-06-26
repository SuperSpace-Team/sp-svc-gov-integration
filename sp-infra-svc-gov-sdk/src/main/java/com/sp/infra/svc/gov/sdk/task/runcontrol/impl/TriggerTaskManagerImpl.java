package com.sp.infra.svc.gov.sdk.task.runcontrol.impl;

import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.task.runcontrol.TriggerTaskManager;
import com.sp.infra.svc.gov.sdk.task.runcontrol.thread.TriggerBeanExecuteThread;
import com.sp.infra.svc.gov.sdk.task.runcontrol.thread.TriggerHttpExecuteThread;
import com.sp.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import com.sp.infra.svc.gov.sdk.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TriggerTaskManagerImpl implements TriggerTaskManager {

    private static final Logger logger = LoggerFactory.getLogger(TriggerTaskManagerImpl.class);
    
    //========================= 公共方法 =============================//
    /**
     * 检查客户端配置，是否启用
     */
    private void checkLauncher(BeanRegistry sc) {

        Boolean enabled = sc.<Boolean>getBean(SdkCommonConstant.TASK_ENABLE_FLAG);
        if(enabled == null || !enabled) {
            throw new BusinessException("Task Client Launcher is disabled!");
        }
    }


    /**
     * 获取日志级别
     * @param logLevel
     * @return
     */
    private List<String> getLogLevel(String logLevel) {
        //日志级别转换list
        String[] arr = logLevel.split(",");
        List<String> logLevelList = Arrays.asList(arr);
        return logLevelList;
    }

    /**
     * 更新任务计划
     * @param sc
     * @param registerGroup
     * @param taskPlanId
     * @param runRealTime
     * @param planTriggerTime
     */
    private void updateTask(BeanRegistry sc, String registerGroup, Long taskPlanId, Date runRealTime, Date planTriggerTime) {
        TaskCallBackManager taskCallBackManager = sc.getBean(TaskCallBackManager.class);
        taskCallBackManager.updateTask(registerGroup, taskPlanId,runRealTime, planTriggerTime);
    }
    //========================= Bean任务方法 =============================//
    /**
     * bean方式触发service的定时任务[Spring]
     *
     * @param registerGroup
     * @param beanName        spring_bean 的 name
     * @param methodName      方法名称
     * @param args            参数 可为空
     * @param taskCode        定时任务编码
     * @param taskPlanId      计划任务id
     * @param planTriggerTime 计划触发时间（非补偿类型的任务，执行方忽略此字段）
     * @param logLevel        日志级别
     * @return
     * @Description:通用任务执行的方法，等待触发器调用，并且调用真实的任务方法
     * @author zan.ma
     * @since 1.1.4
     */
    @Override
    public Date triggerTask(String registerGroup, String beanName, String methodName, Object args, String taskCode, Long taskPlanId, Date planTriggerTime, String logLevel) {
		if (logger.isDebugEnabled())
			logger.debug("triggerTask[{}] begin, execute method is {}.{}", taskCode, beanName, methodName);
        
        BeanRegistry sc = BeanRegistry.getInstance();
        this.checkLauncher(sc);
        SpringUtil.checkApplicationContext();

        // 解析SpringBean验证
        Object taskInstance = this.getBeanInstance(beanName);
        // 解析参数个数
        String[] paramArray = this.getParams(args);
        // 解析方法
        Method refectMethod = this.getMethod(methodName, paramArray, taskInstance);
        // 解析日志级别
        List<String> logLevelList = this.getLogLevel(logLevel);

        // 启动任务线程
        ThreadUtil.taskThreadPool.execute(new TriggerBeanExecuteThread(taskCode, registerGroup, refectMethod, taskInstance, paramArray,
                taskPlanId, planTriggerTime, logLevelList));
        // 更新任务计划
        Date runRealTime = new Date();
        this.updateTask(sc,registerGroup, taskPlanId, runRealTime, planTriggerTime);
        
		if (logger.isDebugEnabled())
			logger.debug("triggerTask[{}] end......", taskCode);
        return runRealTime;
    }

    /**
     * *获取spring.bean
     * @param beanName
     * @return
     */
    private Object getBeanInstance(String beanName) {
        Object instance = null;
        try {
            instance =  SpringUtil.getBean(beanName); //applicationContext中不存在 则返回null
            if (null == instance) {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            String errorInfo = "TASK beanName[" + beanName +"] is not found! please check your spring config" ;
            logger.error(errorInfo);
            throw new BusinessException(errorInfo);
        }
        return instance;
    }
    /**
     * 解析参数个数
     * @param args
     * @return
     */
    private String[] getParams(Object args) {
        if (StringUtils.isNotBlank((String) args)) {
            return LineSplit.getLineArray((String) args, SdkCommonConstant.SEPARATOR_COMMA);
        }
        return new String[0]; //空数组
    }
    /**
     * 反射查找具体任务方法
     * 仅根据参数个数查找
     * 无法辨别重载
     * @param methodName
     * @param paramArray
     * @param taskInstance
     * @return
     */
    private Method getMethod(String methodName, String[] paramArray, Object taskInstance) {
        // 参数个数
        int paramSize = paramArray == null? 0 : paramArray.length;

        // 得到所有方法数组(包括父类的public)
        Method[] methods = taskInstance.getClass().getMethods();
        Method refectMethod = null;
        // 循环方法数组，得到当前需要的方法
        for (Method method : methods) {
            //方法名匹配
            if (method.getName().equals(methodName)) {
                // 方法参数个数匹配
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == paramSize) {
                    refectMethod = method;
                    break;
                }
            }
        }
        // 判断是否找到
        if (refectMethod == null) {
            String errorInfo = "TASK method[" + methodName + "] with params size[" + paramSize + "]is not found";
            logger.error(errorInfo);
            throw new BusinessException(errorInfo);
        }
        return refectMethod;
    }
    //========================= HTTP任务方法 =============================//
    /**
     * 回调http服务
     *
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
    @Override
    public Date triggerHttpTask(String registerGroup, String taskCode, String httpUrl, Object args, Integer httpRequestMethod, String contentType, Long taskPlanId, Date planTriggerTime, String logLevel) {
		if (logger.isDebugEnabled())
			logger.debug("triggerHttpTask[{}] begin......,url is {}", taskCode, httpUrl);
        BeanRegistry sc = BeanRegistry.getInstance();
        this.checkLauncher(sc);
        // 解析参数
        Map<String, Object> httpParams = this.getHttpParams(args);
        // 解析日志级别
        List<String> logLevelList = this.getLogLevel(logLevel);

        // 启动任务线程
        ThreadUtil.taskThreadPool.execute(new TriggerHttpExecuteThread(taskCode, registerGroup, httpUrl, httpParams, httpRequestMethod,
                contentType, taskPlanId, planTriggerTime, logLevelList));
		if (logger.isDebugEnabled())
			logger.debug("triggerHttpTask[{}] executed.", taskCode);
        // 更新任务计划
        Date runRealTime = new Date();
        this.updateTask(sc, registerGroup, taskPlanId, runRealTime, planTriggerTime);

        return runRealTime;
    }

    /**
     * 验证是否json字符串
     * @param args
     */
    private Map<String, Object> getHttpParams(Object args) {

        if (null == args || StringUtils.isBlank(args.toString())) {
            return null;
        }

        try{
            return JsonUtil.readValue(args.toString(), Map.class);
        } catch (Exception e) {
            String errorInfo = "HTTP-TASK param[" + args +"] convert to map Error! Please check config @platform" ;
            logger.error(errorInfo);
            throw new BusinessException(errorInfo);
        }
    }


}
