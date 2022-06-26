/**
 * 
 */
package com.sp.infra.svc.gov.sdk.command.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个应用的全部的配置信息。
 * 
 * @author luchao 2018-12-14
 *
 */
public class AppConfig {
	private String appId;
	private Integer version;
	private String schemaVersion;
	private Boolean lifecycle;
	private List<Entry> entries = new ArrayList<Entry>();
	private MonitorConfig monitor = new MonitorConfig();
	private List<LogConfig> logCfgs = new ArrayList<LogConfig>();
	private List<CommonConfig> commonCfgs = new ArrayList<CommonConfig>();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getSchemaVersion() {
		return schemaVersion;
	}

	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	public Boolean getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Boolean lifecycle) {
		this.lifecycle = lifecycle;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public MonitorConfig getMonitor() {
		return monitor;
	}

	public void setMonitor(MonitorConfig monitor) {
		this.monitor = monitor;
	}

	public List<LogConfig> getLogCfgs() {
		return logCfgs;
	}

	public void setLogCfgs(List<LogConfig> logCfgs) {
		this.logCfgs = logCfgs;
	}

	public List<CommonConfig> getCommonCfgs() {
		return commonCfgs;
	}

	public void setCommonCfgs(List<CommonConfig> commonCfgs) {
		this.commonCfgs = commonCfgs;
	}

}
