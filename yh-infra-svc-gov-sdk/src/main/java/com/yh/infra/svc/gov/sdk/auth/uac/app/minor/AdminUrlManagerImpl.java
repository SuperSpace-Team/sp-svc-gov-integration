package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.BackWarnEntity;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.UrlCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUrlManagerImpl implements AdminUrlManager {

	@Override
	public UrlCommand saveOrUpdate(UrlCommand command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		String url = UacSdkContext.getDomain() + "/json/admin/url/add";
		return CommonAuthUtil.authOpCommon(params, url, UrlCommand.class);
	}

	@Override
	public Integer delete(List<Long> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/url/delete";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}

	@Override
	public UrlCommand get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/url/get";
		return CommonAuthUtil.authOpCommon(params, url, UrlCommand.class);
	}
	@Override
	public BackWarnEntity upload(List<UrlCommand> list) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("list", list);
		String url = UacSdkContext.getDomain() + "/json/admin/url/upload";
		return CommonAuthUtil.authOpCommon(params, url, BackWarnEntity.class);
	}
}
