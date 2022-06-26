package com.sp.infra.svc.gov.metrics.meter;

import io.micrometer.core.instrument.Tag;

/**
 * 回调方式的接口。 
 * 
 * @author luchao  2020-12-03
 *
 */
public interface MetricsProvider {
	default long getCounterValue(String key, Iterable<Tag> tags) {return 0;};
	default double getGaugeValue(String key, Iterable<Tag> tags) {return 0;};
	default long getTimerLongValue(String key, Iterable<Tag> tags) {return 0;};
	default double getTimerDoubleValue(String key, Iterable<Tag> tags) {return 0;};
}
