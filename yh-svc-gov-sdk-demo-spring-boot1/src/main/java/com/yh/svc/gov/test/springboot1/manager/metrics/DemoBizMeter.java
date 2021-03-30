/**
 * 
 */
package com.yh.svc.gov.test.springboot1.manager.metrics;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.yh.infra.svc.gov.metrics.meter.MetricsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import io.micrometer.core.instrument.Tag;

/**
 * @author luchao  2020-12-10
 *
 */
@Component
public class DemoBizMeter implements MetricsProvider {
	private static final Logger logger = LoggerFactory.getLogger(DemoBizMeter.class);
	
	AtomicInteger ai = new AtomicInteger();
	
	@Override
	public long getCounterValue(String key, Iterable<Tag> tags) {
		Random r = new Random();
		
		int ret = ai.addAndGet(Math.abs(r.nextInt(10)));
		logger.info("get the counter value of {}, [{}], {}", key, tags, ret);
		
		return ret;
	}

	@Override
	public double getGaugeValue(String key, Iterable<Tag> tags) {
		
		Random r = new Random();
		int ret = Math.abs(r.nextInt(100));
		
		logger.info("get the gauge value of {}, [{}], {}", key, tags, ret);
		
		return ret;
	}

}
