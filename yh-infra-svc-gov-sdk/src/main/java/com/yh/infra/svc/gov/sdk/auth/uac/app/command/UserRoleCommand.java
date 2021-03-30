package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class UserRoleCommand implements Serializable {
	
	
	/**
	 * 用户权限表
	 */
	private static final long serialVersionUID = -2097846439993213217L;

	/**
	 * 权限id
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 组织id
	 */
	private Long ouId;
	
	/**
	 * 组织类型
	 */
	private String ouTypeName;
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 所属组织
	 */
	private String ouName;
	
	/**
	 * 组织类型id
	 */
	private Long ouTypeId;
	
	/**
	 * 父级id
	 */
	private Long parentOuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getOuTypeName() {
		return ouTypeName;
	}

	public void setOuTypeName(String ouTypeName) {
		this.ouTypeName = ouTypeName;
	}

	public Long getOuTypeId() {
		return ouTypeId;
	}

	public void setOuTypeId(Long ouTypeId) {
		this.ouTypeId = ouTypeId;
	}

	public Long getParentOuId() {
		return parentOuId;
	}

	public void setParentOuId(Long parentOuId) {
		this.parentOuId = parentOuId;
	}
	
	
}
