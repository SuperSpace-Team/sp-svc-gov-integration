package com.sp.infra.svc.gov.sdk.task.support.httpClient;

import com.sp.infra.svc.gov.sdk.platform.baseservice.task.manager.task.TaskPlanExecuteManager;
import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.util.AesAuthUtil;
import com.sp.infra.svc.gov.sdk.util.DateTimeHelper;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * declare as taskPlanExecuteManager
 * @author MSH8244
 *
 */
public class TaskPlanExecuteManagerAdapter implements TaskPlanExecuteManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskPlanExecuteManagerAdapter.class);
	
    /**
     * taskPlanRestUrl注入 ${api-gateway.http.url}/task/task_plan/
     */
    private String taskPlanRestUrl;
    
    /**
     * 更新任务计划
     */
    @Override
    public void updateTaskPlan(String registerGroup, Long taskPlanId, Date runRealTime, Date taskPlanTime) {
        this.checkUrl();
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("registerGroup", registerGroup);
        map.put("taskPlanId", taskPlanId);
        map.put("runRealTime", DateTimeHelper.toString(runRealTime));
        map.put("taskPlanTime", DateTimeHelper.toString(taskPlanTime));
        
        String json = JsonUtil.writeValue(map);
        String encryptStr = AesAuthUtil.encrypt(json);
        
        if (!taskPlanRestUrl.endsWith("/")) {
            taskPlanRestUrl += "/";
        }
		String logApiUrl = taskPlanRestUrl + taskPlanId;
		
		logger.debug("updateTaskPlan: {}", logApiUrl);
        try {
            HttpJsonClient.putJsonData(logApiUrl, encryptStr, 0, null);
        } catch (Exception e) {
            logger.error("update task plan error.",e);
        }
    }

    /**
     * checkUrl
     */
    private void checkUrl() {
        if (StringUtils.isBlank(this.taskPlanRestUrl)) {
            throw new IllegalArgumentException("task callback url is empty!");
        }
        boolean matches = Pattern.matches(".*/task/plan/|.*/task/task_plan/", this.taskPlanRestUrl);

        if(!matches){
            throw new IllegalArgumentException("task callback url is NOT endwith '/task/task_plan/' or 'task/plan/'!");
        }
    }

    public void setTaskPlanRestUrl(String taskPlanRestUrl) {
        this.taskPlanRestUrl = taskPlanRestUrl;
    }
}
