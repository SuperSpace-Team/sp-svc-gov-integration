package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import com.yh.common.utilities.EncryptUtil;
import com.yh.common.utilities.http.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.BackEntity;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    @SuppressWarnings("unchecked")
    public static <T> T readValue(String json, Class<?> parametrized) {

        return (T) JsonUtil.readValue(json, parametrized);
    }

    public static String postJsonData(String url, Map<String, ?> params) {
        return HttpJsonClient.postJsonData(url,params);
    }

    public static String hash(String plainText, String salt){
        return EncryptUtil.getInstance().hash(plainText, salt);
    }

    public static String base64Decode(String param){

        return EncryptUtil.getInstance().base64Decode(param);
    }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    public static boolean isBlank(final String string) {
        return isEmpty(string) || string.trim().length() == 0;
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }

    public static void main(String[] args) throws Exception {
        //	System.setProperty("https.protocols", "TLSv1");
        String url="https://account.yonghui.cn/person/login";
        //String url="https://www.baidu.com";
        String str=HttpJsonClient.getJsonData(url, null);

        System.out.println(str);


    }

    public static String encode(String sign){
        try {
            return URLEncoder.encode(sign,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendRequestString(String startUrl, Map<String, Object> params) {
        BeanRegistry sc = BeanRegistry.getInstance();
        UacService uacService = sc.getBean(UacService.class);
        Map<String, String> retMap = uacService.uacSendRequestWithToken(startUrl, params,
                SdkCommonConstant.PG_CONNECT_TIMEOUT);
        String tmp = retMap.get("error");
        if (tmp != null) {
            // 系统异常
            logger.warn("system error. {} ", retMap.get("error"));
            return null;
        }
        if (Integer.parseInt(retMap.get("status")) != 200) {
            logger.error(retMap.get("result"));
            return null;
        }
        if (retMap.get("result").contains("\"isSuccess\":false")) {
            logger.error(retMap.get("result"));
            return null;
        }
        return "success";
    }

    public static BackEntity sendRequestBackEntity(String startUrl, Map<String, Object> params) {
        BeanRegistry sc = BeanRegistry.getInstance();
        UacService uacService = sc.getBean(UacService.class);
        Map<String, String> retMap = uacService.uacSendRequestWithToken(startUrl, params,
                SdkCommonConstant.PG_CONNECT_TIMEOUT);
        String tmp = retMap.get("error");
        if (tmp != null) {
            // 系统异常
            logger.warn("system error. {} ", retMap.get("error"));
            return null;
        }
        if (Integer.parseInt(retMap.get("status")) != 200) {
            logger.error(retMap.get("result"));
            return null;
        }
        if (retMap.get("result").contains("\"isSuccess\":false")) {
            logger.error(retMap.get("result"));
            return null;
        }
        BackEntity backEntity = JsonUtil.readValue(retMap.get("result"), BackEntity.class);
        return backEntity;
    }



}
