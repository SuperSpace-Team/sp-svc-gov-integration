/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 
 * @author luchao  2019-04-01
 *
 */
public class ClazzUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClazzUtil.class);

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
	public static String getMethodFullName(Class<?> clz, MethodWrapper method) {
		// 有可能传入的class, method是NULL
		if (clz == null || method == null)
			return "";
		StringBuilder clzEntry = new StringBuilder();  // 本执行方法 的 全称/签名
		clzEntry.append(clz.getName()).append(".").append(method.getName()).append("(").append(ClazzUtil.getParamString(method)).append(")");
		return clzEntry.toString();
	}
	/**
	 * 构建方法参数的  字符串。
	 * 
	 * @param method
	 * @return
	 */
	public static String getParamString(MethodWrapper method) {
		StringBuilder st = new StringBuilder();
		Class<?> ptype[] = method.getParameterTypes();
		if ((ptype != null) && (ptype.length != 0)) {
			for (int i = 0; i < ptype.length; i++) {
				st.append(ptype[i].getSimpleName());
				if (i < (ptype.length - 1)) {
					st.append(SdkCommonConstant.SEPARATOR_COMMA);
				}
			}
		}
		return st.toString();
	}

    public static Method getMethod(ClassLoader cl, String className, String methodName, Class<?>... paraTypes) {
        try {
            Class<?> aClass = loadClass(className, cl);
            if (aClass == null) {
                return null;
            }
            return aClass.getDeclaredMethod(methodName, paraTypes);
        } catch (Exception e) {
       		logger.warn("get method error. ", e);
            return null;
        }
    }
    public static Method getMethod(Class<?> aClass, String methodName, Class<?>... paraTypes) {
        try {
            return aClass.getDeclaredMethod(methodName, paraTypes);
        } catch (Exception e) {
       		logger.warn("get method error. ", e);
            return null;
        }
    }

    /**
     * 仅用于对  静态方法调用 
     * 
     * @param ret
     * @param method
     * @param args
     * @return
     */
    public static boolean invoke(BaseResponseEntity ret, Method method, Object... args) {
        return invoke(ret, null, method, args);
    }
	
    /**
     * 
     * @param ret
     * @param method
     * @param args
     * @return
     */
    public static boolean invoke(BaseResponseEntity ret, Object target, Method method, Object... args) {
    	ret.setIsSuccess(false);
    	Object retObj = null;
        try {
            retObj = method.invoke(target, args);
        } catch (Exception e) {
       		logger.warn("invoke error. ", e);
            ret.error("", e.getMessage());
            ret.setData(e);
            return false;
        }
        ret.setIsSuccess(true);
        ret.setData(retObj);
        return true;
    }
}
