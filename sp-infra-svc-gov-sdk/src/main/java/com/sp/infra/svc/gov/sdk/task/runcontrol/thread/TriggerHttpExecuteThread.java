package com.sp.infra.svc.gov.sdk.task.runcontrol.thread;

import com.sp.infra.svc.gov.sdk.command.HttpClientResponse;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.constant.SchedulerRequestMethod;
import com.sp.infra.svc.gov.sdk.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.sp.infra.svc.gov.sdk.task.support.HttpTaskExecutor;
import com.sp.infra.svc.gov.sdk.task.support.TaskCallBackManager;
import com.sp.infra.svc.gov.sdk.util.DateTimeHelper;
import com.sp.infra.svc.gov.sdk.util.StringUtils;
import com.sp.infra.svc.gov.sdk.util.TaskHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TriggerHttpExecuteThread implements Runnable{

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(TriggerHttpExecuteThread.class);

    private String taskCode;

    private String registerGroup;

    private String httpUrl;

    private Map<String, Object> httpParams; // 请求参数

    private Integer httpRequestMethod;

    private String contentType;

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
     * @param httpUrl
     * @param httpParams
     * @param httpRequestMethod
     * @param contentType
     * @param taskPlanId
     * @param planTriggerTime
     * @param logLevelList
     */
    public TriggerHttpExecuteThread(String taskCode, String registerGroup, String httpUrl, Map<String, Object> httpParams,
                                    Integer httpRequestMethod, String contentType,
                                    Long taskPlanId, Date planTriggerTime, List<String> logLevelList) {
        this.taskCode = taskCode;
        this.registerGroup = registerGroup;
        this.httpUrl = httpUrl;
        this.httpParams = httpParams;
        this.httpRequestMethod = httpRequestMethod;
        this.contentType = contentType;
        this.taskPlanId = taskPlanId;
        this.planTriggerTime = planTriggerTime;
        this.logLevelList = logLevelList;
        this.init();
    }
    //--------------------------------//
    private TaskCallBackManager taskCallBackManager;
    private HttpTaskExecutor httpTaskExecutor;
    private void init() {
        BeanRegistry sc = BeanRegistry.getInstance();
        this.taskCallBackManager = sc.getBean(TaskCallBackManager.class);
        this.httpTaskExecutor = sc.getBean(HttpTaskExecutor.class);
    }


    /**
     * 执行方法
     */
    @Override
    public void run() {
        try {
            
            // Cache -->cache无效 因为http请求是另外一个线程
            // 改成把变量存储到header中
            Map<String, String> headerMap = TaskHelper.buildMapCache(this.registerGroup,this.taskPlanId, this.planTriggerTime, this.logLevelList, this.contentType);
            //请求类型
            SchedulerRequestMethod requestMethod = SchedulerRequestMethod.valueOf(httpRequestMethod);
            //推送start
            this.taskCallBackManager.addLog(this.registerGroup, this.taskPlanId, this.planTriggerTime, "["+ DateTimeHelper.toString(new Date()) + "]推送开始...\n", null, null);

            // 参数转换
            this.convertParams();

			if (logger.isDebugEnabled())
				logger.debug("http task[{}] URL=[{}]", this.taskCode, this.httpUrl);
            //执行任务
            HttpClientResponse response = this.httpTaskExecutor.execute(httpUrl, httpParams, requestMethod, headerMap);
            
			if (logger.isDebugEnabled())
				logger.debug("http task[{}] response is :[{}]", this.taskCode, response);
            //检查返回结果
            this.checkResponse(response);
            //推送结束
            if(httpParams == null || (!httpParams.keySet().contains(SdkCommonConstant.JSON_TASK_PLAN_ID) &&
                    !httpParams.keySet().contains(SdkCommonConstant.JSON_REGISTER_GROUP))) {
                this.taskCallBackManager.addLog(this.registerGroup, this.taskPlanId, this.planTriggerTime, "[" + DateTimeHelper.toString(new Date()) + "]推送结束...\n", null, true);
            }

        } catch (Exception e) {
            logger.error("http task[" + this.taskCode +"]invoke Error!", e);
            
            this.taskCallBackManager.addLog(this.registerGroup, this.taskPlanId, this.planTriggerTime, 
                "[" + DateTimeHelper.toString(new Date()) + "]推送异常...\n" + TaskHelper.getExceptionMessage(e.getMessage()),
                "[" + DateTimeHelper.toString(new Date()) + "]异常Stack: \n" + TaskHelper.getExceptionAllinformation(e) + "\n",  false);

        }
    }

    /**
     * 参数转换
     */
    private void convertParams() {
        
        if (null != this.httpParams && !this.httpParams.isEmpty()) {
            // 触发时间
            Object p_sysdate = httpParams.get(SdkCommonConstant.JSON_SYSDATE);
            if (null != p_sysdate && SdkCommonConstant.PARAM_SYSDATE.equals(p_sysdate)) {
                this.httpParams.put(SdkCommonConstant.JSON_SYSDATE, DateTimeHelper.toString(this.planTriggerTime));
            }
            // 任务计划ID
            Object p_taskPlanId = httpParams.get(SdkCommonConstant.JSON_TASK_PLAN_ID);
            if (null != p_taskPlanId && SdkCommonConstant.PARAM_TASK_PLAN_ID.equals(p_taskPlanId)) {
                this.httpParams.put(SdkCommonConstant.JSON_TASK_PLAN_ID, this.taskPlanId);
            }
            // 集群
            Object p_registerGroup = httpParams.get(SdkCommonConstant.JSON_REGISTER_GROUP);
            if (null != p_registerGroup && SdkCommonConstant.PARAM_REGISTER_GROUP.equals(p_registerGroup)) {
                this.httpParams.put(SdkCommonConstant.JSON_REGISTER_GROUP, this.registerGroup);
            }
        }
    }
    
    /**
     * _检测返回结果
     * @param response
     */
    private void checkResponse(HttpClientResponse response) {
        
        if (null == response) {
            logger.error("response is null!");
            return; // 我也不知道结果
        }
        String error = response.getError();
        if (StringUtils.isNotBlank(error)) {
            Throwable throwable = new Throwable(error);
            throw new BusinessException(error, throwable);
        }
        // 如果没有异常，查看返回值
        if (!response.isOk()) {
            String message = response.getEntity();
            Throwable throwable = new Throwable(message);
            throw new BusinessException(message, throwable);
        }
        
    }


}
