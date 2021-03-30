/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @author LSH10022
 *
 */
public class ValiteCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6163252734878520917L;

	private String email;//邮箱
	
	private String phone;//手机号
	
	private String loginName;//登录名
	
	//accesstoken(即租户的secret)
    private String accessToken;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
