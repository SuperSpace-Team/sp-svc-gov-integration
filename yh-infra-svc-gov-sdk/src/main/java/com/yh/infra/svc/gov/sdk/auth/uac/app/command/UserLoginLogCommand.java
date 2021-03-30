/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LSH10022
 * 用户登录日志实例对象
 */
public class UserLoginLogCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //用户id
	private Long  userId;
	//登录名
	private String loginName;
	//登录时间
	private Date   loginTime;
	//登录ip
	private String ip;
	//用户来源
	private Integer userSource;
    //企业编码
	private String orgCode;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getUserSource() {
		return userSource;
	}

	public void setUserSource(Integer userSource) {
		this.userSource = userSource;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
}
