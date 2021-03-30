package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

public final class UserConstants {

	private UserConstants() {}
	
	/**
	 * 左菜单
	 */
	public static final String MENU_ITEMS = "menuItems";
	
	/**
	 * 组织列表
	 */
	public static final String ORG_LIST = "unitTree";
	
	/**
	 * 当前组织类型
	 */
	public static final String ORG_TYPE = "orgType";
	
	/**
	 * cookie名称的前缀，此cookie用来存放用户上次退出时使用的组织
	 */
	public static final String COOKIE_NAME_PREFIX="orgId_";
	
	
}
