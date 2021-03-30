package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class PrivilegeFunctionCommand  implements Serializable {
	
	private static final long serialVersionUID = -1706429296654201525L;

	private Long id;
	
	 /** 权限ID */
    private Long privilegeId;
    
    /** 权限类型 */
    private String functionType;
    
    /** 描述 */
    private String description;
    
	private boolean selected;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
    
    
}
