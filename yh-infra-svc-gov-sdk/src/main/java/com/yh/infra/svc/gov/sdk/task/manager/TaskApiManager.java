package com.yh.infra.svc.gov.sdk.task.manager;

import com.alibaba.fastjson.JSONObject;
import com.yh.common.lark.common.dao.Pagination;
import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.command.HttpClientResponse;
import com.yh.infra.svc.gov.sdk.exception.BusinessException;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.task.command.*;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.QueryBean;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 14:43 2020/1/17
 * @Modified by:
 */
public class TaskApiManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskApiManager.class);

    private String gatewayUrl;

    /**
     * 创建任务
     *
     * @param reqVO
     * @return
     */
    public TaskReturnObj create(TaskReqVO reqVO) {
        try {
            String url = String.format("%s/task/create", gatewayUrl);
            HttpClientResponse response = HttpJsonClient.postJsonData(url, JsonUtil.toJson(reqVO), 5, this.getHeaders());
            TaskReturnObj parsing = this.parsing(response);
            if (!parsing.isResultFlag() && parsing.getErrorCode() == 2) {
                getUacService().resetToken();
            }
            return parsing;
        } catch (Exception e) {
            logger.error("", e);
            String message = e.getMessage();
            return new TaskReturnObj(false, message == null ? "The create task throws an exception" : message);
        }
    }

    /**
     * 修改任务
     *
     * @param reqVO
     * @return
     */
    public TaskReturnObj modify(TaskReqVO reqVO) {
        try {
            String url = String.format("%s/task/modify", gatewayUrl);
            HttpClientResponse response = HttpJsonClient.postJsonData(url, JsonUtil.toJson(reqVO), 5, this.getHeaders());
            TaskReturnObj parsing = this.parsing(response);
            if (!parsing.isResultFlag() && parsing.getErrorCode() == 2) {
                getUacService().resetToken();
            }
            return parsing;
        } catch (Exception e) {
            logger.error("", e);
            String message = e.getMessage();
            return new TaskReturnObj(false, message == null ? "The modify task throws an exception" : message);
        }
    }

    /**
     * 查询任务详情
     *
     * @param registerGroup
     * @param taskCode
     * @return
     */
    public TaskReqVO find(String registerGroup, String taskCode) {

        String url = String.format("%s/task/find/%s/%s", gatewayUrl, registerGroup, taskCode);
        HttpClientResponse response = HttpJsonClient.getData(url, null, 5, null);

        TaskReturnObj taskReturnObj = this.parsing(response);

        if (!taskReturnObj.isResultFlag()) {
            throw new BusinessException(taskReturnObj.getErrorCode(), taskReturnObj.getErrorMsg());
        }
        return JsonUtil.parseJson(JsonUtil.toJson(taskReturnObj.getData()), TaskReqVO.class);
    }

    /**
     * 按租户查询任务详情
     *
     * @param registerGroup
     * @param appKey
     * @param query
     * @return
     */
    public Pagination<SchedulerTaskCommand> findAppTask(String registerGroup, String appKey, QueryBean query) {

        String url = String.format("%s/task/findAppTask/%s/%s", gatewayUrl, registerGroup, appKey);
        TaskReturnObj taskReturnObj = postJsonDataImpl(query, url);
        return JsonUtil.parseJson(JsonUtil.toJson(taskReturnObj.getData()), Pagination.class);
    }

    /**
     * 任务计划查询
     *
     * @param appKey
     * @param taskCode
     * @param currentDate
     * @param query
     * @return
     */
    public Pagination<TaskPlanCommand> findTaskPlan(String appKey, String taskCode, String currentDate, QueryBean query) {

        String url = String.format("%s/task/findTaskPlan/%s/%s/%s", gatewayUrl, appKey, taskCode, currentDate);
        TaskReturnObj taskReturnObj = postJsonDataImpl(query, url);

        ObjectMapper mapper = getObjectMapper();
        List<TaskPlanCommand> list = mapper.convertValue(JSONObject.parseObject(JsonUtil.toJson(taskReturnObj.getData()))
                .get("items"), new TypeReference<List<TaskPlanCommand>>() {
        });
        Pagination<TaskPlanCommand> pagination = JsonUtil.parseJson(JsonUtil.toJson(taskReturnObj.getData()), Pagination.class);
        pagination.setItems(list);
        return pagination;
    }

    /**
     * 任务日志查询
     *
     * @param appKey
     * @param taskCode
     * @param currentDate
     * @param query
     * @return
     */
    public Pagination<TaskLogCommand> findTaskLog(String appKey, String taskCode, String currentDate, QueryBean query) {

        String url = String.format("%s/task/findTaskLog/%s/%s/%s", gatewayUrl, appKey, taskCode, currentDate);
        TaskReturnObj taskReturnObj = postJsonDataImpl(query, url);

        Pagination<TaskLogCommand> pagination = JsonUtil.parseJson(JsonUtil.toJson(taskReturnObj.getData()), Pagination.class);
        ObjectMapper mapper = getObjectMapper();
        List<TaskLogCommand> list = mapper.convertValue(JSONObject.parseObject(JsonUtil.toJson(taskReturnObj.getData()))
                .get("items"), new TypeReference<List<TaskLogCommand>>() {
        });
        pagination.setItems(list);
        return pagination;
    }


    /**
     * 封装http headers
     *
     * @return
     */
    private Header[] getHeaders() {
        UacService uacService = getUacService();
        String token = uacService.getAppToken();

        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(-1, "Tenant authentication failed");
        }
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Authorization", token);
        return headers;
    }

    private UacService getUacService() {
        BeanRegistry br = BeanRegistry.getInstance();

        return br.getBean(UacService.class);
    }

    /**
     * 解析返回body
     *
     * @param response
     * @return
     */
    public TaskReturnObj parsing(HttpClientResponse response) {
        TaskReturnObj taskReturnObj = new TaskReturnObj();

        String error = response.getError();
        if (StringUtils.isNotEmpty(error)) {
            taskReturnObj.setResultFlag(false);
            taskReturnObj.setErrorCode(response.getStatusCode());
            taskReturnObj.setErrorMsg(response.getMessage());
            return taskReturnObj;
        }

        return JsonUtil.parseJson(response.getEntity(), taskReturnObj.getClass());
    }

    /**
     * postJsonData发送和检验
     *
     * @param query
     * @param url
     * @return
     */
    private TaskReturnObj postJsonDataImpl(QueryBean query, String url) {
        HttpClientResponse response = HttpJsonClient.postJsonData(url, JsonUtil.toJson(query), 5, null);
        TaskReturnObj taskReturnObj = this.parsing(response);

        if (!taskReturnObj.isResultFlag()) {
            throw new BusinessException(taskReturnObj.getErrorCode(), taskReturnObj.getErrorMsg());
        }
        return taskReturnObj;
    }

    /**
     * 获取Mapper
     *
     * @return
     */
    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }
}
