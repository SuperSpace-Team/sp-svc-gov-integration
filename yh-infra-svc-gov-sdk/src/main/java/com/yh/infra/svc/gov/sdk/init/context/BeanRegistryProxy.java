package com.yh.infra.svc.gov.sdk.init.context;

import com.yh.infra.svc.gov.sdk.init.callback.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本代理仅供 sdk以外的 模块使用
 *
 * @author luchao  2019-04-03
 *
 */
public class BeanRegistryProxy {
	private static final Logger logger = LoggerFactory.getLogger(BeanRegistryProxy.class);

	private BeanRegistryProxy() {
		throw new IllegalStateException("BeanRegistryProxy class unable to instance");
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
    public static void register(Object obj) {
		if (logger.isDebugEnabled())
			logger.debug("Begin to register object {}", obj.getClass().getName());
    	BeanRegistry.getInstance().register(obj);
		setUpdateFlag(obj);
    }

    /**
     * 
     * @param key
     * @param obj
     */
    public static void register(String key, Object obj) {
		if (logger.isDebugEnabled())
			logger.debug("begin to register object {}, {}", key, obj);
    	BeanRegistry.getInstance().register(key, obj);
		setUpdateFlag(obj);
    }

    public static <T> boolean add(Class<T> clazz, Object obj) {
    	boolean ret = BeanRegistry.getInstance().add(clazz, obj);
    	if (ret) {
    		setUpdateFlag(obj);
    	}
    	return ret;
    }

    private static void setUpdateFlag(Object obj) {
    	// 设置标记，强制更新版本
    	AppRegContext ctx = BeanRegistry.getInstance().getBean(AppRegContext.class);
    	if (ctx != null && ifNeedUpdate(obj)) {
			if (logger.isDebugEnabled())
				logger.debug("Force to refresh the config after a new bean added. {}", obj.getClass().getName());
    		ctx.setNewCallback(true);
    	}
    }

    /**
     * 获取自定义IOC容器中的Bean
     * @param key
     * @return
     */
    public static Object getBean(String key) {
		if (logger.isDebugEnabled())
			logger.debug("Get bean {}", key);
        return BeanRegistry.getInstance().getBean(key);
    }

    
    private static boolean ifNeedUpdate(Object obj) {
    	return (obj instanceof RequestHandler);
    }
}
