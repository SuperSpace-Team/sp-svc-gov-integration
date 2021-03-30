/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 *一个探索任务的 日志。
 * 
 * 
 * @author luchao  2019-03-12
 *
 */
public class DiscoveryLogUnitCommand {
	private String bizKey;	// 业务关键字
	private String className; //类全路径名
	private String methodName; //方法名，不含类名
	private String parameters; // 参数列表， 逗号分隔。每个参数都是类简名
	private String transId;    // trans Id
	private int transSeq;      // 在trans Id中的调用序号
	private String bizKeyPos;  // 关键词在 参数中的  层次和位置。 采用spring el的格式。
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public int getTransSeq() {
		return transSeq;
	}
	public void setTransSeq(int transSeq) {
		this.transSeq = transSeq;
	}
	public String getBizKeyPos() {
		return bizKeyPos;
	}
	public void setBizKeyPos(String bizKeyPos) {
		this.bizKeyPos = bizKeyPos;
	}
	
	
	
}
