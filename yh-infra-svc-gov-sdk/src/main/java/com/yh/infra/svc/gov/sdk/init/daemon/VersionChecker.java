package com.yh.infra.svc.gov.sdk.init.daemon;

import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.callback.RequestHandler;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.init.service.ConfigService;
import com.yh.infra.svc.gov.sdk.init.service.SendReceiveService;
import com.yh.infra.svc.gov.sdk.util.CollectionUtils;
import com.yh.infra.svc.gov.sdk.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SDK版本检查器
 * @author luchao 2018-12-19
 *
 */
public class VersionChecker extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(VersionChecker.class);

	private AppRegContext context;
	private SendReceiveService sendReceiveService;
	private ConfigService configService;
	private boolean exit = false;

	public VersionChecker(AppRegContext context, SendReceiveService srs, ConfigService cs) {
		this.context = context;
		sendReceiveService = srs;
		configService = cs;
	}

	public void setExit() {
		this.exit = true;
	}

	@Override
	public void run() {
		while (!exit) {
			try {
				if (!initialized()) {
					//Sleep 1s后，重新检查
					ThreadUtil.sleep(1000);
					continue;
				}
				
				VersionQueryReq req = new VersionQueryReq();
				req.setAppId(context.getConfig().getAppKey());
				req.setHostName(context.getConfig().getHostName());
				req.setIp(context.getConfig().getIp());
				req.setCfgVersion(getCfgVersion());
				req.setSdkVersion(context.getConfig().getSdkVersion());
				
				List<RequestHandler> handlers = BeanRegistry.getInstance().getBeanList(RequestHandler.class);
				if (!CollectionUtils.isEmpty(handlers)) {
					//依次加载依赖组件的配置项KV
					for (RequestHandler handler : handlers) {
						req.getReqData().put(handler.getKey(), handler.getValue());
					}
				}
				
				VersionQueryResp resp = sendReceiveService.send(req);
				if (resp != null) {
					logger.info("Received data from service governance server.Update version. {}, {}",
							resp.getCode(), resp.getVersion());
					configService.updateVersion(resp);
				}
			} catch (Exception e) {
				logger.warn("Cannot update svc gov SDK version.", e);
			}

			for (int i = 0; i < context.getConfig().getVersionPullInterval(); i++) {
				ThreadUtil.sleep(1000);
				if (exit) {
					break;
				}

				if (logger.isDebugEnabled() && context.isNewCallback()) {
					logger.debug("Find NEW listener registered in service governance SDK! Current version:{}",
							context.getCurrentVersion());
				}
				// 如果有新的callback登记，停止sleep。立刻抓取新的 config。
				if ((context.getCurrentVersion() > 0) && context.isNewCallback()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Find NEW listener registered! Trigger another query from server side!");
					}

					break;
				}
			}
		}
	}
	
	private boolean initialized() {
		Boolean init = BeanRegistry.getInstance().<Boolean>getBean(SdkCommonConstant.SDK_INITIALIZED_FLAG);
		// 尚未初始化成功，不做任何操作
		if (init == null) {
			logger.info("SDK has not initialized, will sleep 1s.");
			ThreadUtil.sleep(1000);
			return false;
		}
		return true;
	}

	/**
	 * 获取配置信息的版本号
	 * @return
	 */
	private Integer getCfgVersion() {
		Integer ret = context.getCurrentVersion();
		//检查是否有新的listener/callback注册,如果有,强制刷新数据。
		if ((context.getCurrentVersion() > 0) && context.isNewCallback()) {
        	logger.info("Found new callback, reset version to -1.");
			ret = SdkCommonConstant.PG_VERSION_EMPTY_VERSION;

			//清除标记
			context.setNewCallback(false);
			return ret;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("NOT found new callback. use current version {}", context.getCurrentVersion());
		}

		return ret;
	}
}
