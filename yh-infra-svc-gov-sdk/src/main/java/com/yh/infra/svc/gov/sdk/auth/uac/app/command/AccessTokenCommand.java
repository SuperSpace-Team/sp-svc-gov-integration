package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * UAC返回的accessToken对象
 * @author Alex Lu
 *
 */
public class AccessTokenCommand implements Serializable {
	private static final long serialVersionUID = -2500636709038181580L;

	/**
	 * 返回的access_token
	 */
	private String accessToken;
	
	/**
	 * 过期的时间戳
	 */
	private Long expireTime;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
}
