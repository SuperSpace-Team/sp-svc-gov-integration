package com.yh.infra.svc.gov.sdk.constant;

/**
 * 所有通用服务对应常量信息
 * @author Justin.Hu
 *
 */
public class ServiceUrlConstants {
	/**
	 * 主键编码服务
	 */
	public static final String SAC_URL="sac/";
	
	/**
	 * UAC
	 */
	public static final String UAC_URL="uac/";

	/**
	 * TASK
	 */
	public static final String TASK_URL="task/";
	
	/**
	 * file
	 */
	public static final String FILE_URL="file/";
	
	/** 应用鉴权接口地址-治理平台基础服务(不直接在ITWork应用中心鉴权) **/
    public static final String APP_AUTH_BASE_URL = "/svc-gov/app";
	/** 版本检查 **/
    public static final String SVC_GOV_VERSION_QUERY = "/svc-gov/version/query";
	/** 应用密钥查询 **/
    public static final String SVC_GOV_SECRET_QUERY = "/svc-gov/secret/query";

//	/** retry**/
//    public static final String RETRY_BASE = "/svc-gov-retry/task/log/";
}
