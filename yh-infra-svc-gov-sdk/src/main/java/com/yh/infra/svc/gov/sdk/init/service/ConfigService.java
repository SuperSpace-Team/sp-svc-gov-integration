package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.callback.CallbackService;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下发的配置信息的管理,包括验证和分发
 * 
 * @author luchao  2019-06-06
 *
 */
public class ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private AppRegContext context;

    public ConfigService(AppRegContext ctx) {
        this.context = ctx;
    }

    /**
     * 同步方式,确保本方法的调用是串行的。
     *
     * @param resp
     */
    public synchronized void updateVersion(VersionQueryResp resp) {
        // 系统启动后， 第一次从server拿到 响应， 无论其是否可用， 都要修改版本。 这样下一次就不是   初始版本了。
        if (context.getCurrentVersion() == SdkCommonConstant.PG_VERSION_INITIAL_VERSION) {
            context.setCurrentVersion(SdkCommonConstant.PG_VERSION_EMPTY_VERSION);
        }

        if (resp.getCode() == SdkCommonConstant.RESP_STATUS_VERSION_NOT_SUPPORTED) {
            logger.warn("Current sdk version {} is not supported, need upgrade.", context.getConfig().getSdkVersion());
            return ;
        }

        // 回调方法， 通知其他的组件
        List<CallbackService> cbList = BeanRegistry.getInstance().getBeanList(CallbackService.class);
        Map<String, Object> cbDataMap = new HashMap<String, Object>();
        String respStr = JsonUtil.writeValue(resp);

        // 如果任一个callback失败，则不更新版本。所以try-catch在最外层。
        try {
            for (CallbackService svr : cbList) {
                Map<String, Object> datamap = new HashMap<String, Object>();

                // 重建一个副本。避免第三方应用修改其中的值。
                datamap.put(SdkCommonConstant.CB_MAP_CONFIG_RESP, respStr);
                datamap.put(SdkCommonConstant.CB_MAP_GLOBAL_CONFIG, context.getConfigJson());

                if (!svr.validate(datamap)) {
                    logger.warn("Callback service {} failed. Do not update version. {}", svr.getCallbackName(), resp.getVersion());
                    return;
                }
                logger.info("Callback service {} passed for version {}. check next one.", svr.getCallbackName(), resp.getVersion());
                
                cbDataMap.put(svr.getClass().getName(), datamap);
            }
        } catch (Throwable t) {
            logger.warn("error occurs when callback.validate", t);
            return;
        }
        
        for (CallbackService svr : cbList) {
            Map<String, Object> dataMap = (Map<String, Object>) cbDataMap.get(svr.getClass().getName());
            if (dataMap != null) {
                // 任一callback 更新版本失败， 不影响其他的更新。 所以try在内层。
                try {
                    svr.process(dataMap);
                    logger.info("Callback service {} accepted version {}. process next one.", svr.getCallbackName(), resp.getVersion());
                } catch (Throwable t) {
                    logger.warn("error occurs when for callback service " + svr.getCallbackName(), t);
                }
            } else {
            	// 这个应该不可能发生
            	logger.warn("No data found for Callback service {}, version {}. ", svr.getCallbackName(), resp.getVersion());
            }
        }

        // 更新版本
        if (resp.getVersion() != null) {
        	context.setCurrentVersion(resp.getVersion());
        	logger.info("config updated. verison : {}", resp.getVersion());
        } else {
        	logger.info("version is null");
        }
    }
}
