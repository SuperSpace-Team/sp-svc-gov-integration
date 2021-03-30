package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.Role;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RoleCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RolePrivilege;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleManagerImpl implements RoleManager {


	@Override
	//X
	public RoleCommand addOrUpdate(RoleCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//X
	public int remove(List<Long> ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RoleCommand getById(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/common/auth/role/getById";
		RoleCommand rc = CommonAuthUtil.authOpCommon(params, url, RoleCommand.class);
		return rc;
	}

	@Override
	public List<Role> getRoles(List<Long> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("ids", JsonUtil.writeValue(ids));
		String url = UacSdkContext.getDomain() + "/common/auth/role/getRoles";
		List<Role> roleList = CommonAuthUtil.authOpCommonList(params, url, Role.class);
		return roleList;
	}

	@Override
	public List<Role> findListByParam(Role role) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("role", role);
		String url = UacSdkContext.getDomain() + "/common/auth/role/findListByParam";
		List<Role> roleList = CommonAuthUtil.authOpCommonList(params, url, Role.class);
		return roleList;
	}

	@Override
	public List<RolePrivilege> findRolePrivilegeByUserIdAndOuId(Long userId, Long ouId) {
		// TODO Auto-generated method stub
		return null;
	}

}
