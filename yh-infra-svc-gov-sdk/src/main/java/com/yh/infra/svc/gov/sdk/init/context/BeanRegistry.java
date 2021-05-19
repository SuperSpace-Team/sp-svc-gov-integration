package com.yh.infra.svc.gov.sdk.init.context;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Bean注册操作类
 * @author: luchao
 * @date: Created in 2021/3/24 4:53 下午
 */
public class BeanRegistry {
    private static final Logger logger = LoggerFactory.getLogger(BeanRegistry.class);
    private static BeanRegistry instance = new BeanRegistry();
    private Map<String, Object> regBeansMap = new ConcurrentHashMap();

    public static BeanRegistry getInstance() {
        return instance;
    }

    public <T> Boolean add(Class<T> clazz, Object obj) {
        if (StringUtils.isBlank(clazz.getName())) {
            return false;
        }
        
        Vector serviceList = null;
        Object existBean = regBeansMap.get(clazz.getName());
        if (existBean == null) {
        	serviceList=new Vector();
        	serviceList.add(obj);
        	regBeansMap.put(clazz.getName(),serviceList);
      }
       if (!(existBean instanceof List)) {
           return false;
        }

        List svcList = (List) existBean;
        for (Object item : svcList) {
            if (item == obj) {
                return false;
            }

            svcList.add(item);
        }
        return true;
    }
    
    public <T> List<T> getBeanList(Class<T> clazz){
        if(clazz == null || StringUtils.isBlank(clazz.getName())){
            return null;
        }

        Object beanObj = regBeansMap.get(clazz.getName());
        if(beanObj == null){
            return null;
        }

        if(beanObj instanceof List){
            return (List<T>)beanObj;
        }

        logger.error("Registered bean is not in list.{}", clazz.getName());
        return null;
    }

    public void register(Object obj){
        regBeansMap.put(obj.getClass().getName(), obj);
    }

    public void register(String key, Object obj){
        regBeansMap.put(key, obj);
    }

    public void remove(Object obj){
        regBeansMap.remove(obj);
    }

    public <T> void unRegister(Class<T> clazz){
        regBeansMap.remove(clazz.getName());
    }

    public <T> T getBean(Class<T> clazz){
        Object obj = regBeansMap.get(clazz.getName());
        if(obj == null){
            return null;
        }

        return (T)obj;
    }

    public <T> T getBean(String key){
        Object obj = regBeansMap.get(key);
        if(obj == null){
            return null;
        }

        return (T)obj;
    }
}
