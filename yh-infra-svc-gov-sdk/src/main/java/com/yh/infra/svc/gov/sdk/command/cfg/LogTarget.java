/**
 * 
 */
package com.yh.infra.svc.gov.sdk.command.cfg;

/**
 * 监控日志写入目标
 * 
 * @author luchao 2018-12-14
 *
 */
public class LogTarget {
	private Integer bizCode;
	private Boolean logFile;
	private Boolean logArch;
	private String level;


	public Integer getBizCode() {
		return bizCode;
	}
	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}
	public Boolean getLogFile() {
		return logFile;
	}
	public void setLogFile(Boolean logFile) {
		this.logFile = logFile;
	}
	public Boolean getLogArch() {
		return logArch;
	}
	public void setLogArch(Boolean logArch) {
		this.logArch = logArch;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "LogTarget [bizCode=" + bizCode + ", logFile=" + logFile + ", logArch=" + logArch + ", level=" + level
				+ "]";
	}
	

}
