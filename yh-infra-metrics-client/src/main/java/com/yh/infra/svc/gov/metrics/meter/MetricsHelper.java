package com.yh.infra.svc.gov.metrics.meter;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yh.infra.svc.gov.metrics.constant.MetricsType;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

/**
 * 提供给业务应用使用的类。 普通情况下， 业务应用只使用这一个类就可以了。
 * 
 * 注意：
 * 1) 所有的方法中，如果需要提供tags，必须提供完成的tags。 因为metrics的区分是通过 name + tags 来区分的， 不完整的tags会导致 a)异常， b)定位到错误的metrics。
 * 2) tag必须是偶数个， 因为其是这个的格式“key1, value1, key2, value2, ....”。系统会提取成为  key1=value1, key2=value2,...
 * 
 * @author luchao 2020-12-04
 *
 */
@Component
public class MetricsHelper {
	
	@Autowired
	private MeterRegistry registry; 
	
		
	/**
	 * 注册指标，普通注册方式， 不使用回调。
	 * 
	 * @param name
	 * @param type
	 * @param tags
	 */
	public Meter register(String name, MetricsType type, Iterable<Tag> tags) {
		return InternalMetricsHelper.register(registry, name, type, tags);
	}
	/**
	 * 注册指标，普通注册方式， 不使用回调。
	 * 
	 * @param name
	 * @param type
	 * @param tagArray
	 */
	public Meter register(String name, MetricsType type, String ...tagArray) {
		return InternalMetricsHelper.register(registry, name, type, tagArray);
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
	public <T> Meter register(String name, MetricsType type, T obj, ToDoubleFunction<T> f, ToLongFunction<T> fl, TimeUnit timeunit, String ...tagArray) {
		return InternalMetricsHelper.register(registry, name, type, obj, f, fl, timeunit, tagArray);
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
	public Meter register(MetricsProvider meter, String name, MetricsType type, Map<String, String> configMap , Iterable<Tag> tags) {
		return InternalMetricsHelper.register(registry, meter, name, type, configMap, tags);

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
	public Meter register(MetricsProvider meter, String name, MetricsType type, TimeUnit timeUnit, String ...tagArray) {
		return InternalMetricsHelper.register(registry, meter, name, type, timeUnit, tagArray);
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
	public void update(String name, MetricsType type, double doubleValue, long longValue, TimeUnit timeUnit, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, type, doubleValue, longValue, timeUnit, tagArray);
	}
}
