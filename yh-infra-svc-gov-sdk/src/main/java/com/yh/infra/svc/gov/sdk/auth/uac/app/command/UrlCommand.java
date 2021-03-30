package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class UrlCommand implements Serializable {

	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2016年4月29日 上午10:32:13
	 */
	private static final long serialVersionUID = -6511504509908763488L;

	private Long id;
	private String url;
	private Long appId;
	private String appName;
	/**
	 * id的获取.
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设定id的值.
	 * @param Long
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * url的获取.
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设定url的值.
	 * @param String
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * appId的获取.
	 * @return Long
	 */
	public Long getAppId() {
		return appId;
	}
	/**
	 * 设定appId的值.
	 * @param Long
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	/**
	 * appName的获取.
	 * @return String
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 设定appName的值.
	 * @param String
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
