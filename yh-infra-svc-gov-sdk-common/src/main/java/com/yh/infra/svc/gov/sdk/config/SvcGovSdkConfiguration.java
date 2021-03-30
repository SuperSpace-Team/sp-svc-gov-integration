package com.yh.infra.svc.gov.sdk.config;

import com.yh.infra.svc.gov.sdk.util.spring.SvcGovSdkSpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SvcGovSdkConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SvcGovSdkConfiguration.class);

    @Bean(name = "svcGovSdkSpringContextUtil")
    public SvcGovSdkSpringContextUtil svcGovSdkSpringContextUtil() {
        if (logger.isDebugEnabled()) {
            logger.debug("Begin to init SvcGovSdkSpringContextUtil.");
        }

        return new SvcGovSdkSpringContextUtil();
    }

//    @Bean(name = "almProperties")
//    public AlmProperties almProperties() {
//        if (logger.isDebugEnabled())
//            logger.debug("begin to init AlmProperties.");
//        return new AlmProperties();
//    }

//    @Bean(name = "pkManagerApi")
//    public PkManagerRemoteApiAdapter pkManagerApi(@Value("${svc-gov-sdk.gatewayUrl}") String apiGatewayUrl) {
//        if (logger.isDebugEnabled())
//            logger.debug("begin to init pkManagerApi.");
//        PkManagerRemoteApiAdapter mgr = new PkManagerRemoteApiAdapter();
//        mgr.setPkManagerUrl(apiGatewayUrl + "/sac");
//        return mgr;
//    }
//
//    @Bean(name = "codeManagerApi")
//    public CodeManagerRemoteApiAdapter codeManagerApi(@Value("${svc-gov-sdk.gatewayUrl}") String apiGatewayUrl) {
//        if (logger.isDebugEnabled())
//            logger.debug("begin to init codeManagerApi.");
//        CodeManagerRemoteApiAdapter mgr = new CodeManagerRemoteApiAdapter();
//        mgr.setCodeManagerUrl(apiGatewayUrl + "/sac");
//        return mgr;
//    }

//    @Bean(name = "rmqApiManager")
//    public RmqApiManager rmqApiManager() {
//        if (logger.isDebugEnabled())
//            logger.debug("begin to init rmqApiManager.");
//        RmqApiManager mgr = new RmqApiManager();
//        return mgr;
//    }

//    @Bean
//    public GsLogService gsLogService() {
//        return new GsLogServiceImpl();
//    }
//
//    @Bean
//    public FileS2ServiceImpl fileS2Service() {
//        return new FileS2ServiceImpl();
//    }
//
//    @Bean
//    public FolderS2ServiceImpl folderS2Service() {
//        return new FolderS2ServiceImpl();
//    }
//
//    @Bean
//    public CipherService cipherService() {
//        CipherService cipherService = new CipherServiceImpl();
//        return cipherService;
//    }

//    @Bean(name = "uaacProperties")
//    public UaacProperties uaacProperties() {
//        if (logger.isDebugEnabled())
//            logger.debug("begin to init UaacProperties.");
//        UaacProperties properties = new UaacProperties();
//        BeanRegistry.getInstance().register(properties);
//        return properties;
//    }
}
