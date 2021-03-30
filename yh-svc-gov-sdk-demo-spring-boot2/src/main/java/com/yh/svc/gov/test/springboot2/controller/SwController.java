package com.yh.svc.gov.test.springboot2.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/sw")
public class SwController {
	Logger logger = LoggerFactory.getLogger(SwController.class);
	
	@GetMapping("/stack")
	public String stack() {
		new Exception("test stack").printStackTrace();
		return "成功stack " + new Date();
	}

	@GetMapping("/trace")
	public String trace(@RequestParam(value = "code") String code) {
		logger.info("received code {}", code);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://sit-api-base.cloud.bz/api/pg/test/echo?code=" + code);
		try(CloseableHttpResponse response = httpclient.execute(httpget)) {
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				logger.info("response : {}", content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error " + e.getMessage();
		}

		return "ok! " + new Date();

	}
}
