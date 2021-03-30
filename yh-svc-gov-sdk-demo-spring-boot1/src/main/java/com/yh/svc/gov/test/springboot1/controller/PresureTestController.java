package com.yh.svc.gov.test.springboot1.controller;



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pt")
public class PresureTestController {
	private static final Logger logger = LoggerFactory.getLogger(PresureTestController.class);
	private int countPrime(int n) {
		int total = 0;
		for (int i = 1; i <= n; i++) {
			int count = 0;
			for (int j = 1; j <= i; j++) {
				if (i % j == 0) {
					count++;
				}
			}
			if (count == 2) {
				total ++;
			}
		}
		return total;
	}

	@GetMapping("/cpu1")
	public String cpu1(@RequestParam(value = "max") int max) {
		long start = System.currentTimeMillis();
		int numberOfPrimes = countPrime(max);
		long end = System.currentTimeMillis();
		logger.info("finished computing for {}, result is {}. time cost: {}", max, numberOfPrimes, end-start);
		return "success " + (end-start) + " . time:" + new Date();
	}
}
