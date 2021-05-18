package com.yh.infra.svc.gov.sdk.auth.uac;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AppLoginReqCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.GetAppAuthCodeReqCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RefreshNewTokenReqCommand;
import com.yh.infra.svc.gov.sdk.command.BaseResponseEntity;
import com.yh.infra.svc.gov.sdk.config.AppRegConfig;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.init.context.AppRegContext;
import com.yh.infra.svc.gov.sdk.init.context.BeanRegistry;
import com.yh.infra.svc.gov.sdk.net.HttpClientProxy;
import com.yh.infra.svc.gov.sdk.util.AesAuthUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * UAC接口调用服务
 * @author luchao 2018-12-20
 *
 */
public class UacService {


	private static final Logger logger = LoggerFactory.getLogger(UacService.class);

	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

	private AppRegContext context = null;

	/**
	 * UAC传入的token
	 */
	private AccessTokenCommand appTokenRespInfo = null;
	private String appTokenStr = null;

	/**
	 * 是否需要更新token
	 */
	private boolean reLoginFlag = false;

	public UacService(AppRegContext ctx) {
		context = ctx;
	}

	/**
	 * 从治理服务端获取应用鉴权码
	 * 
	 * startURL 从哪里获取
	 * @param startUrl
	 * @param appKey
	 * @return
	 */
	private String getAppAuthCode(String startUrl, String appKey) {
		if(StringUtils.isBlank(startUrl) || StringUtils.isBlank(appKey)){
			return null;
		}

		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);
		String encryptStr = AesAuthUtil.encrypt(JsonUtil.writeValue(new GetAppAuthCodeReqCommand(appKey)));

		Map<String, String> retMap = httpClient.postJson(
				startUrl + SdkCommonConstant.INVOKE_SVC_GOV_API_GET_APP_AUTH_CODE,
					encryptStr, SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		String tmp = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (tmp != null) {
			// 系统异常
			logger.warn("Get app auth code failed! {}", retMap);
			return null;
		}

		tmp = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (!SdkCommonConstant.HTTP_STATUS_OK.equals(tmp)) {
			logger.warn("Fetch app auth code result : {}", retMap);
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Get the app auth code result: {} ", retMap);
		}

		BaseResponseEntity responseVO = JsonUtil.readValueSafe(retMap.get(
				SdkCommonConstant.RESP_KEY_RESULT), BaseResponseEntity.class);
		if (responseVO == null) {
			logger.warn("Parse BaseResponseEntity of app auth code failed! {} ",
					retMap.get(SdkCommonConstant.RESP_KEY_RESULT));
			return null;
		}

		return String.valueOf(responseVO.getData());
	}

	/**
	 * 应用登录服务治理认证
	 * 根据应用鉴权码获取应用鉴权Token
	 * @param startUrl
	 * @param appKey
	 * @param appSecret
	 * @param appAuthCode
	 * @return
	 */
	private AccessTokenCommand appLogin(String startUrl, String appKey, String appSecret, String appAuthCode) {
		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);

		AppLoginReqCommand appLoginReqCommand = new AppLoginReqCommand(appKey, appSecret, appAuthCode);
		String reqParamStr = JsonUtil.writeValue(appLoginReqCommand);
		if (logger.isDebugEnabled()) {
			logger.debug("App login parameter string: {}", reqParamStr);
		}

		String encryptStr = AesAuthUtil.encrypt(reqParamStr);

		//请求治理平台基础服务应用登录并返回Token信息(并转为HttpClientResponse(error,result,status属性)模型)
		Map<String, String> retMap = httpClient.postJson(
				startUrl + SdkCommonConstant.INVOKE_SVC_GOV_API_EXEC_APP_LOGIN, encryptStr,
					SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		String retInfo = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (StringUtils.isNotBlank(retInfo)) {
			//系统异常
			logger.warn("Cannot get the app token! {}", retMap);
			return null;
		}

		retInfo = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (!SdkCommonConstant.HTTP_STATUS_OK.equals(retInfo)) {
			logger.warn("App login auth result: {}", retMap);
			return null;
		}

		BaseResponseEntity responseVO = JsonUtil.readValueSafe(
				retMap.get(SdkCommonConstant.RESP_KEY_RESULT), BaseResponseEntity.class);
		if (responseVO == null) {
			logger.warn("Parse BaseResponseEntity of app login failed. {} ", retMap);
			return null;
		}

		if (responseVO.getIsSuccess() && responseVO.getData() != null) {
			AccessTokenCommand appTokenCmd = (AccessTokenCommand)responseVO.getData();
			if (appTokenCmd == null) {
				logger.warn("Parse app token info failed. {} ",
						JsonUtil.writeValue(responseVO.getData()));
				return null;
			}

			logger.info("Service governance app successfully logged in. App token:{}.",
					appTokenCmd.toString());
			return appTokenCmd;
		}

		return null;
	}

	/**
	 * 更新token
	 * 成功后，会延长token的过期时间。或者另外拿到一个token
	 * 
	 * @param startUrl
	 * @param appKey
	 * @param appSecret
	 * @return
	 */
	private boolean refreshToken(String startUrl, String appKey, String appSecret) {
		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);

		RefreshNewTokenReqCommand reqCommand = new RefreshNewTokenReqCommand(appKey, appSecret);
		String encryptStr = AesAuthUtil.encrypt(JsonUtil.writeValue(reqCommand));

		Map<String, String> retMap = httpClient.postJson(
				startUrl + SdkCommonConstant.INVOKE_SVC_GOV_API_REFRESH_NEW_APP_TOKEN, encryptStr,
					SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		if (logger.isDebugEnabled()) {
			logger.debug("Get refresh app token response result: {}", retMap);
		}

		String retInfoTmp = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (StringUtils.isNotBlank(retInfoTmp)) {
			//系统异常
			logger.warn("System error: {} ", retMap);
			return false;
		}

		retInfoTmp = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (SdkCommonConstant.HTTP_STATUS_OK.equals(retInfoTmp)) {
			BaseResponseEntity responseVO = JsonUtil.readValueSafe(
					retMap.get(SdkCommonConstant.RESP_KEY_RESULT), BaseResponseEntity.class);
			if (responseVO == null) {
				logger.warn("Parse BaseResponseEntity of refresh app token failed! {} ", retMap);
				return false;
			}

			if (responseVO.getIsSuccess() && responseVO.getData() != null) {
				logger.info("Successfully refreshed app token.New app token {}.", responseVO.getData());

				AccessTokenCommand appTokenCmd = (AccessTokenCommand)responseVO.getData();
				appTokenRespInfo.setAccessToken(appTokenCmd.getAccessToken());
				appTokenRespInfo.setExpireTime(appTokenCmd.getExpireTime());
				return true;
			}
		}

		logger.warn("refresh app token failed. result : {}", retMap);
		return false;
	}

	/**
	 * 重置token,强制登录
	 */
	public void resetToken() {
		WriteLock lock = rwLock.writeLock();
		try {
			lock.lock();
			if (logger.isDebugEnabled()) {
				logger.debug("Request to reset app token {}", appTokenRespInfo);
			}

			reLoginFlag = true;
		}finally {
			lock.unlock();
		}
	}

	/**
	 * 获取应用端鉴权Token
	 * @return
	 */
	public String getAppToken() {
		ReadLock lock = rwLock.readLock();
		try {
			lock.lock();
			boolean valid = true;

			if ((appTokenRespInfo == null) || reLoginFlag) {
				valid = false;
			} else {
				//提前30分钟,进行refresh
				long now = System.currentTimeMillis() + SdkCommonConstant.PG_APP_TOKEN_REFRESH_IN_ADVANCE;
				if (now >= appTokenRespInfo.getExpireTime()) {
					valid = false;
				}
			}

			if (!valid) {
				refreshToken();
			}
		} finally {
			lock.unlock();
		}

		return appTokenStr;
	}

	/**
	 * 更新或者login获取应用鉴权token,不再检查时间戳
	 */
	private void refreshToken() {
		WriteLock wlock = rwLock.writeLock();
		ReadLock rlock = rwLock.readLock();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Begin to refresh token. reLoginFlag: {}, token:{}", reLoginFlag, appTokenRespInfo);
		}

		try {
			//lock不能升级，即不能从read -> write，所以只能先把当前的read释放掉，然后尝试 加write。
			rlock.unlock();
			wlock.lock();
			AppRegConfig config = context.getConfig();

			// 如果需要更新token,token清空
			if (reLoginFlag) {
				appTokenRespInfo = null;
			}

			if (appTokenRespInfo != null) {
				if (refreshToken(config.getAppAuthUrl(), config.getAppKey(), config.getAppSecret())) {
					//有token,且refresh 成功,直接退出
					return;
				}

				// refresh 失败， token清空。
				appTokenRespInfo = null;
			}
			//字符串清空
			appTokenStr = null;
			
			//没有token，或者refresh失败，需要login
			String code = getAppAuthCode(config.getAppAuthUrl(), config.getAppKey());
			if (StringUtils.isEmpty(code)) {
				return;
			}

			appTokenRespInfo = appLogin(config.getUnionGatewayUrl(), config.getAppKey(), config.getAppSecret(), code);
		} catch (Exception e) {
			logger.warn("System error when refresh token.", e);
		} finally {
			// 强制login标志复位。 但此时有可能仍然没有token。要等下次 gettoken来重试了。
			reLoginFlag = false;
			
			// 更新 token 字符串。
			if (appTokenRespInfo != null) {
				appTokenStr = appTokenRespInfo.getAccessToken();
			}

			wlock.unlock();

			// rlock.lock是需要的， 因为lock/unlock是成对出现的， 外面还要执行一个unlock。所以这必须有个lock
			rlock.lock();
		}
	}
	
	public Map<String, String> sendRequestWithToken(String url, String content, int timeout) {
		String token = getAppToken();
		if (StringUtils.isEmpty(token)) {
			logger.warn("Cannot get app token. App key:{}", context.getConfig().getAppKey());

			Map<String, String> resultMap = new HashMap<>(16);
			resultMap.put("status", SdkCommonConstant.PG_CANNOT_GET_TOKEN);
			resultMap.put("error_code", SdkCommonConstant.HTTP_STATUS_SYSTEM_ERROR);
			return resultMap;
		}

		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);
		Header[] headers = { new BasicHeader("Authorization", token) };
		Map<String, String> resultMap = httpClient.postJson(url, content, timeout, headers);
		
		//只要不是200,一律尝试解析数据
		if (!SdkCommonConstant.HTTP_STATUS_OK.equals(resultMap.get("status"))) {
			Map bodyMap = JsonUtil.readValueSafe(resultMap.get("result"), Map.class);
			if (bodyMap != null && bodyMap.containsKey("error_code")) {
				//看看是不是 强制重新登录标记60001
				String ec = String.valueOf(bodyMap.get("error_code"));
				if (SdkCommonConstant.PG_UAC_FORCE_TO_LOGIN.equals(ec)) {
					logger.warn("Received a response asking for re-login. {}, {}", url, token);
			        resetToken();
				}
			}
		}
		return resultMap;
	}
	
	public Map<String, String> uacSendRequestWithToken(String url, Map<String, Object> params, int timeout) {
		params.put("token", getAppToken()) ;
		return sendRequestWithToken(url, JsonUtil.writeValue(params),timeout);
	}
}
