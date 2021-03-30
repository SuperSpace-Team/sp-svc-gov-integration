package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.List;

public class PrivilegeCommand implements Serializable {

	private static final long serialVersionUID = -111455473418759214L;

	private Long id;
	private String acl;
	private String name;
	/** 组织类型 */
	private Long ouTypeId;
	private Integer type;

	/** 分组名称 */
	private String groupName;
	 /** 序号 */
    private Integer sortNo;

	private List<PrivilegeFunctionCommand> privilegeFunctions;

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

	public List<PrivilegeFunctionCommand> getPrivilegeFunctions() {
		return privilegeFunctions;
	}

	public void setPrivilegeFunctions(List<PrivilegeFunctionCommand> privilegeFunctions) {
		this.privilegeFunctions = privilegeFunctions;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}


}
