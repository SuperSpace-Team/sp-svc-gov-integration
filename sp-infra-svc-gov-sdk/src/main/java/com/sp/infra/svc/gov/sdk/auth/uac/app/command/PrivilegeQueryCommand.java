package com.sp.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class PrivilegeQueryCommand implements Serializable {

	private static final long serialVersionUID = -111455473418759214L;

	private String name;

	/** 组织类型 */
	private Long ouTypeId;
	
	private Integer type;

	/** 分组名称 */
	private String groupName;
	
	private String acl;
	
	 /** 序号 */
    private Integer sortNo;

	private Long[] funIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOuTypeId() {
		return ouTypeId;
	}

	public void setOuTypeId(Long ouTypeId) {
		this.ouTypeId = ouTypeId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long[] getFunIds() {
		return funIds;
	}

	public void setFunIds(Long[] funIds) {
		this.funIds = funIds;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
	}
}
