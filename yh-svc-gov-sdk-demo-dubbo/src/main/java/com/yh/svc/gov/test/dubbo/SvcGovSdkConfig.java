package com.yh.svc.gov.test.dubbo;

import com.yh.infra.svc.gov.sdk.init.AppRegLauncher;
import com.yh.infra.svc.gov.sdk.init.SvcGovSdkInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SvcGovSdkConfig {
	private static final Logger logger = LoggerFactory.getLogger(SvcGovSdkConfig.class);
//	@Autowired
//	private AlmProperties almProperties;

	@Bean("initGsdkByStarter")
	@DependsOn(value = {"retryInit", "gsdkSpringContextUtil"})
	public AppRegLauncher initGsdkByStarter(
			@Value("${svc-gov-sdk.versionChecker:true}") boolean enableVersionChecker,
			@Value("${svc-gov-sdk.appKey}") String appKey,
			@Value("${svc-gov-sdk.secret}") String secret,
			@Value("${svc-gov-sdk.gatewayUrl}") String apiGatewayUrl,
			@Value("${svc-gov-sdk.verPollingInterval:30}") int verPollingInterval) {

		if (logger.isDebugEnabled())
			logger.debug("begin to init initGsdkByStarter.");

		AppRegLauncher client = SvcGovSdkInitializer.initSvcGovSdk(//almProperties,
				enableVersionChecker, appKey, secret, apiGatewayUrl, verPollingInterval);
		if (client != null) {
			client.init();
		}

		return client;
	}
}
