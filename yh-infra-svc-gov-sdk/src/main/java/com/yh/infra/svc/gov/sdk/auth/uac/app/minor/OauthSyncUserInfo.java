package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CommonUserInfo;

/**
 * 同步用户信息接口
 * @author wenjin.gao
 * @Date 2015年9月8日  下午1:09:45
 * @Version 
 * @Description 
 *
 */
public interface OauthSyncUserInfo {
	
	/**
	 * 同步用户信息
	 * @author wenjin.gao
	 * @param commonUser
	 * @return true 同步成功<br> false 同步失败
	 */
	boolean syncUserInfo(CommonUserInfo commonUser);
	
}
