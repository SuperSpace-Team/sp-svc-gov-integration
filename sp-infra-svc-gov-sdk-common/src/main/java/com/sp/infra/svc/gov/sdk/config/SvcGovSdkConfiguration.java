package com.sp.infra.svc.gov.sdk.config;

import com.sp.infra.svc.gov.sdk.util.spring.SvcGovSdkSpringContextUtil;
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

    @Bean(name = "almProperties")
    public AlmProperties almProperties() {
        if (logger.isDebugEnabled())
            logger.debug("begin to init AlmProperties.");
        return new AlmProperties();
    }

//    @Bean
//    public GovLogService govLogService() {
//        return new GovLogServiceImpl();
//    }
}
