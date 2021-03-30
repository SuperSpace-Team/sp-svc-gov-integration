/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 * @author luchao 2018-12-19
 *
 */
public class TransformLogUnitCommand {
	private int code;
	private String srcKey;
	private String targetKey;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSrcKey() {
		return srcKey;
	}
	public void setSrcKey(String srcKey) {
		this.srcKey = srcKey;
	}
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}

}
