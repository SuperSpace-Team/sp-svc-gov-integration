package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class CustomButtonCommand implements Serializable {

	private static final long serialVersionUID = -111455473418759214L;

	private Long id;
	private String acl;
	private String funName;
	private String funCode;

	private Long appId;

	 /** 序号 */
    private Integer sortNo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
	}

	
	public String getFunName()
	{
		return funName;
	}

	
	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	
	public String getFunCode()
	{
		return funCode;
	}

	
	public void setFunCode(String funCode)
	{
		this.funCode = funCode;
	}

	
	public Long getAppId()
	{
		return appId;
	}

	
	public void setAppId(Long appId)
	{
		this.appId = appId;
	}

	
	public Integer getSortNo()
	{
		return sortNo;
	}

	
	public void setSortNo(Integer sortNo)
	{
		this.sortNo = sortNo;
	}


}
