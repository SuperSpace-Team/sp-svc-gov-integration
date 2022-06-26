package com.sp.infra.svc.gov.sdk.auth.uac.app.command;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @description: 刷新应用Token请求Command
 * 若治理服务端缓存已有尚未过期则直接返回,否则生成新的appToken
 * @author: luchao
 * @date: Created in 5/18/21 5:44 PM
 */
@AllArgsConstructor
public class RefreshNewTokenReqCommand implements Serializable {
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
}
