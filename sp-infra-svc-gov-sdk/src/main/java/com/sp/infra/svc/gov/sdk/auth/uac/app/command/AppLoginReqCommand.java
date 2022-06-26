package com.sp.infra.svc.gov.sdk.auth.uac.app.command;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @description: 应用登录请求Command
 * @author: luchao
 * @date: Created in 5/18/21 5:12 PM
 */
@AllArgsConstructor
public class AppLoginReqCommand implements Serializable {
    private static final long serialVersionUID = -2500636709038181580L;

    /**
     * 应用Key
     * 来源自ITWork应用中心
     */
    private String appKey;

    /**
     * 应用Secret
     * 来源自ITWork应用中心
     */
    private String appSecret;

    /**
     * 应用鉴权码
     * 来源自服务治理平台基础服务生成返回
     */
    private String appAuthCode;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppAuthCode() {
        return appAuthCode;
    }

    public void setAppAuthCode(String appAuthCode) {
        this.appAuthCode = appAuthCode;
    }
}
