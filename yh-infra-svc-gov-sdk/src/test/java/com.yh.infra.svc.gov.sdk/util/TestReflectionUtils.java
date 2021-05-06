/**
 * 
 */
package com.yh.infra.svc.gov.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Qian.Chao(钱超) 2018-12-19
 *
 */
public class TestReflectionUtils {
	public static void setValue(Object target, String fieldName, Object value) {
		Field f;
		try {
			f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getValue(Object target, String fieldName) {
		Field f;
		try {
			f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Object getValue(Class clz, String fieldName) {
		Field f;
		try {
			f = clz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 仅为单元测试使用!!!! 
	 * 
	 * @param clz
	 * @param fieldName
	 * @param value
	 * @param retClz
	 * @return
	 * @throws Exception
	 */
	public static <T> T setStaticValue(Class clz, String fieldName, Object value) {
		Field target;
		T ret = null;
		try {
			target = clz.getDeclaredField(fieldName);
			target.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			int modify = target.getModifiers() & ~Modifier.FINAL;
			modifiersField.setInt(target, modify);
			ret = (T)target.get(null);
			target.set(null, value);

			modify = target.getModifiers() & ~Modifier.FINAL;
			modifiersField.setInt(target, modify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 仅为单元测试使用!!!! 
	 * 
	 * @param clz
	 * @param fieldName
	 * @param value
	 * @param retClz
	 * @return
	 * @throws Exception
	 */
	public static <T> T setStaticValue(Class clz, String fieldName, Object value, Class<T> retClz) {
		Field target;
		T ret = null;
		try {
			target = clz.getDeclaredField(fieldName);
			target.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			int modify = target.getModifiers() & ~Modifier.FINAL;
			modifiersField.setInt(target, modify);
			ret = (T)target.get(null);
			target.set(null, value);

			modify = target.getModifiers() & ~Modifier.FINAL;
			modifiersField.setInt(target, modify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Object execute(Object target, String methodName, Class[] paraTypes, Object[] values) {
		Object ret = null;
		try {
			Method m = target.getClass().getDeclaredMethod("doConfigure", paraTypes);
			ret = m.invoke(target, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
