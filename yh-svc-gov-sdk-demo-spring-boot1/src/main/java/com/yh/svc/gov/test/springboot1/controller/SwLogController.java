//package com.yh.svc.gov.test.springboot1.controller;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yh.svc.gov.test.springboot1.manager.SwManager;
//import com.yh.common.utilities.json.JsonUtil;
//
//@RestController
//@RequestMapping("/swlog")
//public class SwLogController {
//	/**
//	 * 自定义trigger
//	 */
//	@Autowired
//	private SwManager swManager;
//
//	Logger logger = LoggerFactory.getLogger(SwLogController.class);
//
//
//	@GetMapping("/test")
//	public String test(@RequestParam(value = "time") int time, @RequestParam(value = "url") String url) {
//		swManager.swTest(time, url);
//		ClassLoader skyWalkingClassLoader = getSkyWalkingClassLoader();
//		try {
//			Class<?> aClass = skyWalkingClassLoader.loadClass("com.yh.infra.skywalking.plugin.log4j.lookup.TracerLookup");
//			if (aClass != null) {
//				return aClass.getName();
//			}
//		} catch (Exception e) {
//			return e.toString();
//		}
//		if (skyWalkingClassLoader != null) {
//			return skyWalkingClassLoader.getClass().getName();
//		}
//		return "成功";
//	}
//
//	private static ClassLoader getSkyWalkingClassLoader() {
//		ClassLoader swCl = Thread.currentThread().getContextClassLoader();
//		Method method;
//		// 找到sw的classloader。
//		while (swCl != null) {
//			method = ClazzUtil.getMethod(swCl, Constants.SKYWALKING_CONTEXT_CLASSPATH_NAME, Constants.SKYWALKING_CONTEXT_METHOD_NAME);
//			if (method == null)
//				swCl = swCl.getParent();
//			else
//				break;
//		}
//		return swCl;
//	}
//
//	@GetMapping("/testLog")
//	public String testLog() {
//		List<ClassLoader> list = new ArrayList();
//		logger.info("testLog 66666");
//		ClassLoader classLoader = this.getClass().getClassLoader();
//		list.add(classLoader);
//		return "成功";
//	}
//
//}
