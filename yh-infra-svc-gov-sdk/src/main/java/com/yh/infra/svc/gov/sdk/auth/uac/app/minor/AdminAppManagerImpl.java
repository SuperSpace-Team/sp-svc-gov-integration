package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AppCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAppManagerImpl implements AdminAppManager {
	
	private List<AppCommand> apps = new ArrayList<AppCommand>();


	@Override
	public AppCommand get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/app/get";
		return CommonAuthUtil.authOpCommon(params, url, AppCommand.class);
	}

	@Override
	public Integer state(List<Long> ids, Integer lifecycle) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lifecycle", lifecycle);
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/app/state";
		Integer count = CommonAuthUtil.authOpCommon(params, url, Integer.class);
		apps.clear();
		return count;
	}

	@Override
	public AppCommand saveOrUpdate(AppCommand command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		String url = UacSdkContext.getDomain() + "/json/admin/app/add";
		AppCommand app = CommonAuthUtil.authOpCommon(params, url, AppCommand.class);
		apps.clear();
		return app;
	}

	@Override
	public List<AppCommand> hasAuthAppSystem(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);
		String url = UacSdkContext.getDomain() + "/json/admin/app/hasUserApp";
		return CommonAuthUtil.authOpCommonList(params, url, AppCommand.class);
	}

	
	@Override
	public Integer batchSyncInfo(List<Long> ids, Long appId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("appId", appId);
		String url = UacSdkContext.getDomain() + "/json/admin/app/batch/syncInfo";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
}	
