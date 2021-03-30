package com.yh.svc.gov.test.springboot1.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/grafana")
public class GrafanaController {

	private static final Logger logger = LoggerFactory.getLogger(GrafanaController.class);
	
	@RequestMapping("/wh/test1")
	public String test1(@RequestBody(required = false) String strValue) {
		
		
		logger.info("value received: {}", strValue);
		System.out.println(strValue);
		return "success " + new Date();
	}
}
