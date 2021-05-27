package com.yh.infra.svc.gov.sdk.skywalking;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.ClazzUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * SkyWalking traceId、segmentId、spanId生成类
 * @author luchao
 * @date 2021/4/25 8:47 下午
 */
public class SkyWalkingGenerator {
	private static final Logger logger = LoggerFactory.getLogger(SkyWalkingGenerator.class);

	private static ThreadLocal<Method> swMethod = new ThreadLocal<>();

	public static Map<String, String> getSkyWalkingMap() {
		Map<String, String> resultMap = new HashMap<>();
		try {
			if (swMethod.get() == null) {
				ClassLoader skyWalkingClassLoader = getSkyWalkingClassLoader();
				if (skyWalkingClassLoader == null) {
					return resultMap;
				}
				swMethod.set(ClazzUtil.getMethod(skyWalkingClassLoader, SdkCommonConstant.SKYWALKING_CONTEXT_CLASSPATH_NAME, SdkCommonConstant.SKYWALKING_CONTEXT_METHOD_NAME));
			}
			if (swMethod.get() == null) 
				return resultMap;
			return (Map<String, String>) swMethod.get().invoke(null);
		} catch (Exception e) {
			logger.error("failed to get sw info. ", e);
			swMethod.remove();
			return resultMap;
		}
	}

	private static ClassLoader getSkyWalkingClassLoader() {
		ClassLoader swCl = Thread.currentThread().getContextClassLoader();
		Method method;
		// 找到sw的classloader。
		while (swCl != null) {
			method = ClazzUtil.getMethod(swCl, SdkCommonConstant.SKYWALKING_CONTEXT_CLASSPATH_NAME, SdkCommonConstant.SKYWALKING_CONTEXT_METHOD_NAME);
			if (method == null)
				swCl = swCl.getParent();
			else
				break;
		}
		return swCl;
	}
}
