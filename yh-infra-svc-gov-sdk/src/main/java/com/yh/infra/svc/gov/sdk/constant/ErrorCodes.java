package com.yh.infra.svc.gov.sdk.constant;

public final class ErrorCodes {

    private ErrorCodes() {}

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    
    /** 平台治理 **/
    public static final int CONNECTION_FAIL = 10001;
    //////////////////http response error//////////////
    public static final int HTTP_RESPONSE_ERROR = 18000;
    
    //////////////////重试//////////////
    public static final int RETRY_SYSTEM_ERROR = 20000;
    public static final int RETRY_LOCK_FAIL = 20001;
    public static final int RETRY_STRATEGY_INVALID = 20010;
    public static final int RETRY_TASK_EXCEED_MAX_RETRY_LIMIT = 20011;
    public static final int RETRY_TASK_EXPIRED = 20012;
    public static final int RETRY_STRATEGY_NOT_FOUND = 20014;
    public static final int RETRY_EXECUTE_UNKNOWN_RESPONSE_CODE = 20020;
    public static final int RETRY_EXECUTE_UNKNOWN_EXCEPTION = 20030;
    public static final int RETRY_EXECUTE_BUSINESS_EXCEPTION = 20031;  //业务系统正常抛出的异常。
    public static final int RETRY_EXECUTE_REJECTED = 20032;  //hystrix限流
    public static final int RETRY_SEND_LOG_FAILED = 20040; 
    public static final int RETRY_REGISTER_FAILED = 20050;
    public static final int RETRY_REGISTER_UUID_DUPLICATED = 20051;
    public static final int RETRY_STRATEGY_DISABLED = 20052;
    
}
