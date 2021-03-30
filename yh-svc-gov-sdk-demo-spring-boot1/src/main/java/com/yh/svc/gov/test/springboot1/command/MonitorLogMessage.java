/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

import java.util.List;

/**
 * 只能放针对一个entry的的调用的日志，全链路监控 日志。
 * 
 * @author luchao  2019-03-12
 *
 */
public class MonitorLogMessage extends LogMessage {
	private String appId;
	private String sdkVersion;
	private Integer cfgVersion;
	private Integer bizCode;
	private List<MonitorLogUnitCommand> monitorLogList;
	private List<TransformLogUnitCommand> transformLogList;
	private long timeStamp;
	private long elaspedTime; 
	private String threadName;
	private String hostName;
	private String ip;
	
	public MonitorLogMessage() {}
	
	public MonitorLogMessage(int type) {
		super(type);
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public Integer getBizCode() {
		return bizCode;
	}
	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}
	
	public List<MonitorLogUnitCommand> getMonitorLogList() {
		return monitorLogList;
	}
	public void setMonitorLogList(List<MonitorLogUnitCommand> monitorLogList) {
		this.monitorLogList = monitorLogList;
	}
	public List<TransformLogUnitCommand> getTransformLogList() {
		return transformLogList;
	}
	public void setTransformLogList(List<TransformLogUnitCommand> transformLogList) {
		this.transformLogList = transformLogList;
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
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getElaspedTime() {
		return elaspedTime;
	}
	public void setElaspedTime(long elaspedTime) {
		this.elaspedTime = elaspedTime;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
