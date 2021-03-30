package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.StdRoleCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPrivilegeManagerImpl implements AdminPrivilegeManager {

	@Override
	public StdRoleCommand findRoleByRoleId(Long ouTypeId, Long appId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ouTypeId", ouTypeId);
		params.put("appId", appId);
		String url = UacSdkContext.getDomain() + "/json/admin/privilege/ouType/role";
		return CommonAuthUtil.authOpCommon(params, url, StdRoleCommand.class);
	}

	@Override
	public PrivilegeCommand saveOrUpdate(PrivilegeCommand command, String[] rps) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		params.put("rps", rps);
		String url = UacSdkContext.getDomain() + "/json/admin/privilege/add";
		return CommonAuthUtil.authOpCommon(params, url, PrivilegeCommand.class);
	}

	@Override
	public Integer delete(List<Long> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/privilege/delete";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}

	@Override
	public PrivilegeCommand get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/privilege/get";
		return CommonAuthUtil.authOpCommon(params, url, PrivilegeCommand.class);
	}
}	
