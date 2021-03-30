package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.common.utilities.type.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

public class MenuInitManagerImpl implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(MenuInitManagerImpl.class);

	/**
	 * 系统初始化时初始菜单
	 * @Description TODO(用一句话描述该类做什么)
	 * @author jiuzhou.hu@yonghui.cn
	 * @date 2016年3月8日 下午7:05:38
	 * @param event
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getDisplayName().startsWith("WebApplicationContext")) {
			try{
				RequestMappingHandlerMapping requestMappingHandlerMapping = event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		        
		        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator.hasNext();) {
		            RequestMappingInfo info = iterator.next();
		            
		            HandlerMethod method = map.get(info);
		            
		            //注解的URL
		            String patternsUrl = info.getPatternsCondition().toString().replaceAll("\\[", "").replaceAll("]", "");
		            String methodName = method.getMethod().getName();
		            String actionName = method.getBeanType().getName();
		            
		            Map<String, String> item = new HashMap<String, String>();
		            item.put("patternsUrl", patternsUrl);
		            item.put("methodName", methodName);
		            item.put("actionName", actionName);
		            data.add(item);
		        }
		        System.out.println(JsonUtil.buildNormalBinder().toJson(data));
			}catch(NullPointerException e) {
				log.info("RequestMappingHandlerMapping is null");
			}
		}
	}
}
