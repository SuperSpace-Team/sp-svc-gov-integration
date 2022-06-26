/**
 * 
 */
package com.sp.infra.svc.gov.sdk.init.context;

import com.sp.infra.svc.gov.sdk.config.AppRegConfig;
import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;

/**
 * @description: 应用注册上下文
 * @author: luchao
 * @date: Created in 2021/3/23 9:42 下午
 */
public class AppRegContext {

	/**
	 * 配置信息
	 */
	private volatile AppRegConfig config = null;
	private volatile String configJson = null;

	/**
	 * 自系统启动后，是否成功向server端注册，并取回数据。
	 */
	private volatile int currentVersion = SdkCommonConstant.PG_VERSION_INITIAL_VERSION;

	private volatile boolean newCallback = false;
	
	public AppRegContext(AppRegConfig cfg) {
		this.config = cfg;
		configJson = JsonUtil.writeValue(cfg);
	}


	/**
	 * 版本数据管理相关
	 * @return
	 */
	public int getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(int currentVersion) {
		this.currentVersion = currentVersion;
	}


	/**
	 * 配置信息
	 * @return
	 */
	public AppRegConfig getConfig() {
		return config;
	}
	

	public String getConfigJson() {
		return configJson;
	}


	/**********
	 * UAC 相关
	 ***********************************************************************/
	/**
	 * 轮询线程 检查版本时，会用到登录，单线程，不需要线程同步处理
	 * 
	 */
	public boolean isNewCallback() {
		return newCallback;
	}

	public void setNewCallback(boolean newCallback) {
		this.newCallback = newCallback;
	}
}
