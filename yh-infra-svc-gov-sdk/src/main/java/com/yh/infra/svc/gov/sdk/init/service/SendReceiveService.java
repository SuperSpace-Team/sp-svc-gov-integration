package com.yh.infra.svc.gov.sdk.init.service;

import com.yh.infra.svc.gov.sdk.auth.uac.UacService;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryReq;
import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 收发数据。目前仅用于发送 version checker request
 * 
 * @author luchao 2018-12-20
 *
 */
public class SendReceiveService {
    private static final Logger logger = LoggerFactory.getLogger(SendReceiveService.class);

    private AppRegContext context;
    private UacService uacService;
    
	public SendReceiveService(AppRegContext context, UacService uacService) {
		this.context = context;
		this.uacService = uacService;
	}

	/**
     * 发送版本查询请求并且读取返回的版本数据
     * 
     * @param req
     * @return
     */
	public VersionQueryResp send(VersionQueryReq req) {
		if(req == null || req.getCfgVersion() == null || req.getCfgVersion() == 0) {
			return null;
		}

		AppRegConfig config = context.getConfig();

		//调用版本检查(/version/query)接口验证
		String url = config.getGovPlatformUrl();
		if (StringUtils.isEmpty(url)) {
			logger.warn("URL of service governance server is invalid.");
			return null;
		}
		
		// 发送数据
		String msgBody = JsonUtil.writeValue(req);
		if (logger.isDebugEnabled()) {
			logger.debug("Send request to service governance server: {}, {}", url, msgBody);
		}

		Map<String, String> retMap = uacService.sendRequestWithToken(url, msgBody, SdkCommonConstant.PG_CONNECT_TIMEOUT);
		String tmp = retMap.get("error");
		String status = retMap.get("status");
		String result = retMap.get("result");
		if ((tmp != null) || (!SdkCommonConstant.HTTP_STATUS_OK.equals(status))) {
			logger.warn("Get error when post JSON request. app:{}, error:{}, status:{}, result:{}",
					req.getAppId(), tmp, status, result);
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Received data from  server : {}", result);
		}

		VersionQueryResp ret = JsonUtil.readValueSafe(result, VersionQueryResp.class);
		if (ret == null) {
			logger.warn("JSON parse failed. {} ", result);
			return null;
		}
		return ret.getCode() == null? null : ret;
	}
}
