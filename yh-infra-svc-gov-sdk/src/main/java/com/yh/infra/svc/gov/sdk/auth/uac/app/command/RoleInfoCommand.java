/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @author LSH10022
 *
 */
public class RoleInfoCommand implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6457343198023521194L;

	private Long roleId;//角色id
	
	private String roleName;//角色名称
	
	private Long ouId;//组织id
	
	private String ouName;//组织名称
	
	
	private String ouType;//组织类型名称
	
	private Long ouTypeId;//组织类型id

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getOuType() {
		return ouType;
	}

	public void setOuType(String ouType) {
		this.ouType = ouType;
	}

	public Long getOuTypeId() {
		return ouTypeId;
	}

	public void setOuTypeId(Long ouTypeId) {
		this.ouTypeId = ouTypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
