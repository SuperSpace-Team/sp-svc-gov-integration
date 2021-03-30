package com.yh.infra.svc.gov.sdk.task.controller;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.task.command.TaskReturnObj;
import com.yh.infra.svc.gov.sdk.task.runcontrol.TriggerTaskManager;
import com.yh.infra.svc.gov.sdk.util.AesAuthUtil;
import com.yh.infra.svc.gov.sdk.util.DateTimeHelper;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 15:44 2019/12/16
 * @Modified by:
 */
@Controller
@RequestMapping("/task")
public class TriggerTaskController {

    private static final Logger logger = LoggerFactory.getLogger(TriggerTaskController.class);

    @Resource
    private TriggerTaskManager triggerTaskManager;

    /**
     * 使用HTTP获取触发任务 入口
     * @param encryptStr
     * @return
     */
    @RequestMapping(value = "/trigger",method = RequestMethod.POST)
    public @ResponseBody TaskReturnObj trigger(@RequestBody String encryptStr) {
        Date endTime = new Date();

        // 解析参数
        Map<String, Object> params;
        try {
            String decryptStr = AesAuthUtil.decrypt(encryptStr);
            params = JsonUtil.readValue(decryptStr, Map.class);
        } catch (Exception e) {
            logger.error("参数解析失败: {}", encryptStr);
            return new TaskReturnObj(false, DateTimeHelper.toString(endTime), 0, e.getMessage());
        }

        String registerGroup = (String) params.get("registerGroup"); // 任务集群
        Integer type = Integer.valueOf(params.get("type").toString()); // 任务类型（1.dubbo 2.http）
        Long taskPlanId = Long.valueOf((String) params.get("taskPlanId"));// 任务计划ID
        String args = (String) params.get("param"); // 参数
        String taskCode = (String) params.get("code"); // 任务编码
        String logLevel = (String) params.get("logLevel"); // 日志级别
        // 任务执行触发时间
        Date planTriggerTime;
        planTriggerTime = DateTimeHelper.getDate((String) params.get("runPlanTime"));
        if (null == planTriggerTime) {
            planTriggerTime = new Date();
        }
        // 任务触发
        try {
            if (SdkCommonConstant.APP_TYPE_DUBBO == type) {
                // 调用Bean方法
                logger.info("task/trigger :{} bean invoke--------", taskCode);

                String beanName = (String) params.get("beanName");
                String methodName = (String) params.get("methodName");
                endTime = triggerTaskManager.triggerTask(registerGroup, beanName, methodName, args, taskCode, taskPlanId, planTriggerTime, logLevel);

            } else if (SdkCommonConstant.APP_TYPE_HTTP == type) {
                // 调用HTTP方法
                logger.info("task/trigger :{} http invoke--------", taskCode);

                Integer httpRequestMethod = Integer.valueOf((String) params.get("requestMethod"));
                String httpUrl = (String) params.get("requestUrl");
                String contentType = (String) params.get("requestContentType");
                endTime = triggerTaskManager.triggerHttpTask(registerGroup, taskCode, httpUrl, args, httpRequestMethod, contentType, taskPlanId, planTriggerTime, logLevel);
            }

        } catch (Exception e) {
            logger.error("[" + taskCode + "]trigger task error：", e);
            return new TaskReturnObj(false, DateTimeHelper.toString(endTime), 0, e.getMessage()); // 异常信息没有返回
        }
        return new TaskReturnObj(true, DateTimeHelper.toString(endTime));
    }
}
