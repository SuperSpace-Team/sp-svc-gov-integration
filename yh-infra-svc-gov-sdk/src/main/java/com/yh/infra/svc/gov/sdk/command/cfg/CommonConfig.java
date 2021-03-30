/**
 * 
 */
package com.yh.infra.svc.gov.sdk.command.cfg;

/**
 * 日志管理
 * 
 * @author luchao 2018-12-14
 *
 */
public class CommonConfig {
	private String cfgKey;
	private String cfgValue;
	public String getCfgKey() {
		return cfgKey;
	}
	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}
	public String getCfgValue() {
		return cfgValue;
	}
	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}
	@Override
	public String toString() {
		return "CommonConfig [cfgKey=" + cfgKey + ", cfgValue=" + cfgValue + "]";
	}
	
}
