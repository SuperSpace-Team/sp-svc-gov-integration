package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OperationPrivilegeCommand implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5477706933147806963L;

	private RoleCommand roleCommand;
	
	private List<PrivilegeCommand> dataPris;
	
	private Map<String, List<PrivilegeCommand>> gpcs;
	
	private List<PrivilegeGroupCommand> groupCommands;

	public RoleCommand getRoleCommand() {
		return roleCommand;
	}

	public void setRoleCommand(RoleCommand roleCommand) {
		this.roleCommand = roleCommand;
	}

	public List<PrivilegeCommand> getDataPris() {
		return dataPris;
	}

	public void setDataPris(List<PrivilegeCommand> dataPris) {
		this.dataPris = dataPris;
	}

	public Map<String, List<PrivilegeCommand>> getGpcs() {
		return gpcs;
	}

	public void setGpcs(Map<String, List<PrivilegeCommand>> gpcs) {
		this.gpcs = gpcs;
	}

	public List<PrivilegeGroupCommand> getGroupCommands() {
		return groupCommands;
	}

	public void setGroupCommands(List<PrivilegeGroupCommand> groupCommands) {
		this.groupCommands = groupCommands;
	}
	
	
}
