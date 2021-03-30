package com.yh.infra.svc.gov.sdk.log.command;

/**
 * 日志信息
 *
 * @author hsh10697
 */
public class CommonLogDto {

    /**
     * 租户编码
     */
    private String customerCode;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 店铺编码
     */
    private String orgCode;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 内容，必填
     */
    private String content;
    /**
     * 备注或描述
     */
    private String note;
    /**
     * 业务字段，如订单号或者物流单号等，必填
     */
    private String businessKey;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private String operationTime;

    private String sendTime;
    /**
     * 扩展参数1
     */
    private String extraParam1;
    /**
     * 扩展参数2
     */
    private String extraParam2;

    private String obj;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
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

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
