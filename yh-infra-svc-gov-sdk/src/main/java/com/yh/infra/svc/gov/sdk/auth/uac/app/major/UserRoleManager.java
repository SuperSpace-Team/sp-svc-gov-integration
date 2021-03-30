package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.UserRole;

public interface UserRoleManager {

	@Deprecated
	void removeUserRoleById(Long id);

	@Deprecated
	void saveOrUpdate(UserRole userRole);

}
