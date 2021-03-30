/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @author LSH10022
 *
 */
public class OutUserAddRoleCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 217252304675029159L;
    //登录名
	private String loginName;
	
	//企业编码
	private String orgCode;
	
	//租户appKey
	private String appKey;
	
	//组织类型中文名称
	private String ouTypeName;
	
	//所属组织名称
	private String ouName;
	
	//角色名称
	private String roleName;
	
	//accesstoken(即租户的secret)
    private String accessToken;
    
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getOuTypeName() {
		return ouTypeName;
	}

	public void setOuTypeName(String ouTypeName) {
		this.ouTypeName = ouTypeName;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
