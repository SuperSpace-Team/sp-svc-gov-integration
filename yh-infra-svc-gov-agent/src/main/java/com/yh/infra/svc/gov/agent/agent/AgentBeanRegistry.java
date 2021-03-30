/**
 *
 */
package com.yh.infra.svc.gov.agent.agent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luchao 2018-12-21
 *
 */
public class AgentBeanRegistry {
    private static Map<String, Object> registry = new ConcurrentHashMap<>();

    private AgentBeanRegistry() {}
    
    public static void register(Object obj) {
        registry.put(obj.getClass().getName(), obj);
    }

    public static void register(String key, Object obj) {
    	registry.put(key, obj);
    }

    public static Object getBean(String key) {
        return registry.get(key);
    }
    public static <T> T getBean(Class<T> clz) {
        return (T)registry.get(clz.getName());
    }
}
