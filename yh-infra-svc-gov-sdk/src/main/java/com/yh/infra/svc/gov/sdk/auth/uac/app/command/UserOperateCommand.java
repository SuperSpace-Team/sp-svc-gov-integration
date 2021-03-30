package com.yh.infra.svc.gov.sdk.auth.uac.app.command;


import java.io.Serializable;
import java.util.List;

public class UserOperateCommand implements Serializable {

	/**
	 * 编辑员工页面
	 */
	private static final long serialVersionUID = 6237352463712852528L;

	
	private Long id;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 组织类型
	 */
	private String ouName;
	
	/**
	 * 工号信息
	 */
	private String jobNumber;
	/**登陆用户名**/
	private String loginName;
	
	private Integer lifecycle;
	
	/**
	 * 权限列表
	 */
	private List<UserRoleCommand> userRoleCommands;
	
	/**组织类型*/
	private List<OperationUnitType> operationUnitTypes;
	
	/***所属组织*/
	private Long ouId;
	
	/**上级组织id*/
	private Long parentOuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public List<UserRoleCommand> getUserRoleCommands() {
		return userRoleCommands;
	}

	public void setUserRoleCommands(List<UserRoleCommand> userRoleCommands) {
		this.userRoleCommands = userRoleCommands;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public List<OperationUnitType> getOperationUnitTypes() {
		return operationUnitTypes;
	}

	public void setOperationUnitTypes(List<OperationUnitType> operationUnitTypes) {
		this.operationUnitTypes = operationUnitTypes;
	}

	public Integer getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public Long getParentOuId() {
		return parentOuId;
	}

	public void setParentOuId(Long parentOuId) {
		this.parentOuId = parentOuId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
}
