package com.yh.infra.svc.gov.metrics.binder;

import java.util.Collection;
import java.util.List;

import com.yh.infra.svc.gov.metrics.meter.InternalMetricsHelper;
import com.yh.infra.svc.gov.metrics.meter.MetricsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yh.infra.svc.gov.metrics.config.YhMetricsProperties;
import com.yh.infra.svc.gov.metrics.config.YhMetricsProperties.MeterProperty;
import com.yh.infra.svc.gov.metrics.constant.MetricsType;
import com.yh.infra.svc.gov.metrics.util.TagUtil;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;


/**
 * 被micrometer自动识别并绑定。
 * 
 * @author luchao  2020-12-18
 *
 */
@Component
public class BizMetricsBinder implements MeterBinder {
	private static final Logger logger = LoggerFactory.getLogger(BizMetricsBinder.class);
	
	@Autowired(required = false)
	private MetricsProvider curtomerProvider;
	
	@Autowired
	private YhMetricsProperties properties;
	
    @Override
    public void bindTo(MeterRegistry meterRegistry) {
    	if (CollectionUtils.isEmpty(properties.getMeters())) {
        	logger.info("NO metrics settings found. ");
        	return;
    	}
		if (curtomerProvider == null) {
			logger.error("no MetricsProvider instance found. cannot register metrics. ");
			return;
		}
   	
    	for (MeterProperty mc : properties.getMeters().values()) {
        	logger.info("begin to bind pre-defined metrics. {}", mc);
    		
        	// 1. 处理 dimensions  配置
        	List<Collection<Tag>> dimensions = TagUtil.buildDimensionTags(mc.getDimensions());
        	if (dimensions == null) {
        		logger.error("invalid tags setting. {}", mc.getKey());
        		continue;
        	}
        	
        	// 2. 处理 其他  配置
    		MetricsType type = MetricsType.valueOf(mc.getType().toUpperCase());
    		String name = mc.getKey();
    		
        	// 3. 注册
			if(CollectionUtils.isEmpty(dimensions)){
				logger.info("Register metrics: {}", mc.getKey());
				InternalMetricsHelper.register(
						meterRegistry,
						curtomerProvider,
						name,
						type,
						mc.getConfig(),
						null);
				return;
			}

    		for (Collection<Tag> tags : dimensions) {
				logger.info("Register metrics: {} with tag: {}", mc.getKey(), tags);

        	// 根据配置构建tag list
				InternalMetricsHelper.register(
						meterRegistry, 
						curtomerProvider, 
						name, 
						type,
						mc.getConfig(),
						tags);
    		}
		}
    }
}
