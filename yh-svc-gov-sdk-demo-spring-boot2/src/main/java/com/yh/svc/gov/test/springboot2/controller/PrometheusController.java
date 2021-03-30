package com.yh.svc.gov.test.springboot2.controller;

import com.yh.svc.gov.test.springboot2.manager.metrics.DemoMetricsManager;
import com.yh.infra.svc.gov.metrics.meter.MetricsProvider;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/prom")
public class PrometheusController {

	private static final Logger logger = LoggerFactory.getLogger(PrometheusController.class);
	
	@Autowired
	private MeterRegistry registry;
	@Autowired
	private MetricsProvider demomp;
	
	@Autowired
	private DemoMetricsManager mgr;
	
	AtomicInteger gv = new AtomicInteger();
	
	
	public PrometheusController() {
		System.out.println("===================PrometheusController.constructor.....");
	}
	
	@RequestMapping(value="/reg")
	public String reg() {
		String key = "alm.test1.counter";
		Counter counter1 = Counter.builder(key).tags("tag1", "value1", "tag2", "value2").register(registry);
		Counter counter2 = Counter.builder(key).tags("tag1", "value1", "tag2", "value3").register(registry);
//		Counter counter1 = registry.counter(key, "tag1", "value1", "tag2", "value2");
//		Counter counter2 = registry.counter(key, "tag1", "value1", "tag2", "value3");
		counter1.increment();
		counter2.increment();
		System.out.println("-------------------------------------start--");
		PrometheusMeterRegistry pmr = (PrometheusMeterRegistry)registry;
		System.out.println(pmr.scrape());
		System.out.println("-------------------------------------end------");
		return "success!";
	}
	@RequestMapping(value="/metrics1",produces = "text/plain")
	public String test1() {
		
		// counter
		Counter counter = registry.counter("alm.block.testblock1.counter", "sys", "alm");
		counter.increment(1);

		// gauge
		Random r = new Random();
		int i = Math.abs(r.nextInt(10));
		registry.gauge("alm.block.testblock1.gauge", gv);
		gv.set(i);
		
		
		// timer
		Timer timer = registry.timer("alm.block.testblock1.timer");
		int ti = Math.abs(r.nextInt(10)) + 1;
		
		for (int j = 0; j < ti; j++) {
			i = Math.abs(r.nextInt(10));
			timer.record(i, TimeUnit.SECONDS);	
		}
		
		
		// summary
		DistributionSummary summary = registry.summary("alm.block.testblock1.summary");
		ti = Math.abs(r.nextInt(10)) + 1;
		for (int j = 0; j < ti; j++) {
			i = Math.abs(r.nextInt(10));
			summary.record(i);	
		}
		String response = "";
		if (registry instanceof PrometheusMeterRegistry) {
			response = ((PrometheusMeterRegistry) registry).scrape();
		}
		logger.debug(response);
		return response;
	}
	@RequestMapping(value="/tag")
	public String testTag() {
		Random r = new Random();  
		int name = Math.abs(r.nextInt(9));

		int c1 = Math.abs(r.nextInt(9));
		Counter counter1 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1", "tag1", "tagvalue1", "tag2", "tagvalue22");
		counter1.increment(c1);

		Search s = registry.find("a");
		Gauge g = s.gauge();
		Iterable it = g.measure();
		
		
		int c2 = Math.abs(r.nextInt(9));
		Counter counter2 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1", "tag1", "tagvalue1");
		counter2.increment(c2);

		int c = Math.abs(r.nextInt(9));
		Counter counter = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1");
		counter.increment(c);
		
		int c6 = Math.abs(r.nextInt(9));
		Counter counter6 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue2");
		counter6.increment(c6);

		
		int c4 = Math.abs(r.nextInt(9));
		Counter counter4 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1");
		counter4.increment(c4);

		int c5 = Math.abs(r.nextInt(9));
		Counter counter5 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue4");
		counter5.increment(c5);

		int c3 = Math.abs(r.nextInt(9));
		Counter counter3 = registry.counter("yh.infra.demo.bz.counter.test10.dev", "name", "namevalue1", "tag1", "tagvalue12", "tag2", "tagvalue22");
		counter3.increment(c3);

		logger.debug("counter: {}, {}, {}, {}", counter.count(),counter1.count(),counter2.count(),counter3.count());

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
	@Scheduled(fixedDelay = 3000)
	public String timer() {
		logger.debug("test timer method.");
		Random r = new Random();  
		int c = Math.abs(r.nextInt(1000));
		mgr.createOrder(c);
		mgr.createSo();
		
		return "success! sleep for " + c + " ms.";
	}

	@RequestMapping(value="/timed")
	@Timed(value = "yh_infra_sb2_timer_test2_parent_dev", extraTags = {"app", "demo-sb2", "project", "YhInfra"})
	public String testTimed(@RequestParam(value = "code") String code) {
		return update(code);
	}	
	
	@Timed(value = "yh_infra_sb2_timer_test2_child_dev", extraTags = {"app", "demo-sb2", "project", "YhInfra"})
	public String update(String code) {
		logger.info("received value: {}", code);
		return "success! " + code + "  .  " + new Date();
	}
}
