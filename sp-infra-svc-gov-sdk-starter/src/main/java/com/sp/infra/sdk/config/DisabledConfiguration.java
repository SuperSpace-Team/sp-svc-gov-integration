package com.sp.infra.sdk.config;

import com.sp.infra.svc.gov.sdk.config.SvcGovSdkConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisabledConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(SvcGovSdkConfiguration.class);

//	@Bean(name = "retryInit")
//	@ConditionalOnProperty(prefix = "svc-gov-sdk.retry", name = "enabled", havingValue = "false", matchIfMissing = true)
//	public String retryInit() {
//		if (logger.isDebugEnabled())
//			logger.debug("begin to init mock retryInit.");
//		return "";
//	}
}
