package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RoleCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RolePrivilege;

import java.util.List;


public interface AdminRoleManager {

	RoleCommand saveOrUpdate(RoleCommand command, String[] rps);
	
	Integer update(Integer lifecycle, List<Long> ids);
	
	RoleCommand get(Long id);
	
	List<RolePrivilege> getRoles(Long roleId, Long appId);
}	
