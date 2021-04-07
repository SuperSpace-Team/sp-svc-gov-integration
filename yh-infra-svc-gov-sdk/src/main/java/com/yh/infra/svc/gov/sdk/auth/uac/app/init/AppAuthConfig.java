package com.yh.infra.svc.gov.sdk.auth.uac.app.init;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
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
    @Value("${yh.svcGovSdk.appKey}")
    private String appKey;

    @Value("${yh.svcGovSdk.appSecret}")
    private String appSecret;

    @Value("${yh.svcGovSdk.unionGatewayUrl}")
    private String unionGatewayUrl;

    public String getUnionGatewayUrl() {
        if(StringUtils.isBlank(unionGatewayUrl)){
            return SdkCommonConstant.DEFAULT_UNION_GATEWAY_URL;
        }

        return unionGatewayUrl;
    }
}
