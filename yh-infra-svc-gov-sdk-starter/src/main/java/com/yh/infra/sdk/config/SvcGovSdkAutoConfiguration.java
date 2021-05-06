package com.yh.infra.sdk.config;

//import com.yh.infra.svc.gov.sdk.config.DisabledConfiguration;
import com.yh.infra.svc.gov.sdk.config.AlmProperties;
import com.yh.infra.svc.gov.sdk.config.SvcGovSdkConfiguration;
import com.yh.infra.svc.gov.sdk.init.AppRegLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.yh.infra.svc.gov.sdk.init.SvcGovSdkInitializer;

@Configuration
@Import(value = { SvcGovSdkConfiguration.class })	//, DisabledConfiguration.class})
public class SvcGovSdkAutoConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(SvcGovSdkConfiguration.class);

	@Bean(name = "initSvcGovSdkByStarter")
	@DependsOn(value = {"svcGovSdkSpringContextUtil"})	//"retryInit",
	public AppRegLauncher initSvcGovSdkByStarter(@Autowired AlmProperties almProperties,
												 @Value("${svc-gov-sdk.versionChecker:true}") boolean enableVersionChecker,
												 @Value("${svc-gov-sdk.appKey}") String appKey,
												 @Value("${svc-gov-sdk.secret}") String secret,
												 @Value("${svc-gov-sdk.unionGatewayUrl}") String apiGatewayUrl,
												 @Value("${svc-gov-sdk.versionPullInterval:30}") int versionPullInterval) {

		if (logger.isDebugEnabled()) {
			logger.debug("begin to init initSvcGovSdkByStarter.");
		}

		AppRegLauncher client = SvcGovSdkInitializer.initSvcGovSdk(
				almProperties,
				enableVersionChecker, appKey, secret, apiGatewayUrl, versionPullInterval);
		if (client != null) {
			client.init();
		}

		return client;
	}
}

