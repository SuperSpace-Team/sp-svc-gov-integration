package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

/**
 * SESSION key相关的常量
 * @author Justin Hu
 *
 */
public class SessionKeyConstants {

	/**
	 * 公共用户登录的相关信息
	 */
	public static final String CU_CONTEXT="cu_context";
	
	public static final String CU_REGISTER_RANDOMCODE = "cu_register_randomcode";

	/**
	 * 返回的url
	 */
	public static final String CU_RETURN_URL = "return_url";
	
	public static final String CU_IBACK_URL = "iback_url";
	

	public static final String CU_RANDOMCODE = "cu_find_randomcode";
	
	/**
	 * 登录需要验证码
	 * (一般是指密码错误超过一次以后)
	 */
	public static final String CU_NEED_LOGIN_CHECKCODE = "cu_need_login_checkcode";
	
	/**
	 * 找回密码用户 id
	 */
	public static final String CU_USERID = "uc_find_userid";
	
	/**
	 * 是否需要强制性改密码，如果是强制性的，那么 CU_CONTEXT需要为空
	 */
	public static final String CU_UPDATE_REQUIRE_PASS = "cu_update_requite_pass";
	
	/**
	 * 判断是域登录还是uac登陆
	 */
	public static final String CU_DOMAIN_LOGIN = "cu_domain_login";
	/**
	 * 判断是否是ad用戶
	 */
	public static final String CU_AD_USER = "cu_ad_user";
}
