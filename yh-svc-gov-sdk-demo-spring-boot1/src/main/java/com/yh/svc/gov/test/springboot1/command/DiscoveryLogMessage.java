/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

import java.util.ArrayList;
import java.util.List;

/**
 *一个探索任务的 日志。
 * 
 * 
 * @author luchao  2019-03-12
 *
 */
public class DiscoveryLogMessage extends LogMessage {
	
	private String appId;
	private String sdkVersion;
	private Integer cfgVersion;
	private int taskCode;   //探索任务code

	private List<DiscoveryLogUnitCommand> unitList = new ArrayList<DiscoveryLogUnitCommand>();
	
	public DiscoveryLogMessage() {}
	
	public DiscoveryLogMessage(int type) {
		super(type);
	}
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSdkVersion() {
		return sdkVersion;
	}
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}
	public Integer getCfgVersion() {
		return cfgVersion;
	}
	public void setCfgVersion(Integer cfgVersion) {
		this.cfgVersion = cfgVersion;
	}
	public List<DiscoveryLogUnitCommand> getUnitList() {
		return unitList;
	}
	public void setUnitList(List<DiscoveryLogUnitCommand> unitList) {
		this.unitList = unitList;
	}
	public int getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(int taskCode) {
		this.taskCode = taskCode;
	}
}
