package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.StdRoleCommand;

import java.util.List;

public interface AdminPrivilegeManager {
	

    StdRoleCommand findRoleByRoleId(Long ouTypeId, Long appId);
	
    PrivilegeCommand saveOrUpdate(PrivilegeCommand command, String[] rps);
	
    Integer delete(List<Long> ids);
	
	PrivilegeCommand get(Long id);
}	
