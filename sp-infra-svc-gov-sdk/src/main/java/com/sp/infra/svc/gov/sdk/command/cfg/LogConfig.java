/**
 * 
 */
package com.sp.infra.svc.gov.sdk.command.cfg;

/**
 * 日志管理
 * 
 * @author luchao 2018-12-14
 *
 */
public class LogConfig {
	private String logType;
	private String fileUrl;
	
	
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Override
	public String toString() {
		return "LogConfig [logType=" + logType + ", fileUrl=" + fileUrl + "]";
	}

}
