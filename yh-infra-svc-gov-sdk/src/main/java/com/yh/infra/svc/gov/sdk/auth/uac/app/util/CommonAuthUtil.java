package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import com.yh.common.utilities.http.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.auth.uac.app.BusinessException;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.util.AesAuthUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public class CommonAuthUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonAuthUtil.class);

    /**
     * HTTP 连接通用方法（返回对象）
     * @author wenjin.gao
     * @param params
     * @param url
     * @return
     */
    public  static <T>  T authOpCommon(Map<String, Object> params, String url, Class<T> cls){
        params.put("appKey", UacSdkContext.getAppKey());
        params.put("secret", UacSdkContext.getSecret());
        //将对象转换成 json 串
        String paramsStr = JsonUtil.writeValue(params);
        //加密
        String encryptStr = AesAuthUtil.encrypt(paramsStr);
        //签名
        String sign = SignProcess.makeSign(new Object[]{encryptStr});
        logger.info("========= {}", sign);
        url += "?sign=" + CommonUtil.encode(sign);
        String result = null;
        try {
            result = HttpJsonClient.postJsonDataByJson(url, encryptStr, 1000);
        } catch (IOException e) {
            logger.error("error post json . ", e);
            throw new BusinessException(10003, "HTTP 连接异常");
        }

        AccountAuthReturnObj authObj = JsonUtil.readValue(result, AccountAuthReturnObj.class);

        if(authObj.isResultFlag()){
            return JsonUtil.readValue(authObj.getData(), cls);
        }
        throw new BusinessException(authObj.getErrorCode(), authObj.getErrorMsg());
    }

    /**
     * HTTP 连接通用方法（返回List）
     * @author wenjin.gao
     * @param params
     * @param url
     * @param cls
     * @return
     */
    public static <T> List<T> authOpCommonList(Map<String, Object> params, String url, Class<T> cls){
        params.put("appKey", UacSdkContext.getAppKey());
        params.put("secret", UacSdkContext.getSecret());
        //将对象转换成 json 串
        String paramsStr = JsonUtil.writeValue(params);
        //加密
        String encryptStr = AesAuthUtil.encrypt(paramsStr);
        //签名
        String sign = SignProcess.makeSign(new Object[]{encryptStr});
        url += "?sign=" + CommonUtil.encode(sign);
        String result = null;
        try {
            result = HttpJsonClient.postJsonDataByJson(url, encryptStr, 1000);
        } catch (IOException e) {
            logger.error("error post json . ", e);
            throw new BusinessException(10003, "HTTP 连接异常");
        }

        AccountAuthReturnObj authObj = JsonUtil.readValue(result, AccountAuthReturnObj.class);

        if(authObj.isResultFlag()){
            return JsonUtil.readList(authObj.getData(), cls);
        }
        throw new BusinessException(authObj.getErrorCode(), authObj.getErrorMsg());
    }

}
