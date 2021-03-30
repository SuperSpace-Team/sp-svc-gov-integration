package com.yh.infra.svc.gov.sdk.task.support.httpClient;

import com.yh.infra.svc.gov.sdk.exception.BusinessException;
import com.yh.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.platform.baseservice.task.manager.log.TaskLogApiExecuteManager;
import com.yh.infra.svc.gov.sdk.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @desc 任务日志dapter declare as "taskLogApiExecuteManager"
 * 
 * @author MSH8244
 *
 */
public class TaskLogApiExecuteManagerAdapter implements TaskLogApiExecuteManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskLogApiExecuteManagerAdapter.class);

    /**
     * taskLogRestUrl注入 ${api-gateway.http.url}/task/task_log/
     */
    private String taskLogRestUrl;

    @Override
    public void addTaskRunLogRealTime(String registerGroup, Long taskPlanId, Date planTriggerTime, String processInfo, String errorInfo, Boolean result) throws BusinessException {
        this.checkUrl();
        
        Map<String, Object> map = new HashMap<>();
        map.put("registerGroup", registerGroup);
        map.put("taskPlanId", taskPlanId);
        map.put("planTriggerTime", DateTimeHelper.toString(planTriggerTime));
        map.put("processInfo", processInfo);
        map.put("errorInfo", errorInfo);
        map.put("result", result);

        String json = JsonUtil.writeValue(map);
        String encryptStr = AesAuthUtil.encrypt(json);

        if (!taskLogRestUrl.endsWith("/")) {
            taskLogRestUrl += "/";
        }
        String logApiUrl = taskLogRestUrl + taskPlanId;

		if (logger.isDebugEnabled())
			logger.debug("addTaskRunLogRealTime: {}", logApiUrl);

        try {
            HttpJsonClient.postJsonData(logApiUrl, encryptStr, 0, null);
        } catch (Exception e) {
            logger.error("update task log error.",e);
        }
    }

    /**
     * @title 非实时
     */
    @Override
    public void addTaskRunLogNotRealTime(String registerGroup, Long taskPlanId, Date planTriggerTime, List<String> processList, List<String> errorInfoList, Boolean result) throws BusinessException {
        
        String processInfo = MessageCombineUtil.combindMessage(processList);
        String errorInfo = MessageCombineUtil.combindMessage(errorInfoList);
        this.addTaskRunLogRealTime(registerGroup, taskPlanId, planTriggerTime, processInfo, errorInfo, result);
    }

    /**
     * checkUrl
     */
    private void checkUrl() {
        if (StringUtils.isBlank(this.taskLogRestUrl)) {
            throw new IllegalArgumentException("task callback url is empty!");
        }
        boolean matches = Pattern.matches(".*/task/log/|.*/task/task_log/", this.taskLogRestUrl);

        if (!matches) {
            throw new IllegalArgumentException("task callback url is NOT endwith '/task/task_log/' or '/task/log/' !");
        }
    }

    public void setTaskLogRestUrl(String taskLogRestUrl) {
        this.taskLogRestUrl = taskLogRestUrl;
    }
}
