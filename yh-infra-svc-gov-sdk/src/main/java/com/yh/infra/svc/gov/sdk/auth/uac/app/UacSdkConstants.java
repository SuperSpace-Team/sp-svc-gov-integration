	package com.yh.infra.svc.gov.sdk.auth.uac.app;

/**
 * SDK的系统常量
 * @author Justin Hu
 *
 */
public class UacSdkConstants {

	private String appKey;
	
	private String appSecret;
	
	private String domain;
	
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

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
