package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeAndUrlCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivilegeAndUrlManagerImpl implements PrivilegeAndUrlManager {

	@Override
	public List<PrivilegeAndUrlCommand> findPrivilegeAndUrl(String groupName, Long ouType, Integer type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("groupName", groupName);
		params.put("ouType", ouType);
		params.put("type", type);
		String url = UacSdkContext.getDomain() + "/common/auth/privilegeUrl/findPrivilegeAndUrl";
		List<PrivilegeAndUrlCommand> paucList = CommonAuthUtil.authOpCommonList(params, url, PrivilegeAndUrlCommand.class);
		return paucList;
	}

	@Override
	public long savePrivilegeAndUrls(Map<String, List<PrivilegeAndUrlCommand>> urlMap) {
		// TODO Auto-generated method stub
		return 0;
	}

}
