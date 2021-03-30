package com.yh.svc.gov.test.springboot1.controller;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yh.svc.gov.test.springboot1.manager.metrics.DemoMetricsManager;
import com.yh.infra.svc.gov.metrics.meter.MetricsHelper;
import com.yh.infra.svc.gov.metrics.meter.MetricsProvider;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@RestController("/prom")
public class PrometheusController {

	private static final Logger logger = LoggerFactory.getLogger(PrometheusController.class);
	
	@Autowired
	private MeterRegistry registry;
	@Autowired
	private MetricsProvider demomp;
	
	@Autowired
	private DemoMetricsManager mgr;
	
	@Autowired
	private MetricsHelper helper;
	
	
	@RequestMapping(value="/reg")
	public String reg() {
		String key = "alm.test3.timer";
		FunctionTimer.builder(key, demomp, c->c.getTimerLongValue("alm.test3.timer", null), c->c.getTimerDoubleValue("alm.test3.timer", null), TimeUnit.SECONDS).register(registry);
		
		key = "alm.test3.gauge";
		Gauge.builder(key, demomp, c->c.getGaugeValue("alm.test3.gauge", null)).register(registry);
		
		
		logger.info("finished reg the timer.");
		return "success!";
	}
	@RequestMapping(value="/update")
//	@Timed(value = "yh_infra_demosb1_timer_test1_dev", extraTags = {"app", "demo-sb1", "project", "YhInfra"})
	public String update(@RequestParam(value = "code") String code) {
		
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
		logger.info("finished call sg... {}", code);

		
		Random r = new Random();  
		int c = Math.abs(r.nextInt(200));
		logger.info("update value : {}, {}", c, code);
		
		try {
			Thread.sleep(c);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "success " + new Date();
	}
	
	@RequestMapping(value="/tag")
	public String testTag() {
		Random r = new Random();  
		int name = Math.abs(r.nextInt(9));
		int c = Math.abs(r.nextInt(9));
		Counter counter = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1", "tag1", "tagvalue1", "tag2", "tagvalue2");
		counter.increment(c);
		
		int c1 = Math.abs(r.nextInt(9));
		Counter counter1 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue2");
		counter1.increment(c1);
		
		int c2 = Math.abs(r.nextInt(9));
		Counter counter2 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue3");
		counter2.increment(c2);

		int c3 = Math.abs(r.nextInt(9));
		Counter counter3 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1", "tag1", "tagvalue12", "tag2", "tagvalue22");
		counter3.increment(c3);

		logger.info("counter: {}, {}, {}, {}", counter.count(),counter1.count(),counter2.count(),counter3.count());

//		AtomicLong a = registry.gauge("yh.infra.demo.bz.gauge.test10.dev", new AtomicLong());
//		a.addAndGet(c);
//		logger.info("after: {}", a.get());
		logger.info("-------------------------------------start--");
		PrometheusMeterRegistry pmr = (PrometheusMeterRegistry)registry;
		System.out.println(pmr.scrape());
		System.out.println("-------------------------------------------");
		
		return "success";
	}
	@RequestMapping(value="/timer")
//	@Scheduled(fixedDelay = 3000)
	public String timer() {
		logger.info("test timer method.");
		Random r = new Random();  
		int c = Math.abs(r.nextInt(1000));
		mgr.createOrder(c);
		mgr.createSo();
		
		return "success! sleep for " + c + " ms.";
	}

}
