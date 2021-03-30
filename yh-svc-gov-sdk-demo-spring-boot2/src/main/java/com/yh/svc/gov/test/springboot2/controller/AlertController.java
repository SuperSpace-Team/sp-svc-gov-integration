package com.yh.svc.gov.test.springboot2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alert")
public class AlertController {

	private static final Logger logger = LoggerFactory.getLogger(AlertController.class);
	
	
	@RequestMapping(value="/email")
	public void testEmail(@RequestBody String body) {
		logger.debug("received alert msg: {}", body);
	}
}
