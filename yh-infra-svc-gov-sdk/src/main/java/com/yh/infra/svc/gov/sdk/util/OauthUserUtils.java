package com.yh.infra.svc.gov.sdk.util;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CommonUserInfo;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.SignProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OauthUserUtils {

    private static final Logger logger = LoggerFactory.getLogger(OauthUserUtils.class);

    public OauthUserUtils() {
    }

    public static CommonUserInfo getCommonUser(String sign, String param) {
        CommonUserInfo commonUserInfo = null;
        boolean signCheck = SignProcess.checkSign(sign, new Object[]{param}, UacSdkContext.getSecret());
        if (signCheck) {
            param = CommonUtil.base64Decode(param);
            HashMap<String, Object> params = (HashMap)CommonUtil.readValue(param, Map.class);
            if (params != null) {
                if (params.containsKey("user_info")) {
                    Object jsonOrder = params.get("user_info");
                    if (jsonOrder instanceof String) {
                        commonUserInfo = (CommonUserInfo)CommonUtil.readValue(jsonOrder.toString(), CommonUserInfo.class);
                    }
                } else {
                    logger.info("参数值中不包含用户信息");
                }
            } else {
                logger.info("参数值不能为空");
            }
        } else {
            logger.info("签名验证失败");
        }

        return commonUserInfo;
    }
}
