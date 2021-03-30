package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LSH10022
 *
 */
public class Uac2BaseManager {

	 public Map<String,Object>  paramsMap(){
	  Map<String,Object> resultMap = new HashMap<>() ;
	  BeanRegistry sc = BeanRegistry.getInstance();
	  AppRegContext ctx = sc.getBean(AppRegContext.class);
	  AppRegConfig cfg = ctx.getConfig();
	  String startUrl = cfg.getUnionGatewayUrl();
	  resultMap.put("startUrl", startUrl) ;
	  return resultMap;
	 }
}
