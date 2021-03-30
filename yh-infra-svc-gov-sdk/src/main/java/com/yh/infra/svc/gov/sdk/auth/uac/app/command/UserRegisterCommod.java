package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * 描述: 用戶自註冊實例對象
 * 版权: Copyright (c) 2019
 * 作者: luchao1@yonghui.cn
 * 版本: 1.0
 * 创建日期: 2019年7月25日
 * 创建时间: 上午11:32:36
 */
public class UserRegisterCommod implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 描述:
     * 版权: Copyright (c) 2019
     * 作者: luchao1@yonghui.com
     * 版本: 1.0
     * 创建日期: 2019年7月25日
     * 创建时间: 上午11:32:33
     */

    //登录名
    private String loginName;
    //姓名
    private String userName;
    //邮箱
    private String email;
    //联系电话
    private String phone;
    //隶属企业
    private String orgCode;
    //企业首页地址
    private String callBackUrl;
    //邮件类型0:text;1：html;2:暂无
    private Integer emailType = 2;
    //邮件内容
    private String emailBody;
    //描述
    private String description;
    //saas租户编码
    private String saasTenantCode;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgCode() {
        return orgCode;
    }


    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public Integer getEmailType() {
        return emailType;
    }

    public void setEmailType(Integer emailType) {
        this.emailType = emailType;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaasTenantCode() {
        return saasTenantCode;
    }

    public void setSaasTenantCode(String saasTenantCode) {
        this.saasTenantCode = saasTenantCode;
    }

    public UserRegisterCommod(String loginName, String userName, String email, String phone, String orgCode, String callBackUrl, Integer emailType, String emailBody, String description, String saasTenantCode) {
        this.loginName = loginName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.orgCode = orgCode;
        this.callBackUrl = callBackUrl;
        this.emailType = emailType;
        this.emailBody = emailBody;
        this.description = description;
        this.saasTenantCode = saasTenantCode;
    }

    public UserRegisterCommod() {
        super();
    }

    @Override
    public String toString() {
        return "UserRegisterCommod{" +
                "loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", emailType=" + emailType +
                ", emailBody='" + emailBody + '\'' +
                ", description='" + description + '\'' +
                ", saasTenantCode='" + saasTenantCode + '\'' +
                '}';
    }
}
