/**
 * 
 */
package com.yh.svc.gov.test.springboot2.manager.metrics;

import com.yh.infra.svc.gov.metrics.meter.MetricsProvider;
import io.micrometer.core.instrument.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luchao  2020-12-10
 *
 */
@Component
public class DemoBizMetricsProvider implements MetricsProvider {
	private static final Logger logger = LoggerFactory.getLogger(DemoBizMetricsProvider.class);
	
	AtomicInteger ai = new AtomicInteger();
	
	@Override
	public long getCounterValue(String key, Iterable<Tag> tags) {
		
		if (key.equals("yh.infra.demo.bz.counter.test1.dev")) {
			// 此处是针对 yh.infra.demo.bz.counter.test1.dev 监控指标的获取逻辑
			for (Tag tag : tags) {
				if (tag.getKey().equals("app") && tag.getValue().equals("demo.sb2")) {
					// 此处是针对 app=demo.sb2  的监控指标获取逻辑
				}
			}
		}
		
		
		// 这是 mock数据。
		Random r = new Random();
		int ret = Math.abs(r.nextInt(10));
//		ret = ai.addAndGet(ret);
		
		ret = ai.incrementAndGet();
		
		logger.info("get the counter value of {}, [{}], {}", key, tags, ret);
		// mock 结束
		
		return ret;
	}

	@Override
	public double getGaugeValue(String key, Iterable<Tag> tags) {
		
		if (key.equals("yh.infra.demo.bz.gauge.test1.dev")) {
			// 此处是针对 yh.infra.demo.bz.counter.test1.dev 监控指标的获取逻辑
			for (Tag tag : tags) {
				if (tag.getKey().equals("app") && tag.getValue().equals("demo.sb2")) {
					// 此处是针对 app=demo.sb2  的监控指标获取逻辑
				}
			}
		}

		// 这是 mock数据。
		Random r = new Random();
		int ret = Math.abs(r.nextInt(100));
		// mock 结束
		
		logger.info("get the gauge value of {}, [{}], {}", key, tags, ret);
		
		return ret;
	}

}
