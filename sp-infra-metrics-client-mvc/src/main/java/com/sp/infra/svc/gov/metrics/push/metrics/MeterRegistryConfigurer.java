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
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configurer to apply customizers, {@link MeterFilter
 * filters}, {@link MeterBinder binders} and {@link Metrics#addRegistry global
 * registration} to {@link MeterRegistry meter registries}.
 *
 * @author Jon Schneider
 * @author Phillip Webb
 */
class MeterRegistryConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(MeterRegistryConfigurer.class);

	private final List<MeterBinder> binders;

	private final boolean addToGlobalRegistry;

	private final boolean hasCompositeMeterRegistry;

	MeterRegistryConfigurer(List<MeterBinder> binders, boolean addToGlobalRegistry, boolean hasCompositeMeterRegistry) {
		this.binders = binders;
		this.addToGlobalRegistry = addToGlobalRegistry;
		this.hasCompositeMeterRegistry = hasCompositeMeterRegistry;
	}

	void configure(MeterRegistry registry) {
		// Customizers must be applied before binders, as they may add custom
		// tags or alter timer or summary configuration.
		if (!this.hasCompositeMeterRegistry || registry instanceof CompositeMeterRegistry) {
			addBinders(registry);
		}
		if (this.addToGlobalRegistry && registry != Metrics.globalRegistry) {
			Metrics.addRegistry(registry);
		}
	}

	private void addBinders(MeterRegistry registry) {
		for (MeterBinder binder : binders) {
			try {
				binder.bindTo(registry);
			} catch (Exception e) {
				logger.error("{} bind registry error", binder.getClass().getName(), e);
			}
		}
	}
}
