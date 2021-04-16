/**
 * 
 */
package com.yh.infra.svc.gov.metrics.meter;

import java.util.Arrays;
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
 * 1) 所有的方法中，如果需要提供tags，必须提供完成的tags。 因为metrics的区分是通过 name + tags 来区分的， 不完整的tags会导致:
 * a)异常, b)定位到错误的metrics。
 * 2) tag必须是偶数个， 因为格式“key1, value1, key2, value2, ....”。系统会提取成为  key1=value1, key2=value2,...
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
			return Tags.empty();
		if ((args.length & 1) == 1) {
			logger.warn("Tag argument number is invalid. {}", Arrays.asList(args));
			return Tags.empty();
		}

		//检查tag是否有效
		for(int i = 0; i < args.length ; i++) {
			if (args[i] == null) {
				// 因为从0开始，所以偶数的 元素其实是 奇数的   位置， 就是key。
				if ((i & 1) == 0) {
					//说明tag为null，无法继续， 返回empty tags
					logger.warn("tag's key is null. {}", Arrays.asList(args));
					return Tags.empty();
				}
				logger.warn("tag'value contains null. will use 'null' instead. {}", Arrays.asList(args));
				break;
			}
		}
		// 如果是null,更换为空字符串。
		for(int i = 1; i < args.length ; ) {
			if (args[i] == null) {
				args[i] = "";
			}
			i += 2;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("build tags: {}", Arrays.asList(args));
		}

		return Tags.of(args);
	}

	/**
	 * 检查meter是否已经存在， 如果存在，返回true
	 *
	 * @param registry
	 * @param name
	 * @return
	 */
	public static boolean isExist(MeterRegistry registry, String name,Tags tags) {
		return (registry.find(name).tags(tags).meter() != null);
	}

	/**
	 * 注册指标，基于自定义回调方法。即回调对象及其方法是自定义的，不使用MetricsProvider <br>
	 * 本方法仅用对于COUNTER/GAUGE/TIMER <br>
	 *
	 * @param <T>
	 * @param registry
	 * @param name
	 * @param type
	 * @param obj
	 * @param f
	 * @param fl
	 * @param timeunit
	 * @param tagArray
	 * @return
	 */
	public static <T> Meter register(MeterRegistry registry, String name, MetricsType type, T obj, ToDoubleFunction<T> f, ToLongFunction<T> fl, TimeUnit timeunit, String ...tagArray) {

		Tags tags = convertAndMerge(tagArray);
		if (isExist(registry, name, tags)) {
			logger.error("metrics exist. cannot re-register. {}, {}, {}", name, type, Arrays.asList(tagArray));
			return null;
		}
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
			default: {
				logger.error("unknown metrics type: {}, {}, [{}]", name, type, tagArray);
				break;
			}
		}
		return ret;
	}

	/**
	 * 注册指标， 基于标准回调接口 MetricsProvider。
	 * 本方法仅用于COUNTER/GAUGE/TIMER <br>
	 *
	 *
	 * @param registry
	 * @param meter
	 * @param name
	 * @param type
	 * @param configMap
	 * @param tags
	 * @return
	 */
	public static Meter register(MeterRegistry registry, MetricsProvider meter, String name, MetricsType type, Map<String, String> configMap , Iterable<Tag> tags) {

		if (isExist(registry, name, Tags.of(tags))) {
			logger.error("metrics exist. cannot re-register. {}, {}, [{}]", name, type, tags);
			return null;
		}
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
						.register(registry);

				break;
			}
			default: {
				logger.error("unknown metrics type: {}, {}, {}", name, type,tags);
				break;
			}
		}
		return ret;
	}
	/**
	 * 注册指标， 基于标准回调接口 MetricsProvider
	 * 本方法仅用于COUNTER/GAUGE/TIMER <br>
	 * 可以指定时间单位。
	 *
	 *
	 * @param registry
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
	 * 注册非回调方式的指标。<br>
	 *
	 * name： 指标名称<br>
	 * type：  指标类型<br>
	 * timeUnit： 用于timer指标，非Timer指标时不用，可为null<br>
	 * percentiles: 是否对TIMER/SUMMARY开启percentiles计算。 开启后会有 0.5/0.9/0.99三个数据。本功能使用客户端的计算资源。
	 * tagArray： tag数组 <br>
	 *
	 *
	 *
	 * @param registry
	 * @param name
	 * @param type
	 * @param timeUnit
	 * @param tagArray
	 */
	public static void register(MeterRegistry registry, String name, MetricsType type, String ...tagArray) {

		Tags tags = convertAndMerge(tagArray);
		if (isExist(registry, name, tags)) {
			logger.error("metrics exist. cannot re-register. {}, {}, {}", name, type, Arrays.asList(tagArray));
			return ;
		}

		try {

			switch (type) {
				case COUNTER: {
					Counter.builder(name).tags(tags).register(registry);
					break;
				}
				case GAUGE: {
					Gauge.builder(name, new Double(0), Double::doubleValue).strongReference(true).tags(tags).register(registry);
					break;
				}
				case TIMER: {
					Timer.builder(name).tags(tags).register(registry);
					break;
				}
				case SUMMARY: {
					DistributionSummary.builder(name).tags(tags).register(registry);
					break;
				}
				default: {
					logger.error("unknown metrics type: {}, {}, [{}]", name, type, tagArray);
					break;
				}
			}
		} catch (Exception e) {
			logger.error("register metrics failed. {}, {} ", name, Arrays.asList(tagArray), e);
		}
	}

	/**
	 * 更新指标的值，如果指标不存在，会自动注册。用于非回调方式的指标。<br>
	 * 注意，自动注册TIMER/SUMMARY时，不会启动percentiles 功能。
	 *
	 * name： 指标名称<br>
	 * type：  指标类型<br>
	 * doubleValue： 用于除timer外的其他指标<br>
	 * longValue： 用于timer 指标，非Timer指标时不用。<br>
	 * timeUnit： 用于timer指标，非Timer指标时不用，可为null<br>
	 * tagArray： tag数组 <br>
	 *
	 *
	 * @param registry
	 * @param name
	 * @param type
	 * @param doubleValue
	 * @param longValue
	 * @param timeUnit
	 * @param tagArray
	 */
	public static void update(MeterRegistry registry, String name, MetricsType type, double doubleValue, long longValue, TimeUnit timeUnit, String ...tagArray) {
		try {
			Tags tags = convertAndMerge(tagArray);
			switch (type) {
				case COUNTER: {
					Counter counter = registry.counter(name, tags);
					counter.increment(doubleValue);
					break;
				}
				case GAUGE: {
					Gauge.builder(name, new Double(doubleValue), Double::doubleValue).strongReference(true).tags(tags).register(registry);
//				registry.gauge(name, tags , new Double(doubleValue));
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
		} catch (Exception e) {
			logger.error("update metrics failed. {}, {} ", name, Arrays.asList(tagArray), e);
		}
	}

	/**
	 *  注册一个支持percentiles的timer指标 <br/s>
	 *
	 * @param registry
	 * @param name
	 * @param percentiles
	 * @param tagArray
	 */
	public static void registerTimer(MeterRegistry registry, String name, double [] percentiles, String ...tagArray) {
		Tags tags = convertAndMerge(tagArray);

		if (isExist(registry, name, tags)) {
			logger.debug("timer metrics exist. cannot re-register. {}, {}", name, Arrays.asList(tagArray));
			return ;
		}

		try {
			Timer.Builder t = Timer.builder(name).tags(tags);
			if (percentiles != null && percentiles.length > 0)
				t = t.publishPercentiles(percentiles);
			t.register(registry);
		} catch (Exception e) {
			logger.error("register metrics failed. {}, {} ", name, Arrays.asList(tagArray), e);
		}
	}
}
