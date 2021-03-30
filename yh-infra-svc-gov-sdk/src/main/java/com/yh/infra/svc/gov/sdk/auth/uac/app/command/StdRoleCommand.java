package com.yh.infra.svc.gov.sdk.auth.uac.app.command;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StdRoleCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2993359879636037949L;

	private RoleCommand roleCommand;
	
	private List<PrivilegeQueryCommand> dataPris;
	
	private Map<String, List<PrivilegeQueryCommand>> funPriMap;
	
	private List<PrivilegeGroupCommand> groupCommands;
	
	private List<SysDictionary> pdts;
	
	private List<SysDictionary> pops;

	public RoleCommand getRoleCommand() {
		return roleCommand;
	}

	public void setRoleCommand(RoleCommand roleCommand) {
		this.roleCommand = roleCommand;
	}

	public List<PrivilegeQueryCommand> getDataPris() {
		return dataPris;
	}

	public void setDataPris(List<PrivilegeQueryCommand> dataPris) {
		this.dataPris = dataPris;
	}

	public Map<String, List<PrivilegeQueryCommand>> getFunPriMap() {
		return funPriMap;
	}

	public void setFunPriMap(Map<String, List<PrivilegeQueryCommand>> funPriMap) {
		this.funPriMap = funPriMap;
	}

	public List<PrivilegeGroupCommand> getGroupCommands() {
		return groupCommands;
	}

	public void setGroupCommands(List<PrivilegeGroupCommand> groupCommands) {
		this.groupCommands = groupCommands;
	}

	public List<SysDictionary> getPdts() {
		return pdts;
	}

	public void setPdts(List<SysDictionary> pdts) {
		this.pdts = pdts;
	}

	public List<SysDictionary> getPops() {
		return pops;
	}

	public void setPops(List<SysDictionary> pops) {
		this.pops = pops;
	}
	 
	
}
