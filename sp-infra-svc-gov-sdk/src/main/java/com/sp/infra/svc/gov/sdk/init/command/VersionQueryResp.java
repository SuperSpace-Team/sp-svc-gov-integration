/**
 * 
 */
package com.sp.infra.svc.gov.sdk.init.command;

/**
 * 客户端发给服务端的版本查询请求的响应对象POJO
 * 
 * @author luchao 2018-12-18
 *
 */
public class VersionQueryResp {
	private Integer code;
	private Integer version;
	private String config;
	private String runtime;
	private String control;

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

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	@Override
	public String toString() {
		return "VersionQueryResp [code=" + code + ", version=" + version + ", config=" + config + ", runtime=" + runtime
				+ ", control=" + control + "]";
	}
}
