/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sp.infra.svc.gov.metrics.push.metrics;

import java.util.List;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

/**
 * {@link BeanPostProcessor} that delegates to a lazily created
 * {@link MeterRegistryConfigurer} to post-process {@link MeterRegistry} beans.
 *
 * @author Jon Schneider
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
public class MeterRegistryPostProcessor implements BeanPostProcessor {

	private final List<MeterBinder> meterBinders;

	private volatile MeterRegistryConfigurer configurer;

	private final ApplicationContext applicationContext;

	public MeterRegistryPostProcessor(List<MeterBinder> meterBinders, ApplicationContext applicationContext) {
		this.meterBinders = meterBinders;
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MeterRegistry) {
			getConfigurer().configure((MeterRegistry) bean);
		}
		return bean;
	}

	private MeterRegistryConfigurer getConfigurer() {
		if (this.configurer == null) {
			boolean hasCompositeMeterRegistry = this.applicationContext
					.getBeanNamesForType(CompositeMeterRegistry.class, false, false).length != 0;
			this.configurer = new MeterRegistryConfigurer(this.meterBinders, true, hasCompositeMeterRegistry);
		}
		return this.configurer;
	}

}
