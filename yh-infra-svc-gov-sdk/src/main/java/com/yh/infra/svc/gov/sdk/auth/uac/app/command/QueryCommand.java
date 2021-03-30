/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @author LSH10022
 *
 */
public class QueryCommand implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 903298141962609651L;

	private  String loginName;//登录名
	
	private  String appKey;//租户appkey
	
	//accesstoken(即租户的secret)
    private String accessToken;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
