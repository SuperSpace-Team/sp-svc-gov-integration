package com.sp.infra.svc.gov.sdk.auth.uac.app.init;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/1/21 11:27 AM
 */
@Component
@Data
public class AppAuthConfig {
    @Value("${svcGovSdk.appKey}")
    private String appKey;

    @Value("${svcGovSdk.appSecret}")
    private String appSecret;

    @Value("svcGovSdk.uniGateway")
    private String uniGateway;

}
