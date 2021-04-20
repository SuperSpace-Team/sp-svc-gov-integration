package com.yh.infra.svc.gov.sdk.constant;

/**
 * @description: SDK公共常量定义
 * @author: luchao
 * @date: Created in 2021/3/23 9:40 下午
 */
public final class SdkCommonConstant {

    private SdkCommonConstant() {}

    /**
     * 版本检查间隔时长(默认30s)
     */
    public static final Integer VERSION_CHECK_PULL_INTERVAL_TIME = 30;

    /**
     * 默认系统环境变量
     */
    public static final String SDK_APP_ENV_VAR = "env";

    /**
     * 默认统一API网关URL
     */
    public static final String DEFAULT_UNION_GATEWAY_URL = "http://gxfw-yh02-"
            + System.getProperty(SDK_APP_ENV_VAR) + ".yh-union-gateway.devgw.yonghui.cn";

    /**
     * 默认治理平台BFF服务URL
     */
    public static final String DEFAULT_SERVICE_GOVERNANCE_PLATFORM_URL = "https://svc-gov-platform-"
            + System.getProperty(SDK_APP_ENV_VAR) + ".yonghui.cn";

    /**
     * 通用常量
     */
    public static final String HTTP_STATUS_OK = "200";
    public static final String HTTP_STATUS_SYSTEM_ERROR = "500";
    public static final int HTTP_STATUS_OK_INT = 200;
    public static final String SEPARATOR_COMMA = ",";
    public static final String SEPARATOR_SEMICOLON = ";";

    /**
     * 平台治理
     */
    public static final String SDK_ENABLE_FLAG = "SDK_ENABLE_FLAG";
    public static final String SDK_ENABLE_FLAG_MONITOR = "SDK_ENABLE_FLAG_MONITOR";
    public static final String SDK_INITIALIZED_FLAG = "SDK_INITIALIZED_FLAG";
    public static final String SDK_GLOBAL_CONFIG_JSON = "SDK_GLOBAL_CONFIG_JSON";


    // 1.1 版： 最初的版本
    // 1.2 版： node中增加了type字段。 用于自定义api日志接口。node中某些字段在type=2时允许为空。
    public static final String SCHEMA_VERSION = "1.2";



    /** 用于callback中的map的key  **/
    public static final String CB_MAP_CONFIG_RESP = "CB_MAP_CONFIG_RESP";
    public static final String CB_MAP_CONFIG = "CB_MAP_CONFIG";
    public static final String CB_MAP_MONITOR_RULES_DATA = "CB_MAP_MONITOR_RULES_DATA";
    public static final String CB_MAP_GLOBAL_CONFIG = "CB_MAP_GLOBAL_CONFIG";

    /** 用于agent与sdk交互 **/
    public static final String SDK_CLASS_LOADER = "SDK_CLASS_LOADER";  //保存sdk的classloader
    public static final String ALM_CLASS_LOADER = "ALM_CLASS_LOADER"; //保存alm的classloader



    /** 用于请求与响应  **/
    public static final int RESP_STATUS_NO_UPDATE = 1;
    public static final int RESP_STATUS_UPDATE = 2;
    public static final int RESP_STATUS_UPDATE_DISABLED = 3;
    public static final int RESP_STATUS_LIFECYCLE_DISABLED = 4;
    public static final int RESP_STATUS_VERSION_NOT_SUPPORTED = 5;
	public static final String RESP_KEY_ERROR = "error";
	public static final String RESP_KEY_RESULT = "result";
	public static final String RESP_KEY_STATUS = "status";

    /** 通用配置 **/
    public static final int PG_CONNECT_TIMEOUT = 5; // 默认网络连接超时
    public static final String PG_LOCAL_FILE_LOGGER_NAME = "PG-Monitor-Log"; // 写到本地日志的时候，logger的name。

    /** 用于版本 **/
    public static final int PG_VERSION_POLLING_INTERVAL = 30; //轮询 30s 间隔。
    public static final int PG_VERSION_INITIAL_VERSION = -100; //系统启动后， 第一个  请求用这个版本。后续的用
    public static final int PG_VERSION_EMPTY_VERSION = -1; //非第一个请求，但是也没有本地 配置的情况下， 用这个版本。

    /** 用于监控/转换日志 **/
    public static final int PG_LOG_SEND_POLLING_INTERVAL = 1; //监控日志 发送线程 轮询 1s 间隔。
    public static final int PG_LOG_CACHE_SIZE_DEFAULT = 10000; //数据缓存容量
    public static final int PG_LOG_BATCH_MAX_SIZE = 200; // 从队列中取出log进行发送，每次取log的最大数目
    public static final int PG_LOG_SENDER_THREAD_MAX_SIZE = 5; // 并发发送日志的最大线程数
    public static final int PG_LOG_CACHE_SIZE_MINIMUM = PG_LOG_BATCH_MAX_SIZE; //数据缓存容量 下限

    /** 用于探索任务日志 **/
    public static final int PG_LOG_DISCOVERY_CACHE_SIZE_DEFAULT = 1000; //数据缓存容量
    public static final int PG_LOG_DISCOVERY_BATCH_MAX_SIZE = 100; // 从队列中取出log进行发送，每次取log的最大数目
    public static final int PG_LOG_DISCOVERY_CACHE_SIZE_MINIMUM = PG_LOG_DISCOVERY_BATCH_MAX_SIZE; //数据缓存容量 下限

    /** 用于  LOG4J/LOG4J2 日志配置更新 */
    public static final String PG_LOGTYPE_LOG4J = "LOG4J";
    public static final String PG_LOGTYPE_LOG4J2 = "LOG4J2";

    /** 用于熔断 **/
    public static final int PG_FUSE_COOL_TIMEOUT = 30; // 熔断后的冷却时间，秒。
    public static final int PG_FUSE_THRESHOLD = 60; // 错误阈值, 默认60%。
    public static final int PG_FUSE_CHECK_TIME_WINDOW = 30; // 熔断错误检测时间窗口, 秒。
    public static final int PG_FUSE_CHECK_TIME_WINDOW_MINIMUM = 10; // 熔断错误检测时间窗口下限, 秒。
    public static final int PG_FUSE_MSG_MAX_SIZE = 10000; // 存放发送消息状态的队列的最大容量。熔断处理使用


    /** 用于UAC **/
    public static final int PG_UAC_REFRESH_IN_ADVANCE = 30 * 60 * 1000; //提前30分钟进行refresh
    public static final String PG_CANNOT_GET_TOKEN = "5001"; //出于某种原因，无法取得token，该错误不是uac返回，是本地的错误
    public static final String PG_UAC_FORCE_TO_LOGIN = "60001"; //强制重新登录的错误码

    /** =========================== 定时任务 ============================== **/
    ///////////////////// 租户触发类型 ///////////////////
    /** 1 dubbo的方式 **/
    public static final int APP_TYPE_DUBBO = 1;
    /** 2 http的方式  **/
    public static final int APP_TYPE_HTTP = 2;

    /////////////////// 定时任务客户端参数配置 ///////////////////////
    public static final String TASK_ENABLE_FLAG = "TASK_ENABLE_FLAG";
    public static final String TASK_APPLICATION_CONTEXT = "TASK_applicationContext";


    /////////////////// 定时任务执行参数 ///////////////////////
    /** 当前时间key **/
    public static final String JSON_SYSDATE = "sysdate";
    /** 当前任务计划ID key **/
    public static final String JSON_TASK_PLAN_ID = "taskPlanId";
    /** 当前集群 key **/
    public static final String JSON_REGISTER_GROUP = "registerGroup";

    /** 当前时间 **/
    public static final String PARAM_SYSDATE = "%sysdate%";
    /** 当前任务计划ID **/
    public static final String PARAM_TASK_PLAN_ID = "%taskPlanId%";
    /** 当前集群 **/
    public static final String PARAM_REGISTER_GROUP = "%registerGroup%";



    /////////////// 任务计划推送 ////////////////
    public static final String KEY_TASK_PLAN_UPDATE_CONFIG = "TASK_PLAN_UPDATE_CONFIG";
    /** 同步 **/
    public static final Integer TASK_PLAN_SYNC = 1;
    /** 异步 **/
    public static final Integer TASK_PLAN_ASYNC = 2;

    /////////////// 任务日志推送 ////////////////
    public static final String KEY_TASK_LOG_UPDATE_CONFIG = "TASK_LOG_UPDATE_CONFIG";
    /** 任务日志推送 实时 **/
    public static final Integer TASK_LOG_REALTIME = 1;
    /** 任务日志推送 非实时**/
    public static final Integer TASK_LOG_NOT_REALTIME = 2;
    /** ip地址 **/
    public static final String TASK_DAEMON_IP = "TASK_DAEMON_IP";

    /**
     * 正常
     */
    public static final Integer LIFECYCLE_NORMAL = 1;

    /**
     * 禁用
     */
    public static final Integer LIFECYCLE_DISABLE = 0;

    /**
     *html格式
     */
    public static final Integer EMAIL_HTML = 1;
    /**
     *text格式
     */
    public static final Integer EMAIL_TEXT = 0;

    /**
     *暂无（默认格式）
     */
    public static final Integer EMAIL_NO = 2;


    /** =========================== 重试服务 ============================== **/
    //// 通用
    public static final String RETRY_THREAD_GROUP_KEY = "Retry-GroupKey-Shared";
    public static final int RETRY_HYSTRIX_DEFAULT_CORE_SIZE = 10;
    public static final int RETRY_HYSTRIX_DEFAULT_MAX_CORE_SIZE = 10;
    public static final String RETRY_URL_UPLOAD_LOG = "receive";
    public static final String RETRY_JSON_EMPTY_STRING = "{}";
    public static final String RETRY_COMPONENT_INITIALIZED = "RETRY_COMPONENT_INITIALIZED";


    // 默认策略
    public static final String RETRY_STRATEGY_DEFAULT_EXPIRE_TIME = "6h";
    public static final String RETRY_STRATEGY_DEFAULT_NAME = "DEFAULT_STRATEGY";

    ////配置
    public static final int RETRY_CFG_MIN_EXEC_TIMEOUT = 10;  //最小执行超时 10s
    public static final int RETRY_CFG_THREAD_CORE_NUMBER = 10;
    public static final int RETRY_LOCK_TIMEOUT = 10;  //分布式锁的超时 10s， 10s内应该被reset成 FREE才对。

    ////类型
    public static final int RETRY_STRATEGY_TYPE_FIX = 1;  //固定重试间隔
    public static final int RETRY_STRATEGY_TYPE_FIBONACCI = 2;  //退避重试间隔
    public static final int RETRY_STRATEGY_TYPE_CUSTOMIZE = 3;  //自定义重试时间点
    ////  hystrix使用 线程隔离类型
    public static final int RETRY_ISOLATE_TYPE_THREAD = 1;
    public static final int RETRY_ISOLATE_TYPE_SEMAPHORE = 2;
    public static final String RETRY_LOG_SENDER_HYSTRIX_POOL_KEY = "RETRY_LOG_SENDER_HYSTRIX_POOL";

    // AOP表达式
    public static final String RETRY_AOP_CFG_PARAMETER_PREFIX = "P";
    public static final String RETRY_AOP_CFG_EXP_RESULT = "RESULT";
    public static final String RETRY_AOP_CFG_EXP_EXCEPT = "EXCEPT";

    // 重试执行结果。
    public static final int RETRY_RESULT_UNKNOWN = 0;
    public static final int RETRY_RESULT_SUCCESS = 1;
    public static final int RETRY_RESULT_FAIL = 2;
    public static final int RETRY_RESULT_CANCEL = 3;


    /** =========================== ALM 全链路监控 ============================== **/
    public static final String ALM_INITIALIZED_FLAG = "ALM_INITIALIZED_FLAG";
    public static final String ALM_CALLBACK_CLASSPATH_NAME = "com.yh.infra.svc.gov.agent.alm.callback.impl.AlmCallbackServiceImpl";
    public static final String AGENT_REGISTRY_CLASSPATH_NAME = "com.yh.infra.svc.gov.agent.agent.AgentBeanRegistry";
    public static final String ALM_REGISTRY_CLASSPATH_NAME = "com.yh.infra.svc.gov.agent.alm.context.BeanRegistryProxy";
    public static final String ALM_INITIALIZER_CLASSPATH_NAME = "com.yh.infra.svc.gov.agent.alm.init.AlmInitializer";
    public static final String AGENT_ALM_MONITOR_SERVICE_NAME = "com.yh.infra.svc.gov.agent.alm.service.MonitorService";
    public static final String AGENT_INSTALL_PROCESSOR_CLASSPATH_NAME = "com.yh.infra.svc.gov.agent.agent.AgentInstallProcessor";
    public static final String VALIDATE = "validate";
    public static final String PROCESS = "process";
	public static final String INIT = "init";
	public static final String GET_BEAN = "getBean";
	public static final String REGISTER = "register";
	public static final String IGNORE_ALM = "IGNORE_ALM";
    public static final String ALM_EMBEDDED_TYPE = "ALM_EMBEDDED_TYPE";




    /********************* 全链路监控**********************************************/
    /**
     * 通用常量
     **/
    public static final int MAX_RECURSION_LEVEL = 10; //递归最大深度。 防止循环递归。
    public static final int LOG_TYPE_MONITOR = 1;
    /**
     * 用于spring el 表达式
     **/
    public static final String PARA_PREFIX = "P";
    /**
     * 通用配置
     **/
    public static final String PG_COMMON_CFG_SYS_LOG_ARCH = "SYS_LOG_ARCH"; // 日志归档服务地址的key。用于全链路监控日志
    public static final String PG_COMMON_CFG_SYS_LOG_ARCH_DISCOVERY = "SYS_LOG_ARCH_DISCOVERY"; // 日志归档服务地址的key。用于探索日志
    public static final int WORK_MODE_SIMPLE = 1; //简单模式 
    public static final int WORK_MODE_NORMAL = 2; //正常模式
    public static final int BLACK_LIST_QUEUE_SIZE = 100; //黑名单 queue的 容量。 100个。

    public static final String ALM_TYPE_AGENT = "agent";
    public static final String ALM_TYPE_GSDK = "svc-gov-sdk";
    /**
     * 用于监控/转换日志
     **/
    public static final String PG_LOG_MAP_KEY_BIZKEY = "PG_LOG_MAP_KEY_BIZKEY"; // map的key，存放 业务关键字 数据
    public static final String PG_LOG_MAP_KEY_RESULT = "PG_LOG_MAP_KEY_RESULT"; // map的key，存放  out/error 数据
    public static final int PG_NODE_TYPE_MONITOR = 0;
    public static final int PG_NODE_TYPE_TRANSFORM = 1;
    public static final String PG_LOG_RULE_IN = "IN"; // 
    public static final String PG_LOG_RULE_OUT = "OUT"; // 
    public static final String PG_LOG_RULE_EXCEPTION = "EXCEPTION"; // 
    public static final String PG_LOG_RULE_CLASS = "CLZ";

    public static final int ALM_NODE_TYPE_NORMAL = 1;  //普通的默认的监控节点， 是字节码增强的那种。
    public static final int ALM_NODE_TYPE_CUSTOMIZE = 2;  //自定义的监控节点， 使用api接口发日志的那种。


    /****************************用于sky walking相关  *********************************/
    public static final String SKYWALKING_CONTEXT_CLASSPATH_NAME = "org.apache.skywalking.apm.agent.core.context.ContextManager";
    public static final String SKYWALKING_CONTEXT_METHOD_NAME = "getContextMap";
    public static final String SKYWALKING_MAP_TRACEID_KEY = "traceId";
    public static final String SKYWALKING_MAP_SPANID_KEY = "spanId";
    public static final String SKYWALKING_MAP_SEGMENTID_KEY = "segmentId";



    /********************* 主键服务**********************************************/
    public static final int PRIMARY_KEY_CONNECT_TIMEOUT = 15;//主键服务链接超时时间

    /********************* uaac **********************************************/
    public static final String UAAC_HEADER_TOKEN = "token";
    public static final String UAAC_HEADER_USER_ID = "user-id";
    public static final String UAAC_HEADER_SECURITY_DOMAIN = "security-domain";
    public static final String UAAC_HEADER_VERIFY = "verify-uaac-token";
    public static final int UAAC_AUTH_ORDER = 1;
    public static final int UAAC_SD_MAP_SIZE = 2000;
    public static final int UAAC_ACL_MAP_SIZE = 2000;
    public static final String UAAC_TOKEN_ERROR = "201";
    public static final String UAAC_AUTH_ERROR = "40300";
    public static final String UAAC_AUTH_SUCCESS = "20000";
    public static final String UAAC_DEFAULT_SPLIT = "-!-";


}
