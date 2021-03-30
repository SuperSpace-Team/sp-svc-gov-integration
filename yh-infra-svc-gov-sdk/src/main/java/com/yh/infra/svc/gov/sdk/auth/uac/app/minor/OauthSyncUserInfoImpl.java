package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CommonUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OauthSyncUserInfoImpl implements OauthSyncUserInfo {
	
	private static final Logger logger = LoggerFactory.getLogger(OauthSyncUserInfoImpl.class);
	

	@Override
	public boolean syncUserInfo(CommonUserInfo commonUser) {
		System.out.println("同步用户, 缺省");
		return true;
	}

}
