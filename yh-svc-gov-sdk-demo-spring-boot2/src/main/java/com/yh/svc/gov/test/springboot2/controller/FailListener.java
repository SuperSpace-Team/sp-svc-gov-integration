package com.yh.svc.gov.test.springboot2.controller;

import io.micrometer.core.instrument.Meter.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class FailListener implements BiConsumer<Id, String> {
	private static final Logger logger = LoggerFactory.getLogger(FailListener.class);
	@Override
	public void accept(Id id, String u) {
		logger.warn(" [{}],  [{}]", id, u);
	}

}
