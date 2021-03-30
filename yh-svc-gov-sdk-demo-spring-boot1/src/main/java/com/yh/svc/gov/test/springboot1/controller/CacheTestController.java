package com.yh.svc.gov.test.springboot1.controller;

import javax.cache.Cache;
import javax.cache.Cache.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.manager.impl.CacheTestManagerImpl;


@RestController
public class CacheTestController {

    private static final Logger logger = LoggerFactory.getLogger(CacheTestController.class);

    @Autowired
    CacheTestManagerImpl mgr;
	@Autowired
	private javax.cache.CacheManager cacheManager;

    
    @RequestMapping("/cache/visit")
    public String visit(@RequestParam(value="value") String value) {
    	String ret = mgr.visit(value, value+"ddd");
        return "success! " + ret;
    }
    
    
    @RequestMapping("/cache/list")
    public String list() {
    	Cache<Object, Object> c = cacheManager.getCache("checkTenantAuth");
		logger.info("cache entities......");
    	for (Entry<Object, Object> e : c) {
			logger.info("key: {}   ,     value: {}", e.getKey() , e.getValue());
		}
		
        return "success list";
    }
    @RequestMapping("/cache/remove")
    public String remove(@RequestParam(value="value") String value, @RequestParam(value="url") String url) {
    	Cache<Object, Object> c = cacheManager.getCache("checkTenantAuth");
    	SimpleKey sk = new SimpleKey(value, url);
    	c.remove(sk);
		
        return "success clean";
    }
    @RequestMapping("/cache/clean")
    public String clean() {
    	Cache<Object, Object> c = cacheManager.getCache("checkTenantAuth");
    	
		c.clear();
		
        return "success clean";
    }
}
