/**
 * 
 */
package com.yh.infra.svc.gov.metrics.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author luchao  2020-12-07
 *
 */
public class BzMetricsProperties {
	private Map<String, MeterProperty> meters = new HashMap<>();
	
	public Map<String, MeterProperty> getMeters() {
		return meters;
	}
	public void setMeters(Map<String, MeterProperty> meters) {
		this.meters = meters;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetricsConfig [meters=");
		builder.append(meters);
		builder.append("]");
		return builder.toString();
	}



	public static class MeterProperty {
		private String key;
		private String type;
		private boolean callback = false;
		private List<String> dimensions = new ArrayList<>();
		private Map<String, String> config = new HashMap<>();

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isCallback() {
			return callback;
		}

		public void setCallback(boolean callback) {
			this.callback = callback;
		}

		public List<String> getDimensions() {
			return dimensions;
		}

		public void setDimensions(List<String> dimensions) {
			this.dimensions = dimensions;
		}

		public Map<String, String> getConfig() {
			return config;
		}

		public void setConfig(Map<String, String> config) {
			this.config = config;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MeterProperty [key=");
			builder.append(key);
			builder.append(", type=");
			builder.append(type);
			builder.append(", callback=");
			builder.append(callback);
			builder.append(", dimensions=");
			builder.append(dimensions);
			builder.append(", config=");
			builder.append(config);
			builder.append("]");
			return builder.toString();
		}

	}

}
