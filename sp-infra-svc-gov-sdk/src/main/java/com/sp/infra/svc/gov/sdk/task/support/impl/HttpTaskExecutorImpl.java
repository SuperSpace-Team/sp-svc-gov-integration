/*
 * HttpTaskExecuteImpl.java Created On 2018年3月15日 Copyright(c) 2018 Yonghui Inc. ALL Rights Reserved.
 */
package com.sp.infra.svc.gov.sdk.task.support.impl;


import com.sp.infra.svc.gov.sdk.command.HttpClientResponse;
import com.sp.infra.svc.gov.sdk.constant.SchedulerRequestMethod;
import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.task.support.HttpTaskExecutor;
import com.sp.infra.svc.gov.sdk.util.AesAuthUtil;
import com.sp.infra.svc.gov.sdk.util.CollectionUtils;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpTaskExecuteImpl 默认的定时任务http实现
 *
 * @time: 下午2:47:57
 * @author mazan
 */
public class HttpTaskExecutorImpl implements HttpTaskExecutor {

    /**
     * 手动单例
     *
     * @author MSH8244
     *
     */
    private static class SingletonHolder {
        private final static HttpTaskExecutor instance = new HttpTaskExecutorImpl();
    }

    public static HttpTaskExecutor getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * _执行http请求
     * @param httpUrl
     * @param requestMethod
     * @return
     */
    @Override
    public HttpClientResponse execute(String httpUrl, Map<String, Object> paramsMap, SchedulerRequestMethod requestMethod, Map<String, String> headerMap) {

        Header[] headers = buildHeader(headerMap);
        HttpClientResponse response;
        switch (requestMethod) {
            case GET:
                response = getData(httpUrl, paramsMap, headers);
                break;
            case HEAD:
                throw new UnsupportedOperationException();
                // break;
            case POST:
                // 默认非requestBody的
                response = postData(httpUrl, paramsMap, headers);
                break;
            case PUT:
                response = putData(httpUrl, paramsMap, headers);
                break;
            case PATCH:
                throw new UnsupportedOperationException();
                // break;
            case DELETE:
                throw new UnsupportedOperationException();
                // break;
            case OPTIONS:
                throw new UnsupportedOperationException();
                // break;
            case TRACE:
                throw new UnsupportedOperationException();
                // break;
            default:
                throw new UnsupportedOperationException();
                // break;
        }
        return response;

    }

    /**
     * *创建header
     *
     * @param headerMap
     * @return
     */
    private static Header[] buildHeader(Map<String, String> headerMap) {
        List<Header> headerList = new ArrayList<>(headerMap.size());
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            Header header = new BasicHeader(entry.getKey(), entry.getValue());
            headerList.add(header);
        }
        Header[] headers = new Header[headerList.size()];
        headerList.toArray(headers);
        return headers;
    }

    // =================== Simple ====================//
    /**
     * _定时任务 HTTP 连接通用方法（GET）
     *
     * @param url
     * @return
     */
    public static HttpClientResponse getData(String url, Map<String, Object> paramsMap, Header[] headers) {
        // 返回结果
        return HttpJsonClient.getData(url, paramsMap, 0, headers);
    }

    /**
     * 定时任务 POST 非requestBody
     *
     * @param url
     * @return
     */
    public static HttpClientResponse postData(String url, Map<String, Object> paramsMap, Header[] headers) {
        // 返回结果
        return HttpJsonClient.postFormData(url, paramsMap, 0, headers);
    }

    /**
     * put操作
     *
     * @param url
     * @param paramsMap
     * @param headers
     * @return
     */
    public static HttpClientResponse putData(String url, Map<String, Object> paramsMap, Header[] headers) {
        // 参数
        String encryptStr = null;
        if (!CollectionUtils.isEmpty(paramsMap)) {
            String json = JsonUtil.writeValue(paramsMap);
            encryptStr = AesAuthUtil.encrypt(json);
        }

        return HttpJsonClient.putJsonData(url, encryptStr, 0, headers);

    }
}

