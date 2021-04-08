/*
 * VersionController.java Created On 2019年1月16日
 * Copyright(c) 2019 Yonghui Inc.
 * ALL Rights Reserved.
 */
package com.yh.svc.gov.test.springboot1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.yh.common.utilities.type.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.utils.GitPropertyUtil;


/**
 * 
 * @author luchao  2019-04-24
 *
 */
@RestController
public class VersionController{
	private static final String CODE_VERSION = "codeVersion";

	private static final Logger logger = LoggerFactory.getLogger(EntryController.class);

	private int sleepSec = 1;
	
    @RequestMapping(value = "/customize_health")
    public Map<String, Object> healthCheck() {
    	Map<String, Object> retMap = new HashMap<>();
		retMap.put("status", 200);
    	Properties p = GitPropertyUtil.loadProps();
    	if (p == null) {
    		retMap.put(CODE_VERSION, "unknown version");
    		return retMap;
    	}
    	String v = p.getProperty("git.commit.id");
    	if (StringUtil.isBlank(v))
    		retMap.put(CODE_VERSION, "empty version");
    	else
    		retMap.put(CODE_VERSION, v);
    	return retMap;
    }
    @RequestMapping(value = "/setSleep")
    public String setSleep(@RequestParam("sleep") int sleep) {
    	sleepSec = sleep;
		logger.info("set sleep duration to {} seconds.", sleep);
    	return "set sleep time to " + sleep + " seconds.";
    }
    
    @RequestMapping(value = "/long_health")
    public Map<String, Object> longHealthCheck() {
    	Map<String, Object> retMap = new HashMap<>();
		retMap.put("status", "UP");
		retMap.put("timestamp", new Date());
		logger.info("begin to sleep for {} seconds.", sleepSec);
		for (int i = 0; i < sleepSec; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("finished to sleep for {} seconds.", sleepSec);
		
    	return retMap;
    }
}
