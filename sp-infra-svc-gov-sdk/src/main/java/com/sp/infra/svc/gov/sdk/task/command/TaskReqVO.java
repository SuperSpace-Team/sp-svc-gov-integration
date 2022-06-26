package com.sp.infra.svc.gov.sdk.task.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 11:03 2020/1/10
 * @Modified by:
 */
public class TaskReqVO{

    private static final Logger logger = LoggerFactory.getLogger(TaskReqVO.class);

    /**
     * 任务集群
     */
    private String registerGroup;

    /**
     * 租户编码
     */
    private String appKeyCode;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 0.禁用
     * 1.启用
     * 2.删除
     */
    private Integer lifecycle;

    /**
     * 业务spring bean名称
     */
    private String beanName;

    /**
     * 业务执行代码方法名
     */
    private String methodName;

    /**
     * 多参数以","分隔；双引号优先,HTTP默认为JSON
     */
    private String args;

    /**
     * 描述
     */
    private String description;

    /**
     * 时间表达式
     */
    private String timeExp;

    /**
     * HTTP 请求方法体 默认1
     * 使用如下枚举可选值：1:GET 2：PUT 3:POST
     * @see TaskEmuns.HTTP_REQUEST_METHOD
     */
    private Integer httpRequestMethod;

    /**
     * 服务端返回内容格式
     */
    private String contentType;

    /**
     * 任务HTTP url
     */
    private String httpUrl;

    /**
     * 任务类型
     * 可选值:1:dubbo的方式 2:http的方式
     * @see TaskEmuns.TYPE
     */
    private Integer type;

    /**
     * 补偿模式默认3
     * 可选值:
     * 1: 1分钟补偿
     * 2: 5分钟补偿
     * 3: 不补偿
     * @see TaskEmuns.COMPENSATE_TYPE
     */
    private Integer compensateType;

    /**
     * 时间配置类型，1表达式，2功能配置
     */
    private Integer timeType;

    public String getRegisterGroup() {
        return registerGroup;
    }

    public void setRegisterGroup(String registerGroup) {
        this.registerGroup = registerGroup;
    }

    public String getAppKeyCode() {
        return appKeyCode;
    }

    public void setAppKeyCode(String appKeyCode) {
        this.appKeyCode = appKeyCode;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeExp() {
        return timeExp;
    }

    public void setTimeExp(String timeExp) {
        this.timeExp = timeExp;
    }

    public Integer getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(Integer httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompensateType() {
        return compensateType;
    }

    public void setCompensateType(Integer compensateType) {
        this.compensateType = compensateType;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }
}
