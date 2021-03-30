package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.BackWarnEntity;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPassRuleConfigManagerImpl implements AdminPassRuleConfigManager {



	@Override
	public BackWarnEntity update(List<String> selectStr,
								 List<String> selectValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selectStr", selectStr);
		params.put("selectValue", selectValue);
		String url = UacSdkContext.getDomain() + "/json/admin/pwd/role/update";
		return CommonAuthUtil.authOpCommon(params, url, BackWarnEntity.class);
	}
}	
