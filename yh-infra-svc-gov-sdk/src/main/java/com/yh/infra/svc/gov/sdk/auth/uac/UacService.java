package com.yh.infra.svc.gov.sdk.auth.uac;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.command.AccountAuthReturnObj;
import com.yh.infra.svc.gov.sdk.command.RefreshToken;
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
	private AccessTokenCommand uacToken = null;
	private String uacTokenStr = null;

	/**
	 * 是否需要更新token
	 */
	private boolean reLoginFlag = false;

	public UacService(AppRegContext ctx) {
		context = ctx;
	}

	/**
	 * 取得随机数
	 * 
	 * 
	 * @param startUrl
	 * @param appId
	 * @return
	 */
	private String getRandomCode(String startUrl, String appId) {
		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);

		String url = startUrl + "/appmember/member/encrypt/code";
		String paramsStr = "{\"appkey\" : \"" + appId + "\"}";
		String encryptStr = AesAuthUtil.encrypt(paramsStr);

		Map<String, String> retMap = httpClient.postJson(url, encryptStr,
				SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		String tmp = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (tmp != null) {
			// 系统异常
			logger.warn("get ramdom code fail. {}", retMap);
			return null;
		}
		tmp = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (SdkCommonConstant.HTTP_STATUS_OK.equals(tmp)) {
			if (logger.isDebugEnabled())
				logger.debug("Get the result: {} ", retMap);

			AccountAuthReturnObj aaro = JsonUtil.readValueSafe(retMap.get(
					SdkCommonConstant.RESP_KEY_RESULT), AccountAuthReturnObj.class);
			if (aaro == null) {
				logger.warn("parse AccountAuthReturnObj of random code fail. {} ",
						retMap.get(SdkCommonConstant.RESP_KEY_RESULT));
				return null;
			}
			return aaro.getData();
		}
		logger.warn("result : {}", retMap);
		return null;
	}

	/**
	 * 取得token
	 * @param startUrl
	 * @param appId
	 * @param secret
	 * @param code
	 * @return
	 */
	private AccessTokenCommand appLogin(String startUrl, String appId, String secret, String code) {
		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);

		String url = startUrl + "/appmember/member/appLogin";
		String paramsStr = String.format("{\"appkey\" : \"%s\", \"secret\" : \"%s\", \"code\" : \"%s\"}", appId, secret,
				code);
		if (logger.isDebugEnabled())
			logger.debug("parameter string is : {}", paramsStr);
		String encryptStr = AesAuthUtil.encrypt(paramsStr);

		Map<String, String> retMap = httpClient.postJson(url, encryptStr,
				SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		String tmp = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (tmp != null) {
			// 系统异常
			logger.warn("cannot get the token. {}", retMap);
			return null;
		}
		tmp = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (SdkCommonConstant.HTTP_STATUS_OK.equals(tmp)) {
			AccountAuthReturnObj authObj = JsonUtil.readValueSafe(
					retMap.get(SdkCommonConstant.RESP_KEY_RESULT), AccountAuthReturnObj.class);
			if (authObj == null) {
				logger.warn("parse AccountAuthReturnObj of appLogin fail. {} ", retMap);
				return null;
			}
			if (authObj.isResultFlag()) {
				AccessTokenCommand token = JsonUtil.readValue(authObj.getData(), AccessTokenCommand.class);
				if (token == null) {
					logger.warn("parse AccessTokenCommand fail. {} ", authObj.getData());
					return null;
				}
				logger.info("successfully logged in. get token {}.", token.toString());
				return token;
			}
		}
		logger.warn("result : {}", retMap);
		return null;
	}

	/**
	 * 更新token。成功后，会延长token的过期时间。或者另外拿到一个token。
	 * 
	 * 
	 * @param startUrl
	 * @param appId
	 * @param secret
	 * @return
	 */
	private boolean refreshToken(String startUrl, String appId, String secret) {
		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);

		String url = startUrl + "/appmember/member/refreshNewToken";
		String paramsStr = String.format("{\"appkey\" : \"%s\", \"secret\" : \"%s\"}", appId, secret);
		String encryptStr = AesAuthUtil.encrypt(paramsStr);

		Map<String, String> retMap = httpClient.postJson(url, encryptStr,
				SdkCommonConstant.PG_CONNECT_TIMEOUT, null);
		if (logger.isDebugEnabled()) 
			logger.debug("Get refresh response: {}", retMap);
		String tmp = retMap.get(SdkCommonConstant.RESP_KEY_ERROR);
		if (tmp != null) {
			// 系统异常
			logger.warn("system error. {} ", retMap);
			return false;
		}

		tmp = retMap.get(SdkCommonConstant.RESP_KEY_STATUS);
		if (SdkCommonConstant.HTTP_STATUS_OK.equals(tmp)) {
			AccountAuthReturnObj authObj = JsonUtil.readValueSafe(retMap.get(SdkCommonConstant.RESP_KEY_RESULT), AccountAuthReturnObj.class);
			if (authObj == null) {
				logger.warn("parse AccountAuthReturnObj of refreshAppToken fail. {} ", retMap);
				return false;
			}
			if (authObj.isResultFlag()) {
				String respdata = authObj.getData();
				logger.info("successfully refreshed. get token {}.", respdata);

				RefreshToken rt = JsonUtil.readValueSafe(respdata, RefreshToken.class);
				uacToken.setAccessToken(rt.getToken());
				uacToken.setExpireTime(rt.getExpireDate());
				return true;
			}
		}
		logger.warn("refreshToken fail. result : {}", retMap);
		return false;
	}

	/**
	 * 重置token，强制登录。
	 */
	public void resetToken() {
		WriteLock lock = rwLock.writeLock();
		lock.lock();
		if (logger.isDebugEnabled())
			logger.debug("request to reset token {}", uacToken);
		reLoginFlag = true;
		lock.unlock();
	}
	
	public String getToken() {
		ReadLock lock = rwLock.readLock();
		try {
			lock.lock();
			boolean valid = true;
			if ((uacToken == null) || reLoginFlag) {
				valid = false;
			} else {
				//提前30分钟,进行refresh
				long now = System.currentTimeMillis() + SdkCommonConstant.PG_UAC_REFRESH_IN_ADVANCE;
				if (now >= uacToken.getExpireTime()) {
					valid = false;
				}
			}
			if (!valid) {
				refreshToken();
			}
		} finally {
			lock.unlock();
		}

		return uacTokenStr;
	}

	/**
	 * 更新或者login获取token。不再检查时间戳了。
	 */
	private void refreshToken() {
		WriteLock wlock = rwLock.writeLock();
		ReadLock rlock = rwLock.readLock();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Begin to refresh token. reLoginFlag: {}, token:{}", reLoginFlag, uacToken);
		}

		try {
			//lock不能升级，即不能从read -> write，所以只能先把当前的read释放掉，然后尝试 加write。
			rlock.unlock();
			wlock.lock();
			AppRegConfig config = context.getConfig();

			// 如果需要relogin,token清空
			if (reLoginFlag) {
				uacToken = null;
			}

			if (uacToken != null) {
				if (refreshToken(config.getUacUrl(), config.getAppKey(), config.getAppSecret())) {
					//有token,且refresh 成功,直接退出
					return;
				}

				// refresh 失败， token清空。
				uacToken = null;
			}
			//字符串清空
			uacTokenStr = null;
			
			//没有token，或者refresh失败，需要login
			String code = getRandomCode(config.getUacUrl(), config.getAppKey());
			if (StringUtils.isEmpty(code))
				return;
			uacToken = appLogin(config.getUacUrl(), config.getAppKey(), config.getAppSecret(), code);
		} catch (Exception e) {
			logger.warn("system error when refresh token.", e);
		} finally {
			// 强制login标志复位。 但此时有可能仍然没有token。要等下次 gettoken来重试了。
			reLoginFlag = false;
			
			// 更新 token 字符串。
			if (uacToken != null)
				uacTokenStr = uacToken.getAccessToken();
			
			wlock.unlock();
			// rlock.lock是需要的， 因为lock/unlock是成对出现的， 外面还要执行一个unlock。所以这必须有个lock
			rlock.lock();
		}
	}
	
	public Map<String, String> sendRequestWithToken(String url, String content, int timeout) {
		String token = getToken();
		if (StringUtils.isEmpty(token)) {
			logger.warn("Cannot get token. {}", context.getConfig().getAppKey());
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("status", SdkCommonConstant.PG_CANNOT_GET_TOKEN);
			resultMap.put("error_code", SdkCommonConstant.HTTP_STATUS_SYSTEM_ERROR);
			return resultMap;
		}

		BeanRegistry sc = BeanRegistry.getInstance();
		HttpClientProxy httpClient = sc.getBean(HttpClientProxy.class);
		Header headers[] = new Header[1];
		headers[0] = new BasicHeader("Authorization", token);
		Map<String, String> resultMap = httpClient.postJson(url, content, timeout, headers);
		
		//只要不是200， 一律尝试解析数据。
		if (! SdkCommonConstant.HTTP_STATUS_OK.equals(resultMap.get("status"))) {
			Map bodymap = JsonUtil.readValueSafe(resultMap.get("result"), Map.class);
			if (bodymap != null && bodymap.containsKey("error_code")) {
				//看看是不是 强制重新登录标记60001
				String ec = String.valueOf(bodymap.get("error_code"));
				if (SdkCommonConstant.PG_UAC_FORCE_TO_LOGIN.equals(ec)) {
					logger.warn("Received a response asking for relogin. {}, {}", url, token);
			        resetToken();
				}
			}
		}
		return resultMap;
	}
	
	public Map<String, String> uacSendRequestWithToken(String url, Map<String, Object> params, int timeout) {
		params.put("token", getToken()) ;
		return sendRequestWithToken(url, JsonUtil.writeValue(params),timeout);
	}
}
