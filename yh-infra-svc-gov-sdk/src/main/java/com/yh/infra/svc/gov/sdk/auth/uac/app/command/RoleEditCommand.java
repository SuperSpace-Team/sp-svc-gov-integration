package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class RoleEditCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 170709336486492270L;
	
	private String userName;
	
	/**用户Id*/
	private Long userId;
	
	/**工号*/
	private String jobNumber;
	
	/**用户权限id*/
	private Long[] urId;
	/**组织id**/
	private Long[] ouId;
	/**角色id**/
	private Long[] roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long[] getUrId() {
		return urId;
	}

	public void setUrId(Long[] urId) {
		this.urId = urId;
	}

	public Long[] getOuId() {
		return ouId;
	}

	public void setOuId(Long[] ouId) {
		this.ouId = ouId;
	}

	public Long[] getRoleId() {
		return roleId;
	}

	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
