/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 *
 */
public abstract class LogMessage {
	private int logType;

	public LogMessage() {}
	
	LogMessage(int type) {
		logType = type;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}
	
}
