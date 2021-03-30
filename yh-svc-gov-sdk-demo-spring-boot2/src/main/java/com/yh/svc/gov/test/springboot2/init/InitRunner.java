package com.yh.svc.gov.test.springboot2.init;

import com.yh.svc.gov.test.springboot2.controller.FailListener;
import com.yh.infra.svc.gov.metrics.meter.MetricsHelper;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // 此注解注明加载顺序
public class InitRunner implements ApplicationRunner {

	@Autowired
	private MeterRegistry registry;
	
	@Autowired
	private FailListener listener;

	@Autowired
	private MetricsHelper helper;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
	}

	

}
