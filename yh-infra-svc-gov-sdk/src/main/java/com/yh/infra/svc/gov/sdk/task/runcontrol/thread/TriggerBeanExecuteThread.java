package com.yh.infra.svc.gov.sdk.task.runcontrol.thread;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import com.yh.infra.svc.gov.sdk.util.DateTimeHelper;
import com.yh.infra.svc.gov.sdk.util.TaskHelper;
import com.yh.infra.svc.gov.sdk.util.converter.ConvertException;
import com.yh.infra.svc.gov.sdk.util.converter.ConvertFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * bean 任务执行器
 */
public class TriggerBeanExecuteThread implements Runnable {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TriggerBeanExecuteThread.class);

    
    
    private String taskCode;
    /**
     * 当前注册中心
     */
    private String registerGroup;
    /**
     * 方法
     */
    private Method method;
    /**
     * 实例
     */
    private Object instance;
    /**
     * 参数
     */
    private String[] args;

    /**
     * 任务计划id
     */
    private Long taskPlanId;
    /**
     * 计划触发时间
     */
    private Date planTriggerTime;
    /**
     * 租户日志级别控制
     */
    private List<String> logLevelList;

    /**
     * 构造函数
     * @param registerGroup
     * @param method
     * @param instance
     * @param args
     * @param taskPlanId
     * @param planTriggerTime
     * @param logLevelList
     */
    public TriggerBeanExecuteThread(String taskCode,String registerGroup, Method method, Object instance, String[] args,
                                    Long taskPlanId, Date planTriggerTime, List<String> logLevelList) {
        this.taskCode = taskCode;
        this.registerGroup = registerGroup;
        this.method = method;
        this.instance = instance;
        this.args = args;
        this.taskPlanId = taskPlanId;
        this.planTriggerTime = planTriggerTime;
        this.logLevelList = logLevelList;

        this.init();
    }

    //--------------------------------//
    private TaskCallBackManager taskCallBackManager;
    private String ip = ""; // TIPS: 此ip仅代表sdk-daemon地址，不代表真正任务执行的地址
    private void init() {
        BeanRegistry sc = BeanRegistry.getInstance();
        this.taskCallBackManager = sc.getBean(TaskCallBackManager.class);
        this.ip = sc.<String>getBean(SdkCommonConstant.TASK_DAEMON_IP);
    }
    
    private String errorLog = "BEAN-TASK [" + this.taskCode +"]执行方异常:";
    
    @Override
    public void run() {
        boolean executeResult = false; // 执行结果
        Throwable throwable = null;
        Throwable causedBy = null;
        try {
            // Cache
            TaskHelper.setThreadLocalCache(this.registerGroup,this.taskPlanId, this.planTriggerTime, this.logLevelList);
            //推送start
            this.taskCallBackManager.addLog(this.registerGroup, this.taskPlanId, this.planTriggerTime, "["+ DateTimeHelper.toString(new Date()) + "][" + this.ip + "]推送开始...\n", null, null);

			if (logger.isDebugEnabled())
				logger.debug("method[{}] begin to invoke......", this.method);
            
            Object invokeResult;
            if (null == this.args || this.args.length == 0) {
				if (logger.isDebugEnabled())
					logger.debug("method invoke arg: null ");

                //无参函数调用
                invokeResult = this.method.invoke(this.instance);

            } else {
				if (logger.isDebugEnabled())
					logger.debug("method invoke arg: {} ", Arrays.asList(this.args));
                //有参方法调用
                invokeResult = this.method.invoke(this.instance, this.convertParams());
            }
            executeResult = true;
            //推送end
			if (logger.isDebugEnabled())
				logger.debug("method invoke success: {} ", invokeResult);
            
        } catch (InvocationTargetException e) {
            logger.error(errorLog + "InvocationTargetException", e);
            throwable = e.getTargetException(); //拿到具体的异常
            causedBy = e.getCause();
        } catch (IllegalAccessException e) {
            logger.error(errorLog + "IllegalAccessException", e);
            throwable = e;
            causedBy = e.getCause();
        } catch (IllegalArgumentException e) {
            logger.error(errorLog + "IllegalArgumentException", e);
            throwable = e;
            causedBy = e.getCause();
        } catch (ConvertException e) {
            logger.error(errorLog + "ConvertException", e);
            throwable = e;
            causedBy = e.getCause();
        } catch (Exception e) { // 其他异常
            logger.error(errorLog + "Exception", e);
            throwable = e;
            causedBy = e.getCause();
        } finally {
            String timestemp = "[" + DateTimeHelper.toString(new Date()) + "]";
            String process = executeResult ? "推送结束" : "推送异常";
            String processInfo = timestemp + "[" + this.ip + "]" + process +"...\n" + TaskHelper.getExceptionMessage(throwable);
            String errorInfo = executeResult ? "" : timestemp + TaskHelper.getExceptionAllinformation(causedBy, throwable) + "\n";
            // 判断异常类型
            if((!Arrays.asList(this.args).contains(SdkCommonConstant.PARAM_TASK_PLAN_ID) &&
                    !Arrays.asList(this.args).contains(SdkCommonConstant.PARAM_REGISTER_GROUP) && executeResult) || !executeResult) {
                this.taskCallBackManager.addLog(this.registerGroup, this.taskPlanId, this.planTriggerTime,
                        processInfo, errorInfo, executeResult);
            }
        }
    }

    /**
     * 参类型转换
     * @return
     * @throws ConvertException
     */
    private Object[] convertParams() throws ConvertException {
        Class<?>[] classArr = this.method.getParameterTypes();
        Class<?> clazz;
        Object[] objectArr = new Object[this.args.length];
        for (int i = 0; i < classArr.length; i++) {
            clazz = classArr[i];
            String fieldValue = this.args[i].trim();
            Object targetValue;
            //首先验证sysdate
            if(fieldValue.equals(SdkCommonConstant.PARAM_SYSDATE)) {
                targetValue = this.planTriggerTime;
            }
            //taskPlanId
            else if(fieldValue.equals(SdkCommonConstant.PARAM_TASK_PLAN_ID)) {
                targetValue = this.taskPlanId;
            } else if(fieldValue.equals(SdkCommonConstant.PARAM_REGISTER_GROUP)) {
                targetValue = this.registerGroup;
            } else if (clazz.isAssignableFrom(fieldValue.getClass())) {
                targetValue = fieldValue;
            } else {
                // 转换当前的值为目标参数类型 TODO replace as spring-core or beanUtil
                targetValue = ConvertFactory.convert(clazz, fieldValue);
            }
            objectArr[i] = targetValue;
        }
        return objectArr;
    }
}
