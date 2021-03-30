package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RoleCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RolePrivilege;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminRoleManagerImpl implements AdminRoleManager {


	@Override
	public RoleCommand saveOrUpdate(RoleCommand command, String[] rps) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		params.put("rps", rps);
		String url = UacSdkContext.getDomain() + "/json/admin/role/add";
		return CommonAuthUtil.authOpCommon(params, url, RoleCommand.class);
	}

	@Override
	public Integer update(Integer lifecycle, List<Long> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lifecycle", lifecycle);
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/role/update";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}

	@Override
	public RoleCommand get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/role/get";
		return CommonAuthUtil.authOpCommon(params, url, RoleCommand.class);
	}

	@Override
	public List<RolePrivilege> getRoles(Long roleId, Long appId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("appId", appId);
		String url = UacSdkContext.getDomain() + "/json/admin/role/roles";
		return CommonAuthUtil.authOpCommonList(params, url, RolePrivilege.class);
	}
}	
