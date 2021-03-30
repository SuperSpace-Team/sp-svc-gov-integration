package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

/**
 * web相关的constants
 * @author 李光辉
 * @date 2015年3月23日 上午11:46:08
 * @since
 */
public final class Constants {

    private Constants() {}
    
    
    /** 权限URL与类型的分隔符 */
    public static final String PRIVILEGE_URL_TYPE_SEP = "-";
    
    /**
	 * 正常
	 */
	public static final Integer LIFECYCLE_NORMAL=1;
	
	/**
	 * 禁用
	 */
	public static final Integer LIFECYCLE_DISABLE=0;
	
	/**
	 * 已删除
	 */
	public static final Integer LIFECYCLE_DELETED=2;
	
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	
	/**
	 * appkey的cookie名称
	 */
	public static final String COOKIE_NAME_APPKEY = "UACAPPKEY";
	
	/**
	 * 拦截器appkey
	 */
	public static final String ACL_SPLIT_USER_APPKEY = "APPCODE";
	
    /**
     * 服务器临时文件夹
     */
    public static final String tmpFilePath = "D://tmp//";
}
