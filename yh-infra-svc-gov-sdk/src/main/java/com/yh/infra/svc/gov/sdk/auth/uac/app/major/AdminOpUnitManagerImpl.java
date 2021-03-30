package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnit;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminOpUnitManagerImpl implements AdminOpUnitManager {

	@Override
	public List<MenuItemCommand> tree(String appKey) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search_appKey", appKey);
		String url = UacSdkContext.getDomain() + "/json/admin/opUnit/tree";
		return CommonAuthUtil.authOpCommonList(params, url, MenuItemCommand.class);
	}

	@Override
	public OperationUnit saveOrUpdate(OperationUnit command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		String url = UacSdkContext.getDomain() + "/json/admin/opUnit/add";
		return CommonAuthUtil.authOpCommon(params, url, OperationUnit.class);
	}

	@Override
	public OperationUnit get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/opUnit/get";
		return CommonAuthUtil.authOpCommon(params, url, OperationUnit.class);
	}

	@Override
	public Integer updateLifecycle(List<Long> ids, Integer lifecycle) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("lifecycle", lifecycle);
		String url = UacSdkContext.getDomain() + "/json/admin/opUnit/update/lifecycle";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}

}
