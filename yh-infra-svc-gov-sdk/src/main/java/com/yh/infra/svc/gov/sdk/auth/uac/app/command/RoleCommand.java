package com.yh.infra.svc.gov.sdk.auth.uac.app.command;


import java.io.Serializable;
import java.util.List;

public class RoleCommand implements Serializable {
	
	private static final long serialVersionUID = 7054010540229427420L;
	private Long id;
	/** 角色名称 */
    private String name;
    
    private String ouTypeName;

    /** 组织类型 */
    private Long ouTypeId;
    
    private List<Long> functionIds;
    
    private List<RolePrivilegeCommand> rolePris;
    
    private List<RolePrivilege> rps;
    

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Long> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
	}

	public String getOuTypeName() {
		return ouTypeName;
	}

	public void setOuTypeName(String ouTypeName) {
		this.ouTypeName = ouTypeName;
	}

	public List<RolePrivilegeCommand> getRolePris() {
		return rolePris;
	}

	public void setRolePris(List<RolePrivilegeCommand> rolePris) {
		this.rolePris = rolePris;
	}

	public List<RolePrivilege> getRps() {
		return rps;
	}

	public void setRps(List<RolePrivilege> rps) {
		this.rps = rps;
	}



	
}

