/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LSH10022
 *
 */
public class RegisetrCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5538254117338096108L;
	
	private String email;//邮箱
	
	private String loginName;//登录名
	
	private String password;//第一次输入密码
	
	private String secondPassword;//二次输入密码
	
	private String orgName;//公司名称

	private String phone;//手机号
	
	private Date date;//当前时间
	
	//accesstoken(即租户的secret)
    private String accessToken;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecondPassword() {
		return secondPassword;
	}

	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
