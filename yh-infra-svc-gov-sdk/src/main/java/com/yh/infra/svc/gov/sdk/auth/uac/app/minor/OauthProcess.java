package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CommonUserInfo;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.AESUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理登录回调
 * @author Alex Lu
 *
 */
public class OauthProcess {

//	private String appKey=null;
//	
//	private String secret=null;
	
	private static final Logger logger = LoggerFactory.getLogger(OauthProcess.class);

	/**
	 * 获取accessToken,通过HttpJsonClient.postJsonData工具类来调用cus的系统<br>
	 * 对传入参数进行加密
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	public AccessTokenCommand queryAccessToken(String code, String appKey, String secret) {
		// 用户登录成功后回调方法URL
		String url= UacSdkContext.getDomain()+"/oauth/encrypt/token";
		HashMap<String ,Object> param = new HashMap<String ,Object>();
		String secretStr = "";
		String codeStr = "";
		try{
			secretStr = AESUtil.parseByte2HexStr(AESUtil.encrypt(secret, UacSdkContext.getSecret()));
			codeStr = AESUtil.parseByte2HexStr(AESUtil.encrypt(code, UacSdkContext.getSecret()));
		}catch(Exception e){
			logger.error("secret 加密失败：", e);
		}
	    param.put("code", codeStr);
	    param.put("appKey", appKey);
	    param.put("secret", secretStr);
		String json= CommonUtil.postJsonData(url,param);
		System.out.println("queryAccessToken return json:" + json);
		AccessTokenCommand accesstokencommand=null;
		try{
			accesstokencommand=CommonUtil.readValue(json, AccessTokenCommand.class);
		}
		catch(Exception e){
			logger.error("queryAccessToken_1",e);
			logger.error("queryAccessToken_2"+json);
			throw new RuntimeException(e);
		}
		return accesstokencommand;
	}
	
	/**
	 * 通过accesstoken 获取用户信息
	 * HttpJsonClient.postJsonData
	 * @param accessToken
	 * @return
	 */
	public CommonUserInfo queryUserInfo(String accessToken){
	     //访问用户中心当前用户的信息URL
		String url= UacSdkContext.getDomain()+"/oauth/user-info";
		HashMap<String ,Object> param = new HashMap<String ,Object>();
		param.put("access_token", accessToken);
		String json=CommonUtil.postJsonData(url,param);
		CommonUserInfo commonuserinfo=CommonUtil.readValue(json, CommonUserInfo.class);
		return commonuserinfo;
	}
	
	/**
	 * 获取当前登录用户所有系统参数,通过HttpJsonClient.postJsonData工具类来调用cus的系统<br>
	 * 对传入参数进行加密
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Object> querySysParam(String userId,String appKey,String secret) {
		// 用户登录成功后回调方法URL
		String url= UacSdkContext.getDomain()+"/oauth/customization/sysParameter";
		HashMap<String ,Object> param = new HashMap<String ,Object>();
		String secretStr = "";
		String userIdStr = "";
		try{
			secretStr = AESUtil.parseByte2HexStr(AESUtil.encrypt(secret, UacSdkContext.getSecret()));
			userIdStr = AESUtil.parseByte2HexStr(AESUtil.encrypt(userId, UacSdkContext.getSecret()));
		}catch(Exception e){
			logger.error("secret 加密失败：", e);
		}
	    param.put("userId", userIdStr);
	    param.put("appKey", appKey);
	    param.put("secret", secretStr);
		String json=CommonUtil.postJsonData(url,param);
		
		Map<String, Object> map = null;
		if(StringUtils.hasText(json)) {
			map = JsonUtil.readValue(json, Map.class);
		}
		
		return map;
	}
}
