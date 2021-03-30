package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.List;

public class PrivilegeGroupCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092087124109485542L;

	private String groupName;
	
	private List<PrivilegeCommand> privilegeCommands;
	
	private List<PrivilegeQueryCommand> queryCommands;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<PrivilegeCommand> getPrivilegeCommands() {
		return privilegeCommands;
	}

	public void setPrivilegeCommands(List<PrivilegeCommand> privilegeCommands) {
		this.privilegeCommands = privilegeCommands;
	}

	public List<PrivilegeQueryCommand> getQueryCommands() {
		return queryCommands;
	}

	public void setQueryCommands(List<PrivilegeQueryCommand> queryCommands) {
		this.queryCommands = queryCommands;
	}
	
	
}
