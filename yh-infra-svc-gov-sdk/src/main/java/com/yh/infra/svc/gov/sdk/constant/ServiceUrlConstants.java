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
	
	/** UAC**/
    public static final String UAC_BASE = ""; 
	/** 版本检查 **/
    public static final String PG_VERSION_QUERY = "/pg/version/query";
	/** 应用密钥查询 **/
    public static final String PG_SECRET_QUERY = "/pg/secret/query";

	/** retry**/
    public static final String RETRY_BASE = "/gs-retry/task/log/"; 
}
