package com.yh.svc.gov.test.springboot1.command;

import java.io.Serializable;
import java.util.Date;

/**
 * 该对象封装通用日志信息
 * <p/>
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2018/4/17
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public class CommonLogCommand implements Serializable {

    /**
     * 租户编码
     */
    private String customerCode;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 备注
     */
    private String note;
    /**
     * 内容
     */
    private String content;
    /**
     *号码，如订单号或者物流单号
     */
    private String businessKey;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 扩展参数1
     */
    private String extraParam1;
    /**
     * 扩展参数2
     */
    private String extraParam2;
    /**
     * 扩展字段对象
     */
    private Object obj;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getExtraParam1() {
        return extraParam1;
    }

    public void setExtraParam1(String extraParam1) {
        this.extraParam1 = extraParam1;
    }

    public String getExtraParam2() {
        return extraParam2;
    }

    public void setExtraParam2(String extraParam2) {
        this.extraParam2 = extraParam2;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
