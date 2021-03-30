/**
 * 
 */
package com.yh.infra.svc.gov.sdk.command.cfg;

/**
 * @author luchao 2018-12-14
 *
 */
public class BizFactor {
	
	private Integer code;
	private String bizAlias;
	private String name;
	private String description;
	private Integer volume;
	private Integer clientMode;
	private Integer serverMode;
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getBizAlias() {
		return bizAlias;
	}
	public void setBizAlias(String bizAlias) {
		this.bizAlias = bizAlias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public Integer getClientMode() {
		return clientMode;
	}
	public void setClientMode(Integer clientMode) {
		this.clientMode = clientMode;
	}
	public Integer getServerMode() {
		return serverMode;
	}
	public void setServerMode(Integer serverMode) {
		this.serverMode = serverMode;
	}
	@Override
	public String toString() {
		return "BizFactor [code=" + code + ", bizAlias=" + bizAlias + ", name=" + name + ", description=" + description + ", volume=" + volume
				+ ", clientMode=" + clientMode + ", serverMode=" + serverMode + "]";
	}
	
}
