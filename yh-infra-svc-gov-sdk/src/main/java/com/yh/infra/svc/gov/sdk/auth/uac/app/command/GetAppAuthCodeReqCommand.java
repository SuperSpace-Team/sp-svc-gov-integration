package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @description: 获取应用鉴权码请求Command
 * @author: luchao
 * @date: Created in 5/18/21 5:56 PM
 */
@AllArgsConstructor
public class GetAppAuthCodeReqCommand implements Serializable {
    private static final long serialVersionUID = -2500636709038181580L;

    /**
     * 应用Key
     * 来源自ITWork应用中心
     */
    private String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
