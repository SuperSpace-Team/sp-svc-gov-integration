/**
 * 
 */
package com.yh.infra.svc.gov.metrics.meter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yh.infra.svc.gov.metrics.constant.Constants;
import com.yh.infra.svc.gov.metrics.constant.MetricsType;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;

/**
 * 内部使用的类。
 * 
 * 注意：
 * 1) 所有的方法中，如果需要提供tags，必须提供完成的tags。 因为metrics的区分是通过 name + tags 来区分的， 不完整的tags会导致 a)异常， b)定位到错误的metrics。
 * 2) tag必须是偶数个， 因为其是这个的格式“key1, value1, key2, value2, ....”。系统会提取成为  key1=value1, key2=value2,...
 * 
 * @author luchao 2020-12-04
 *
 */
public final class InternalMetricsHelper {
	private static final Logger logger = LoggerFactory.getLogger(InternalMetricsHelper.class);
	
	/**
	 * 验证并转换tags
	 * 
	 * @param args
	 * @return
	 */
	private static Tags convertAndMerge(String ...args) {
		if (args == null)
			return null;
		if ((args.length & 1) == 1)
			return null;
		
		return Tags.of(args);
	}
	
	/**
	 * 注册指标，普通注册方式， 不使用回调。
	 * 
	 * @param name
	 * @param type
	 * @param tags
	 */
	public static Meter register(MeterRegistry registry, String name, MetricsType type, Iterable<Tag> tags) {
		Meter ret = null;
		switch (type) {
		case COUNTER: {
			registry.counter(name, tags);
			break;
		}
		case GAUGE: {
			ret = Gauge.builder(name,()-> 0).tags(tags).register(registry);
			break;
		}
		case TIMER: {
			ret = registry.timer(name, tags);
			break;
		}
		case SUMMARY: {
			ret = registry.summary(name, tags);
			break;
		}
		default: {
			logger.error("unknown metrics type: {}, {}, [{}]", name, type, tags);
			break;
		}
		}
		return ret;
	}
	/**
	 * 注册指标，普通注册方式， 不使用回调。
	 * 
	 * @param name
	 * @param type
	 * @param tagArray
	 */
	public static Meter register(MeterRegistry registry, String name, MetricsType type, String ...tagArray) {
		Tags tags = convertAndMerge(tagArray);
		return register(registry, name, type, tags);
	}
	
	/**
	 * 注册指标，基于自定义回调方法。即回调对象及其方法是自定义的，不使用MetricsProvider
	 * 
	 * @param <T>
	 * @param name
	 * @param type
	 * @param tags
	 * @param obj
	 * @param f
	 * @param fl
	 * @param timeunit
	 */
	public static <T> Meter register(MeterRegistry registry, String name, MetricsType type, T obj, ToDoubleFunction<T> f, ToLongFunction<T> fl, TimeUnit timeunit, String ...tagArray) {
		Tags tags = convertAndMerge(tagArray);
		Meter ret = null;
		switch (type) {
		case COUNTER: {
			ret = FunctionCounter.builder(name, obj, f).tags(tags).register(registry);
			break;
		}
		case GAUGE: {
			ret = Gauge.builder(name, obj, f).tags(tags).register(registry);
			break;
		}
		case TIMER: {
			ret = FunctionTimer.builder(name, obj, fl, f, timeunit).tags(tags).register(registry);
			break;
		}
		case SUMMARY: {
			ret = DistributionSummary.builder(name).tags(tags).register(registry);
			break;
		}
		default: {
			logger.error("unknown metrics type: {}, {}, [{}]", name, type, tagArray);
			break;
		}
		}
		return ret;
	}

	
	/**
	 * 注册指标， 基于标准回调接口 MetricsProvider
	 * 
	 * @param meter
	 * @param name
	 * @param type
	 * @param timeUnit
	 * @param tags
	 * @return
	 */
	public static Meter register(MeterRegistry registry, MetricsProvider meter, String name, MetricsType type, Map<String, String> configMap , Iterable<Tag> tags) {
		Meter ret = null;
		switch (type) {
		case COUNTER: {
			ret = FunctionCounter.builder(name, meter, c->c.getCounterValue(name, tags)).tags(tags).register(registry);
			break;
		}
		case GAUGE: {
			ret = Gauge.builder(name, meter, c -> c.getGaugeValue(name, tags)).tags(tags).register(registry);
			break;
		}
		case TIMER: {
			ret = FunctionTimer
					.builder(name, 
						meter, 
						c -> c.getTimerLongValue(name, tags), 
						c -> c.getTimerDoubleValue(name, tags), 
						TimeUnit.valueOf(configMap.getOrDefault(Constants.KEY_TIME_UNIT, "MILLISECONDS")))
					.tags(tags)
					.register(registry);
			break;
		}
		case SUMMARY: {
			ret = DistributionSummary.builder(name).tags(tags).register(registry);
			break;
		}
		default: {
			logger.error("unknown metrics type: {}, {}, [{}]", name, type, tags);
			break;
		}
		}
		return ret;

	}
	/**
	 * 注册指标， 基于标准回调接口 MetricsProvider
	 * 
	 * 
	 * @param meter
	 * @param name
	 * @param type
	 * @param timeUnit
	 * @param tagArray
	 * @return
	 */
	public static Meter register(MeterRegistry registry, MetricsProvider meter, String name, MetricsType type, TimeUnit timeUnit, String ...tagArray) {
		Tags tags = convertAndMerge(tagArray);
		Map<String, String> configMap = new HashMap<>();
		configMap.put(Constants.KEY_TIME_UNIT, timeUnit.toString());
		return register(registry, meter, name, type, configMap, tags);
	}
	
	/**
	 * 更新指标的值
	 * name 指标名称
	 * type  指标类型
	 * doubleValue 用于除timer外的其他指标
	 * longValue 用于timer 指标，非Timer指标时不用。
	 * timeUnit 用于timer指标，非Timer指标时不用，可为null
	 * tagArray tag数组 
	 * 
	 * @param name
	 * @param type
	 * @param doubleValue  
	 * @param longValue
	 * @param timeUnit
	 * @param tagArray
	 */
	public static void update(MeterRegistry registry, String name, MetricsType type, double doubleValue, long longValue, TimeUnit timeUnit, String ...tagArray) {
		Tags tags = convertAndMerge(tagArray);
		switch (type) {
		case COUNTER: {
			Counter counter = registry.counter(name, tags);
			counter.increment(doubleValue);
			break;
		}
		case GAUGE: {
			registry.gauge(name, tags , doubleValue);
			break;
		}
		case TIMER: {
			Timer t = registry.timer(name, tags);
			t.record(longValue, timeUnit);
			break;
		}
		case SUMMARY: {
			DistributionSummary t = registry.summary(name, tags);
			t.record(doubleValue);
			break;
		}
		default: {
			logger.error("unknown metrics type: {}, {}, [{}]", name, type, tagArray);
			break;
		}
		}
	}
	
	
}
