package com.yh.svc.gov.test.springboot1.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.TestApplication;


@RestController
@RequestMapping("/alm")
public class AlmController {

	private static final Logger logger = LoggerFactory.getLogger(AlmController.class);

	private String name = "haha";
	public String name1 = "haha";
	
	static {
		logger.info("============this is the class static print ");
	}
	
	public AlmController() throws RuntimeException {
		
        ClassLoader p = TestApplication.class.getClassLoader();
        logger.info("============this is the almcontroller. constructor...........{}",p);
        while (p != null) {
        	ClassLoader p1 = p.getParent();
        	logger.info("demo-sb1.AlmController {} 's parent is {}", p, p1);
        	p = p1;
        }

	}
	
	
	@RequestMapping("/mlog")
	public String mlog1(@RequestParam(value="code")int code, @RequestParam(value="bizKey")String bizKey) {
		
//		LogService.mLog(code, bizKey, "inLog-" + bizKey, "outLog-" + bizKey);
		
		logger.info("enter into mlog1 {}, {}", code, bizKey);
		
    	new Exception("test stack").printStackTrace();

    	Class<?>[] ifs = this.getClass().getInterfaces();
    	for (Class<?> c : ifs) {
    		logger.info("---interface: {}", c.getName());
		}
    	
    	Method[] ms = this.getClass().getMethods();
    	for (Method m : ms) {
			logger.info("----method:{}", m.getName());
		}
    	
    	Field[] fs = this.getClass().getFields();
    	for (Field f : fs) {
			logger.info("----field:{}  ,  type:{}", f.getName(), f.getType());
		}
    	if (fs == null || fs.length == 0)
    		logger.info("----no field found");
    	
		return "success " + System.currentTimeMillis();
	}
}
