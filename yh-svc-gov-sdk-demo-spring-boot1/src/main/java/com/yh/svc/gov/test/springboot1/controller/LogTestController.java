package com.yh.svc.gov.test.springboot1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.manager.LogManager;


@RestController
public class LogTestController {

    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

    @Autowired
    LogManager logMgr;
    
    @RequestMapping("/log/tx")
    public String logTx() {
    	logger.info("enter the logtx.");
    	String codes[] = new String[3];
    	codes[0] = "http://1dfasfdsafdsfsdgsdasdsaf:32321/fasdfsdafdsafldsfldsjgl;dsj;lgjdsl;gjdsal;gjdslajgldsajgsdgsgsad";
    	codes[1] = "http://2dfasfdsafdsfsdgsdasdsaf:32321/fasdfsdafdsafldsfldsjgl;dsj;lgjdsl;gjdsal;gjdslajgldsajgsdgsgsad";
    	codes[2] = "http://3dfasfdsafdsfsdgsdasdsaf:32321/fasdfsdafdsafldsfldsjgl;dsj;lgjdsl;gjdsal;gjdslajgldsajgsdgsgsad";
    	
    	try {
			logMgr.throwException(null);
	    	logger.info("after the mgr call.");
		} catch (Exception e) {
			logger.error("got error for null: {}", codes, e);
		}
    	
        return "success";
    }
}
