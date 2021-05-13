package com.yh.infra.svc.gov.sdk.config;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import lombok.Data;

/**
 * @description: 应用注册配置类
 * @author: luchao
 * @date: Created in 2021/3/23 9:24 下午
 */
@Data
public class AppRegConfig {
    /**
     * 应用当前实例主机名
     */
    private String hostName;

    /**
     * 应用当前实例IP地址
     */
    private String ip;

    /**
     * 应用认证Key
     */
    private String appKey;

    /**
     * 应用认证密钥
     */
    private String appSecret;

    /**
     * 治理平台BFF服务URL
     */
    private String govPlatformUrl;

    /**
     * 统一API网关URL
     */
    private String unionGatewayUrl;

    /**
     * 治理平台SDK版本号
     */
    private String sdkVersion;

    /**
     * 是否启用VersionChecker线程
     */
    private Boolean enableVersionChecker = true;

    /**
     * 定时拉取间隔时长
     */
    private Integer versionPullInterval = SdkCommonConstant.VERSION_CHECK_PULL_INTERVAL_TIME;

    /**
     * 应用中心接入URL
     */
    private String uacUrl;

    /**
     * 密钥管理接入URL
     */
    private String secretUrl;
}
