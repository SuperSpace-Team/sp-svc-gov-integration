/**
 * 
 */
package com.sp.infra.svc.gov.sdk.init.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端发给服务端的版本查询请求对象POJO
 * 
 * @author luchao 2018-12-18
 *
 */
public class VersionQueryReq {
	private String appId;
	private Integer cfgVersion;
	private String sdkVersion;
	private String hostName;
	private String ip;
	private Map<String, String> reqData = new HashMap<>();
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getCfgVersion() {
		return cfgVersion;
	}
	public void setCfgVersion(Integer version) {
		this.cfgVersion = version;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map<String, String> getReqData() {
		return reqData;
	}
	
	@Override
	public String toString() {
		return "VersionQueryReq{" +
				"appId='" + appId + '\'' +
				", version=" + cfgVersion +
				", sdkVersion='" + sdkVersion + '\'' +
				", hostName='" + hostName + '\'' +
				", ip='" + ip + '\'' +
				'}';
	}
}
