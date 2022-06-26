/**
 * 
 */
package com.sp.infra.svc.gov.sdk.command.cfg;

/**
 * @author luchao 2018-12-14
 *
 */
public class Entry {
	
	private Integer code;
	private String className;
	private String methodName;
	private String inputParamType;
	private String description;
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getInputParamType() {
		return inputParamType;
	}
	public void setInputParamType(String inputParamType) {
		this.inputParamType = inputParamType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Entry [code=" + code + ", className=" + className + ", methodName=" + methodName + ", inputParamType="
				+ inputParamType + ", description=" + description + "]";
	}
	
}
