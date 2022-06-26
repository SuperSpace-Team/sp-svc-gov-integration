/*
 * HttpTaskExecute.java Created On 2018年3月15日
 * Copyright(c) 2018 SuperSpace Inc.
 * ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.task.support;

import com.sp.infra.svc.gov.sdk.command.HttpClientResponse;
import com.sp.infra.svc.gov.sdk.constant.SchedulerRequestMethod;

import java.util.Map;

/**
 * HttpTaskExecute
 * 自定义实现任务触发接口
 * @time: 上午11:20:40
 * @author mazan
 */
public interface HttpTaskExecutor {
    /**
     * Http回调接口方法
     * @param httpUrl
     * @param requestMethod
     * @return String
     */
    HttpClientResponse execute(String httpUrl, Map<String, Object> paramsMap, SchedulerRequestMethod requestMethod, Map<String, String> headerMap);
}

