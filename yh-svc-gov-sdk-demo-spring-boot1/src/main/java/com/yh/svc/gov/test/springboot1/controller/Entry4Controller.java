package com.yh.svc.gov.test.springboot1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

public class Entry4Controller {

	private static final Logger logger = LoggerFactory.getLogger(Entry4Controller.class);


	public String start(String appId) {
		logger.info("Entry4Controller get the data: {}", appId);
		return "Entry4Controller get the app:" + appId;
	}
}
