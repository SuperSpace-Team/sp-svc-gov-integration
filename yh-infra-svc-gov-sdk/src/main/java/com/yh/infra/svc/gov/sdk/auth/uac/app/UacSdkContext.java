package com.yh.infra.svc.gov.sdk.auth.uac.app;

/**
 * 系统参量的初始配置
 * @author Alex Lu
 *
 */
public class UacSdkContext {

	private static UacSdkConstants UacSdkConstants =null;

	public UacSdkContext() {
		throw new IllegalStateException("UacSdkContext class unable to instance");
	}

	/**
	 * 在使用系统context之前，需要设置常量类
	 * 常量类一般由客户端项目进行初始化
	 * @param sdkCon
	 */
	public static void init(UacSdkConstants sdkCon){
		UacSdkConstants =sdkCon;
	}
	/**
	 * 获取appkey
	 * @return
	 */
	public static String getAppKey(){
		if(UacSdkConstants !=null)
			return UacSdkConstants.getAppKey();
		else 
			return null;
	}
	
	/**
	 * 获取密钥
	 * @return
	 */
	public static String getSecret(){
		if(UacSdkConstants !=null)
			return UacSdkConstants.getAppSecret();
		else 
			return null;
	}
	
	/**
	 * 获取url域
	 * @return
	 */
	public static String getDomain(){
		if(UacSdkConstants !=null)
			return UacSdkConstants.getDomain();
		else 
			return null;
	}
}
