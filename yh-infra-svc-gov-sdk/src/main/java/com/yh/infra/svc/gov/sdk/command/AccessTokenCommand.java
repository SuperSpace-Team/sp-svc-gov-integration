package com.yh.infra.svc.gov.sdk.command;

import java.io.Serializable;

/**
 * 返回的accessToken对象
 * @author Alex Lu
 *
 */
public class AccessTokenCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500636709038181580L;

	@Override
	public String toString() {
		return "AccessTokenCommand [accessToken=" + accessToken + ", expireTime=" + expireTime + "]";
	}

	private String accessToken;
	
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
