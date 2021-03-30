package com.yh.infra.svc.gov.sdk.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * json转换的工具类
 * 
 * @author Justin Hu
 *
 */
public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略没有定义在属性

	}

	/**
	 * 将json串转换成对象
	 * 
	 * @param json
	 * @param parametrized
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readValue(String json, Class<?> parametrized) {
		if (json == null || json.trim() == "") {
			return null;
		}
		try {
			return (T) mapper.readValue(json, parametrized);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> T readValueSafe(String json, Class<?> parametrized) {
		try {
			return readValue(json, parametrized);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readList(String str, Class<T> cls) {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, cls);

		try {
			return (List<T>) mapper.readValue(str, javaType);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将对象转成json串
	 * 
	 * @param obj
	 * @return
	 */
	public static String writeValue(Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将对象转成json串
	 * 
	 * @param obj
	 * @return
	 */
	public static String writeValueSafe(Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("write JSON failed. ", e);
			return "";
		}
	}

	/**
	 * 把json字符串转换为对象
	 * 
	 * @param <T>
	 * @param json
	 * @param parametrized
	 * @param parameterClasses
	 * @return
	 */
	public static <T> T parseJson(String json, Class<?> parametrized, Class<?>... parameterClasses) {
		if (json == null || json == "") {
			return null;
		}
		try {
			JavaType jt = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
			return mapper.readValue(json, jt);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String json, Class<?> parametrized) {
		if (json == null || json == "") {
			return null;
		}
		try {
			return (T) mapper.readValue(json, parametrized);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String toJson(Object obj) {
		if (obj == null) {
			return "";
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> T objectToBean(Object object, Class<T> cls) {
		if (object == null) {
			return null;
		}
		String json = null;
		if (object instanceof String) {
			json = object.toString();
		} else {
//			json = JsonUtil.buildNormalBinder().toJson(object);
			json = JsonUtil.writeValue(object);
		}
		if (json != null) {
			return JsonUtil.readValue(json, cls);
//			return JsonUtil.buildNormalBinder().getJsonToObject(json,cls);
		} else {
			return null;
		}
	}

	public static <T> List<T> objectToList(Object object, Class<T> cls) {
		if (object == null) {
			return null;
		}
		String json = null;
		if (object instanceof String) {
			json = object.toString();
		} else {
			json = com.yh.common.utilities.type.JsonUtil.buildNormalBinder().toJson(object);
		}
		if (json != null) {
			return com.yh.common.utilities.type.JsonUtil.buildNormalBinder().getJsonToList(json, cls);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] objectToArray(Object object, Class<T> cls) {
		if (object == null) {
			return null;
		}
		List<T> list = objectToList(object, cls);
		return (T[]) list.toArray();
	}

}
