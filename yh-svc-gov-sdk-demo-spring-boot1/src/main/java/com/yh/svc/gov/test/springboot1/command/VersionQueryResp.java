/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 * 客户端发给服务端的版本查询请求  的 响应。
 * 
 * @author luchao 2018-12-18
 *
 */
public class VersionQueryResp {
	private Integer code;
	private Integer version;
	private String config;
	private String runtime;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	@Override
	public String toString() {
		return "VersionQueryResp [code=" + code + ", version=" + version + ", config=" + config + ", runtime=" + runtime + "]";
	}
	
}
