package com.yh.infra.svc.gov.sdk.alm.constant;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:19 下午
 */
public class ParameterTypes {

	public static final String[] include = {
			"com.yh",
			"com.jumbo",
			"java.",
			"javax.",
	};
	
	public static final String[] exclude = {
			"javax.servlet.ServletRequestWrapper"
	};
	/*** 以下类型存在自引用**/
//	,"org.apache.catalina.core.ApplicationHttpRequest"
	
}
