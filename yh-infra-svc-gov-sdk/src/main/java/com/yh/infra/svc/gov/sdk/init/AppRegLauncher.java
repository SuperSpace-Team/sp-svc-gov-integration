package com.yh.infra.svc.gov.sdk.init;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.constant.ServiceUrlConstants;
import com.yh.infra.svc.gov.sdk.init.callback.ComponentInit;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.init.daemon.VersionChecker;
import com.yh.infra.svc.gov.sdk.init.service.ConfigService;
import com.yh.infra.svc.gov.sdk.init.service.SendReceiveService;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.net.impl.HttpClientProxyImpl;
import com.yh.infra.svc.gov.sdk.util.CollectionUtils;
import com.yh.infra.svc.gov.sdk.util.NetUtils;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 应用注册启动操作类
 * @author: luchao
 * @date: Created in 2021/3/23 9:42 下午
 */
public class AppRegLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppRegLauncher.class);
    /**
     * 应用认证Key
     */
    private String appKey;

    /**
     * 应用认证密钥
     */
    private String appSecret;

    /**
     * 统一API网关URL
     */
    private String unionGatewayUrl = SdkCommonConstant.DEFAULT_UNION_GATEWAY_URL;

    /**
     * 是否启用治理SDK
     * 默认不启用
     */
    private Boolean enabled = false;

    /**
     * 是否启用全链路监控
     * 默认开启
     */
    private Boolean monitorEnabled = true;

    AppRegConfig appRegConfig = new AppRegConfig();
    private HttpClientProxy httpClientProxy = null;
    private ExecutorService adminThreadPool = Executors.newFixedThreadPool(4);


    public AppRegLauncher(){ }

    public AppRegLauncher(String appKey){
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public AppRegConfig getAppRegConfig() {
        return appRegConfig;
    }

    public void setAppRegConfig(AppRegConfig appRegConfig) {
        this.appRegConfig = appRegConfig;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUnionGatewayUrl() {
        return unionGatewayUrl;
    }

    public void setUnionGatewayUrl(String unionGatewayUrl) {
        this.unionGatewayUrl = unionGatewayUrl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getMonitorEnabled() {
        return monitorEnabled;
    }

    public void setMonitorEnabled(Boolean monitorEnabled) {
        this.monitorEnabled = monitorEnabled;
    }

    public void init(){
        BeanRegistry br = BeanRegistry.getInstance();
        Boolean hasRegistered = br.getBean(SdkCommonConstant.SDK_INITIALIZED_FLAG);
        if((hasRegistered != null) && hasRegistered){
            logger.error("Service governance client has been initialized, but error exists!");
            return;
        }

        br.register(SdkCommonConstant.SDK_ENABLE_FLAG, enabled);
        if(!enabled){
            logger.error("Service governance SDK is disabled!");
            return;
        }

        //全链路监控开关
        br.register(SdkCommonConstant.SDK_ENABLE_FLAG_MONITOR, getMonitorEnabled());
        logger.info("Begin to initialize the client for service governance SDK...");

        //构建配置信息
        fillConfig();

        if(StringUtils.isEmpty(getSdkVersion())){
            logger.error("Cannot get sdk version, svc-gov-sdk does not initialize successfully!");
            return;
        }

        AppRegContext appRegContext = new AppRegContext(appRegConfig);

        //将配置信息的JSON注册
        if(logger.isDebugEnabled()){
            logger.debug("put config json string. {}", appRegContext.getConfigJson());
        }
        br.register(SdkCommonConstant.SDK_GLOBAL_CONFIG_JSON, appRegContext.getConfigJson());

        //初始化可用的网络连接器
        if(httpClientProxy == null){
            httpClientProxy = new HttpClientProxyImpl();
        }

        //创建并注册服务
        ConfigService configService = new ConfigService(appRegContext);
        UacService uacService = new UacService(appRegContext);
        SendReceiveService sendReceiveService = new SendReceiveService(appRegContext, uacService);
        VersionChecker versionChecker = new VersionChecker(appRegContext, sendReceiveService, configService);

        //注册全局上下文
        br.register(appRegContext);
        br.register(HttpClientProxy.class.getName(), httpClientProxy);
        br.register(uacService);
        br.register(sendReceiveService);
        br.register(configService);

        //守护线程也注册,由于是单线程,没有影响,后续可以取到使用
        br.register(versionChecker);

        //UAC登录
        uacService.getToken();

        //全链路监控注册
//       BeanRegistry.getInstance().add(ComponentInit.class, new AlmComponentInit());

        //LOG4J管理注册
//        BeanRegistry.getInstance().add(ComponentInit.class, new Log4jComponentInit());

        //Retry服务注册
//        BeanRegistry.getInstance().add(ComponentInit.class, new RetryLauncher());

        if(!initComponent(appRegContext)){
            logger.error("Service governance initialize failed!");
            return;
        }

        //启动守护线程
        if(appRegConfig.getEnableVersionChecker()){
            adminThreadPool.execute(versionChecker);
            //停1s,等线程启动(不启动也没关系)
            ThreadUtil.sleep(1000);
        }else {
            logger.info("Service governance version checker is disabled.");
        }

        br.register(SdkCommonConstant.SDK_INITIALIZED_FLAG, true);
        logger.info("Initialized service governance SDK successfully!");
    }

    /**
     * 附属模块初始化
     * @param appRegContext
     * @return
     */
    private boolean initComponent(AppRegContext appRegContext) {
        List<ComponentInit> componentInitList = BeanRegistry.getInstance().getBeanList(ComponentInit.class);
        if(CollectionUtils.isEmpty(componentInitList)){
            return true;
        }

        boolean failed = false;
        for (ComponentInit comp : componentInitList){
            logger.info("Begin to init component {}", comp.getClass().getName());

            if(!comp.init(appRegContext)){
                logger.warn("Component initialize failed.{}", comp.getClass().getName());
                failed = true;
                break;
            }
        }

        if(failed){
            for (ComponentInit comp : componentInitList){
                if(logger.isDebugEnabled()){
                    logger.debug("Begin to clean component {}" + comp.getClass().getName());
                    comp.clean(appRegContext);
                }
            }
        }

        return !failed;
    }

    private void fillConfig() {
        if(StringUtils.isBlank(appRegConfig.getHostName())){
            appRegConfig.setHostName(NetUtils.getLocalhostName());
        }

        if(StringUtils.isBlank(appRegConfig.getIp())){
            appRegConfig.setIp(NetUtils.getExternalIp());
        }

        if(StringUtils.isEmpty(appRegConfig.getAppKey())){
            appRegConfig.setAppKey(appKey);
        }else {
            appKey = appRegConfig.getAppKey();
        }

        if(StringUtils.isEmpty(appRegConfig.getAppSecret())){
            appRegConfig.setAppSecret(appSecret);
        }else {
            appSecret = appRegConfig.getAppSecret();
        }

        if(StringUtils.isEmpty(appRegConfig.getUnionGatewayUrl())){
            appRegConfig.setUnionGatewayUrl(appSecret);
        }else {
            unionGatewayUrl = appRegConfig.getUnionGatewayUrl();
        }

        if(StringUtils.isEmpty(appRegConfig.getGovPlatformUrl())){
            appRegConfig.setGovPlatformUrl(unionGatewayUrl + ServiceUrlConstants.PG_VERSION_QUERY);
        }

        if(StringUtils.isEmpty(appRegConfig.getUacUrl())){
            appRegConfig.setUacUrl(unionGatewayUrl + ServiceUrlConstants.UAC_BASE);
        }

        if(StringUtils.isEmpty(appRegConfig.getSecretUrl())){
            appRegConfig.setUacUrl(unionGatewayUrl + ServiceUrlConstants.PG_SECRET_QUERY);
        }

        appRegConfig.setSdkVersion(getSdkVersion());
    }

    /**
     * 从配置文件获取SDK版本
     * @return
     */
    private String getSdkVersion() {
        String sdkVersion = "";
        Properties properties = new Properties();

        try {
            InputStream is = this.getClass().getResourceAsStream(
                    "META-INF/maven/com.yh.infra.svc.gov/yh-infra-svc-gov-sdk/pom.properties");
            if(is != null){
                properties.load(is);
                sdkVersion = properties.getProperty("version");
                if(logger.isDebugEnabled()){
                    logger.debug("Get the sdk version:" + sdkVersion);
                }
            }else {
                logger.warn("Cannot get the sdk version.");
            }
        } catch (IOException e) {
            logger.warn("Error to get sdk version.", e);
        }

        return sdkVersion;
    }
}
