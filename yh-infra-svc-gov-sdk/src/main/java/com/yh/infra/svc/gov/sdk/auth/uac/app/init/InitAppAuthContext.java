package com.yh.infra.svc.gov.sdk.auth.uac.app.init;

import com.yh.common.utilities.http.HttpJsonClient;
import com.yh.infra.svc.gov.sdk.auth.uac.app.BusinessException;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AppCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/1/21 11:08 AM
 */
public class InitAppAuthContext implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(InitAppAuthContext.class);

    @Autowired
    AppAuthConfig appAuthConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            initAppAuthValidation();
        }
    }

    /**
     * 集成永辉应用中心校验
     */
    private void initAppAuthValidation() {
        if(appAuthConfig == null || StringUtils.isBlank(appAuthConfig.getAppKey())
            || StringUtils.isBlank(appAuthConfig.getAppSecret())){
            return;
        }

        Map<String, Object> paramsMap = new HashMap();
        //将对象转换成JSON串
        String paramsJsonStr = JsonUtil.writeValue(paramsMap);
        String result = null;

        try {
            String appAuthUrl = appAuthConfig.getUnionGatewayUrl() + "/app/checkAuth";
            result = HttpJsonClient.postJsonDataByJson(appAuthUrl, paramsJsonStr, 1000);
        } catch (IOException e) {
            logger.error("Error post json . ", e);
            throw new BusinessException(10003, "HTTP 连接异常");
        }

//        ResponseDTO response = JsonUtil.readValue(result, AccountAuthReturnObj.class);
//        if(response.success() && response.getData()){
//            return JsonUtil.readValue(authObj, AppCommand.class);
//        }
//        throw new BusinessException(authObj.getErrorCode(), authObj.getErrorMsg());
    }
}
