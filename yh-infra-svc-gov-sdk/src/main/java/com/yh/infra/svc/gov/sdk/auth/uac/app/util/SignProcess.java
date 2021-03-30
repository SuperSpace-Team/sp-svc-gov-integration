package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import com.yh.common.utilities.EncryptUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;

/**
 * 签名处理
 * @author Justin Hu
 *
 */
public class SignProcess {
	
	/**
	 * 获取单个对象值
	 * @param obj
	 * @return
	 */
	private static String queryObject(Object obj){
	
		if(obj instanceof Number){
			
			return ((Number)obj).toString();
			
		}
		else if(obj instanceof String){
			
			return (String)obj;
		}
		
		return null;
	}
	
	/**
	 * 获取多个对象串起来的字符串
	 * @param objects
	 * @return
	 */
	public static String queryObjects(Object... objects){
		StringBuffer sb=new StringBuffer();
		for(Object obj:objects){
			sb.append(queryObject(obj));
		}
		
		return sb.toString();
	}
	
	/**
	 * objects中的参数只能是基本对象，如String,Integer等
	 * 生成签名
	 * @param objects 参数列表
	 * @return
	 */
	public static String makeSign(Object[] objects){
		
		String value=queryObjects(objects);
		
		return CommonUtil.hash(value, UacSdkContext.getSecret());
	}
	
	/**
	 * 验证签名是否正确
	 * @param sign 签名
	 * @param objects 
	 * @return
	 */
	public static boolean checkSign(String sign, Object[] objects){
		
		String value=queryObjects(objects);
		String signNow=CommonUtil.hash(value, "12345678");
		
		if(signNow.equals(sign)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 验证签名是否正确
	 * @param sign 签名
	 * @param objects 
	 * @return
	 */
	public static boolean checkSign(String sign, Object[] objects, String secret){
		
		String value=queryObjects(objects);
		String signNow= EncryptUtil.getInstance().hash(value, secret);
		
		if(signNow.equals(sign)){
			return true;
		}
		else{
			return false;
		}
	}
}
