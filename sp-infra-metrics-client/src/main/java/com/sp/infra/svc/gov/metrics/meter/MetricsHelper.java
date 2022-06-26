package com.sp.infra.svc.gov.metrics.meter;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import org.springframework.stereotype.Component;

import com.sp.infra.svc.gov.metrics.constant.MetricsType;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;


/**
 * 提供给业务应用使用的类。 普通情况下， 业务应用只使用这一个类就可以了。<br/>
 *
 * 注意： <br/>
 * 1) 所有的方法中，如果需要提供tags，必须提供完整的tags。 因为metrics的区分是通过 name + tags 来区分的， 不完整的tags会导致 <br/>
 *     a)异常， <br/>
 *     b)定位到错误的metrics。  <br/>
 * 2) tagArray数组必须是偶数， 因为它的含义是“key1, value1, key2, value2, ....”。系统会提取成为  key1=value1, key2=value2,... <br/>

 * @author luchao 2020-12-04
 *
 */
@Component(value = "metricsHelper")
public class MetricsHelper {

	private MeterRegistry registry;

	public MetricsHelper(MeterRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 注册指标，基于自定义回调方法。即回调对象及其方法是自定义的，不使用MetricsProvider <br>
	 * 本方法仅用对于COUNTER/GAUGE/TIMER <br>
	 *
	 *
	 * @param <T>
	 * @param name
	 * @param type
	 * @param obj
	 * @param f
	 * @param fl
	 * @param timeunit
	 * @param tagArray
	 * @return
	 */
	public <T> Meter register(String name, MetricsType type, T obj, ToDoubleFunction<T> f, ToLongFunction<T> fl, TimeUnit timeunit, String ...tagArray) {
		return InternalMetricsHelper.register(registry, name, type, obj, f, fl, timeunit, tagArray);
	}

	/**
	 * 已弃用。 <br/>
	 * 注册指标， 基于标准回调接口 MetricsProvider。<br>
	 *
	 * @param meter
	 * @param name
	 * @param type
	 * @param configMap
	 * @param tags
	 * @return
	 */
	@Deprecated
	public Meter register(MetricsProvider meter, String name, MetricsType type, Map<String, String> configMap , Iterable<Tag> tags) {
		return InternalMetricsHelper.register(registry, meter, name, type, configMap, tags);

	}
	/**
	 * 注册指标， 基于标准回调接口 MetricsProvider。
	 * 本方法仅用于COUNTER/GAUGE/TIMER <br>
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
	 * 已弃用<br/>
	 * 更新指标的值。<br/>
	 * name： 指标名称<br/>
	 * type：  指标类型<br/>
	 * doubleValue： 用于除timer外的其他指标<br/>
	 * longValue： 用于timer 指标，非Timer指标时不用。<br/>
	 * timeUnit： 用于timer指标，非Timer指标时不用，可为null<br/>
	 * tagArray： tag数组 <br>
	 *
	 * @param name
	 * @param type
	 * @param doubleValue
	 * @param longValue
	 * @param timeUnit
	 * @param tagArray
	 */
	@Deprecated
	public void update(String name, MetricsType type, double doubleValue, long longValue, TimeUnit timeUnit, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, type, doubleValue, longValue, timeUnit, tagArray);
	}


	/**
	 * 更新COUNTER指标的值，如果指标不存在，会自动注册。用于非回调方式的指标。<br>
	 *
	 * @param name 指标名称<br>
	 * @param doubleValue   值<br>
	 * @param tagArray tag数组<br>
	 */
	public void updateCounter(String name, double doubleValue, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, MetricsType.COUNTER, doubleValue, 0, null, tagArray);
	}
	/**
	 * 更新GAUGE指标的值，如果指标不存在，会自动注册。用于非回调方式的指标。<br>
	 *
	 * @param name 指标名称<br>
	 * @param doubleValue   值<br>
	 * @param tagArray tag数组<br>
	 */
	public void updateGauge(String name, double doubleValue, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, MetricsType.GAUGE, doubleValue, 0, null, tagArray);
	}
	/**
	 * 更新SUMMARY指标的值，如果指标不存在，会自动注册。用于非回调方式的指标。<br>
	 * 注册时不会启动percentiles 功能。<br>
	 *
	 * @param name 指标名称<br>
	 * @param doubleValue 值<br>
	 * @param tagArray  tag数组<br>
	 */
	public void updateSummary(String name, double doubleValue, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, MetricsType.SUMMARY, doubleValue, 0, null, tagArray);
	}
	/**
	 * 更新TIMER指标的值，如果指标不存在，会自动注册。用于非回调方式的指标。<br>
	 * 注册时不会启动percentiles 功能。<br>
	 *
	 * @param name 指标名称<br>
	 * @param longValue 值<br>
	 * @param timeUnit  时间单位<br>
	 * @param tagArray  tag数组<br>
	 */
	public void updateTimer(String name, long longValue, TimeUnit timeUnit, String ...tagArray) {
		InternalMetricsHelper.update(registry, name, MetricsType.TIMER, 0, longValue, timeUnit, tagArray);
	}

	/**
	 *  注册一个支持percentiles的timer指标 <br/s>
	 *
	 * @param name
	 * @param percentiles
	 * @param tagArray
	 */
	public void registerTimer(String name, double [] percentiles, String ...tagArray) {
		InternalMetricsHelper.registerTimer(registry, name, percentiles, tagArray);
	}
}
