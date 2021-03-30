/**
 * 
 */
package com.yh.svc.gov.test.springboot1.controller;

import java.lang.reflect.Method;

/**
 * 
 * @author luchao  2019-04-01
 *
 */
public class ClazzUtil {

    private ClazzUtil() {
        throw new IllegalStateException("clazzUtil class unable to instance");
    }

    public static Class<?> loadClass(String name, ClassLoader cl) {
        Class<?> clazz = null;
        try {
            clazz = cl.loadClass(name);
        } catch (ClassNotFoundException e) {
            // 不需要输出日志。 找不到是正常的。
        }

        return clazz;
    }

    public static Method getMethod(ClassLoader cl, String className, String methodName, Class<?>... paraTypes) {
        try {
            Class<?> aClass = loadClass(className, cl);
            if (aClass == null) {
                return null;
            }
            return aClass.getMethod(methodName, paraTypes);
        } catch (Exception e) {
            return null;
        }
    }
}
