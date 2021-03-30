package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class RolePrivilegeCommand  implements Serializable {

	private static final long serialVersionUID = -4973797402160808320L;
	
	private String acl;
	
	private String funcodes;

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
	}

	public String getFuncodes() {
		return funcodes;
	}

	public void setFuncodes(String funcodes) {
		this.funcodes = funcodes;
	}
	

}
