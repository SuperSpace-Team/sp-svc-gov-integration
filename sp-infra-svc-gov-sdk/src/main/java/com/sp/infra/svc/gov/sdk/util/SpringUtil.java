package com.sp.infra.svc.gov.sdk.util;

import org.springframework.context.ApplicationContext;

/**
 * 需要再spring.xml中声明该bean
 * 才能使用springBean方式定时任务
 */

public class SpringUtil {
    
    public static ApplicationContext applicationContext;


    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) SpringUtil.getApplicationContext().getBean(name);
    }

    public static <T> T  getBean(Class<T> requiredType){
        return SpringUtil.getApplicationContext().getBean(requiredType);
    }

    public static <T> T  getBean(String name, Class<T> requiredType){
        return SpringUtil.getApplicationContext().getBean(name, requiredType);
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * _检测spring是否注入
     */
    public static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "applicaitonContext未注入,请在applicationContext.xml中定义SpringUtil");
        }
    }

    /**
     *_ 检测spring是否初始化
     * @return
     */
    public static boolean checkApplicationContextInitialization() {
        if (applicationContext == null) {
            return false;
        }
        return true;
    }


}
