package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CustomButtonCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCustomButtonManagerImpl implements AdminCustomButtonManager {



	@Override
	public CustomButtonCommand saveOrUpdate(CustomButtonCommand command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("command", command);
		String url = UacSdkContext.getDomain() + "/json/admin/customization/add";
		return CommonAuthUtil.authOpCommon(params, url, CustomButtonCommand.class);
	}
	
	@Override
	public CustomButtonCommand get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/customization/get";
		return CommonAuthUtil.authOpCommon(params, url, CustomButtonCommand.class);
	}
	
	@Override
	public Integer delete(List<Long> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/customization/delete";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
}	
