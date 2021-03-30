package com.yh.infra.svc.gov.sdk.auth.uac.account;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.auth.uac.app.BusinessException;
import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * saas租户相关服务
 * @author hsh10697
 */
public class SaasTenantService {
    private static final Logger logger = LoggerFactory.getLogger(SaasTenantService.class);

    /**
     * 添加saas租户
     */
    public BaseResponseEntity<String> add(String saasTenantCode, String saasTenantName) {
        SaasTenantInfo saasTenantInfo = new SaasTenantInfo();
        saasTenantInfo.setSaasTenantCode(saasTenantCode);
        saasTenantInfo.setSaasTenantName(saasTenantName);

        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();
        String url = config.getUnionGatewayUrl() + "/appmember/saas/tenantInfo/add";
        Map<String, String> map = uacService.sendRequestWithToken(url, JsonUtil.toJson(saasTenantInfo), 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<String> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            return new BaseResponseEntity<>(false, "5000", "Result为null", "");
        }
        return data;
    }

    /**
     * 删除saas租户
     */
    public BaseResponseEntity<String> delete(String saasTenantCode) {
        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();

        String url = config.getUnionGatewayUrl() + "/appmember/saas/tenantInfo/delete?saasTenantCode=" + saasTenantCode;
        Map<String, String> map = uacService.sendRequestWithToken(url, "", 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<String> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            throw new BusinessException("Result为null");
        }
        return data;
    }

    /**
     * 获取saas租户列表
     */
    public BaseResponseEntity<List<SaasTenantInfo>> getTenantList() {
        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();

        String url = config.getUnionGatewayUrl() + "/appmember/saas/tenantInfo/list";
        Map<String, String> map = uacService.sendRequestWithToken(url, "", 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<List<SaasTenantInfo>> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            throw new BusinessException("Result为null");
        }
        return data;
    }

    /**
     * saas租户授权
     */
    public BaseResponseEntity<String> authorize(String saasTenantCode) {
        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();

        String url = config.getUnionGatewayUrl()
                + "/appmember/saas/tenantToken/authorize?saasTenantCode=" + saasTenantCode;
        Map<String, String> map = uacService.sendRequestWithToken(url, "", 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<String> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            throw new BusinessException("Result为null");
        }
        return data;
    }

    /**
     * 获取saas租户token列表
     */
    public BaseResponseEntity<List<SaasTenantToken>> getTenantTokenList() {
        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();

        String url = config.getUnionGatewayUrl()
                + "/appmember/saas/tenantToken/list";
        Map<String, String> map = uacService.sendRequestWithToken(url, "", 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<List<SaasTenantToken>> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            throw new BusinessException("Result为null");
        }
        return data;
    }

    /**
     * 同步租户tenantToken到缓存
     */
    public BaseResponseEntity<Object> syncTenantTokenList2Cache() {
        AppRegConfig config = getAppRegConfig();
        UacService uacService = getUacService();

        String url = config.getUnionGatewayUrl() + "/appmember/saas/tenantToken/syncCache";
        Map<String, String> map = uacService.sendRequestWithToken(url, "", 10);
        String result = map.get("result");
        logger.info("result: {}", result);
        BaseResponseEntity<Object> data = JsonUtil.readValueSafe(result, BaseResponseEntity.class);
        if (data == null) {
            throw new BusinessException("Result为null");
        }
        return data;
    }

    private AppRegConfig getAppRegConfig() {
        BeanRegistry br = BeanRegistry.getInstance();
        AppRegContext context = br.getBean(AppRegContext.class);
        return context.getConfig();
    }

    private UacService getUacService() {
        BeanRegistry br = BeanRegistry.getInstance();
        return br.getBean(UacService.class);
    }
}
