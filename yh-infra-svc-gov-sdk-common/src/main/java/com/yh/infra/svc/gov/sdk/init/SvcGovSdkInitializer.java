/**
 *
 */
package com.yh.infra.svc.gov.sdk.init;

import com.yh.infra.svc.gov.sdk.alm.config.AlmConfig;
import com.yh.infra.svc.gov.sdk.config.AlmProperties;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yh.infra.svc.gov.sdk.config.AppRegConfig;

/**
 * @author luchao  2020-07-02
 *
 */
public class SvcGovSdkInitializer {
	private static final Logger logger = LoggerFactory.getLogger(SvcGovSdkInitializer.class);


	public static AppRegLauncher initSvcGovSdk(AlmProperties almProperties,
											   boolean enableVersionChecker,
											   String appKey,
											   String secret,
											   String unionGatewayUrl,
											   int versionPullInterval) {

		logger.info("begin to prepare PgClientLauncher. {}, {}", appKey, unionGatewayUrl);
		logger.info("ALM config: {}", almProperties);

		//若已经初始化过,则返回null。
		if (BeanRegistry.getInstance().getBean(SdkCommonConstant.SDK_INITIALIZED_FLAG) != null) {
			logger.error("Service governance SDK has been initialized!");
			return null;
		}

		AlmConfig almconfig = new AlmConfig();
		BeanUtils.copyProperties(almProperties, almconfig);
		BeanRegistry.getInstance().register(almconfig);
		
		AppRegConfig config = new AppRegConfig();
		config.setEnableVersionChecker(enableVersionChecker);
		config.setAppKey(appKey);
		config.setAppSecret(secret);
		config.setUnionGatewayUrl(unionGatewayUrl);
		config.setVersionPullInterval(versionPullInterval);

		AppRegLauncher clientLauncher = new AppRegLauncher();
		clientLauncher.setAppRegConfig(config);
		clientLauncher.setAppKey(appKey);
		clientLauncher.setAppSecret(secret);
		clientLauncher.setUnionGatewayUrl(unionGatewayUrl);
		clientLauncher.setEnabled(true);
		clientLauncher.setMonitorEnabled(almProperties.isEnabled());

		return clientLauncher;
	}
}
