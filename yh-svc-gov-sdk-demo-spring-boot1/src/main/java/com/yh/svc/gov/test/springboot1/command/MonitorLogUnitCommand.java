/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 * @author luchao 2018-12-19
 *
 */
public class MonitorLogUnitCommand {
	private int code;
	private String key;
	private String inLog;
	private String outLog;
	private String exceptionLog;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInLog() {
		return inLog;
	}
	public void setInLog(String inLog) {
		this.inLog = inLog;
	}
	public String getOutLog() {
		return outLog;
	}
	public void setOutLog(String outLog) {
		this.outLog = outLog;
	}
	public String getExceptionLog() {
		return exceptionLog;
	}
	public void setExceptionLog(String exceptionLog) {
		this.exceptionLog = exceptionLog;
	}
}
