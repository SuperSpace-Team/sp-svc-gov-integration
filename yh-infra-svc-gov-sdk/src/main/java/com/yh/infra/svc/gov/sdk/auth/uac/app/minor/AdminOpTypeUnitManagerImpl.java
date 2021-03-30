package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminOpTypeUnitManagerImpl implements AdminOpTypeUnitManager {

	@Override
	public List<MenuItemCommand> tree(String appKey) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search_appKey", appKey);
		String url = UacSdkContext.getDomain() + "/json/admin/opTypeUnit/tree";
		return CommonAuthUtil.authOpCommonList(params, url, MenuItemCommand.class);
	}

	@Override
	public OperationUnitType saveOrUpdate(OperationUnitType command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		String url = UacSdkContext.getDomain() + "/json/admin/opTypeUnit/add";
		return CommonAuthUtil.authOpCommon(params, url, OperationUnitType.class);
	}

	@Override
	public OperationUnitType get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/opTypeUnit/get";
		return CommonAuthUtil.authOpCommon(params, url, OperationUnitType.class);
	}
}	
