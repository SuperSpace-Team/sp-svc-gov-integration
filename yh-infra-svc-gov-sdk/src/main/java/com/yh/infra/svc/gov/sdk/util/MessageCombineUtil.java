/*
 * TaskMessageUtil.java Created On 2017年12月19日
 * Copyright(c) 2017 Mazan Inc.
 * ALL Rights Reserved.
 */
package com.yh.infra.svc.gov.sdk.util;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * MessageUtil
 * 日志消息处理
 * @time: 下午6:02:14
 * @author mazan
 */
public class MessageCombineUtil {

	/**
	 * 换行符
	 */
	public static final char wrap = '\n';

	private MessageCombineUtil() {
	}

	/**
	 * 判断一段字符串是否包含换行符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isWrap(String str) {
		char end = str.charAt(str.length() - 1);
		return end == wrap;
	}
	
	/**
	 * 处理空值
	 * @param message
	 * @return
	 */
	public static String handleNull(String message) {
		return message == null? "" : message;
	}
	/**
	 * 在原有基础上拼接目标字符串,新字符串不需要换行
	 * eg：
	 * ("aaa\n", "bbb") ====> "aaa\nbbb"
	 * ("aaa", "bbb") ====> "aaa\nbbb"
	 * 
	 * ("aaa", "") ====> "aaa"
	 * ("", "bbb") ====> "bbb"
	 * ("", "") ====> ""
	 * 
	 * @param existMessage
	 * @param newMessage  
	 * @return
	 * @desc 之前updateTaskLog的时候已经把数据库中的内容取出来放到了内存中，现在又要写回来，造成了日志重复，故而第一次写入数据库的时候process为空
	 */
	public static String combindMessageWithWarp(String existMessage, String newMessage) {
		String result = "";
		existMessage = handleNull(existMessage);
		newMessage = handleNull(newMessage);
		//
		if(StringUtils.hasText(existMessage) && StringUtils.hasText(newMessage)) {
			if (isWrap(existMessage)) {
				result = existMessage + "" + newMessage;
			} else {
				result = existMessage + wrap + newMessage;
			}
		} else if (StringUtils.hasText(existMessage) && !StringUtils.hasText(newMessage)) {
			result = existMessage;
		} else if (!StringUtils.hasText(existMessage) && StringUtils.hasText(newMessage)) {
			result = newMessage;
		} else if (!StringUtils.hasText(existMessage) && !StringUtils.hasText(newMessage)) {
			result = "";
		}
		
		return result;
	}
	/**
	 * 合并消息,在每个消息体后面添加换行符\n
	 * @param messageList
	 * @return
	 */
	public static String combindMessage(List<String> messageList) {
		//合并 异常信息
		StringBuilder message = new StringBuilder();
		if(null != messageList && messageList.size() > 0) {
			for (String string : messageList) {
				if (isWrap(string)) {
					message.append(string + "");
				} else {
					message.append(string + wrap);
				}
			}
		}
		return message.toString();
	}
	

	/**
	 * 合并消息
	 * @param messageArray
	 * @return
	 */
	public static String combindMessage(String[] messageArray) {
		//合并 异常信息
		StringBuilder message = new StringBuilder();
		if(null != messageArray && messageArray.length > 0) {
			for (String string : messageArray) {
				if (isWrap(string)) {
					message.append(string + "");
				} else {
					message.append(string + wrap);
				}
			}
		}
		return message.toString();
	}
	
	/** 
	 * 转义正则特殊字符 （$()*+.[]?\^{},|） 
	 *  
	 * @param keyword 
	 * @return 
	 */  
	public static String escapeExprSpecialWord(String keyword) {  
	    if (!StringUtils.isEmpty(keyword)) {  
	        String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
	        for (String key : fbsArr) {  
	            if (keyword.contains(key)) {  
	                keyword = keyword.replace(key, "\\" + key);  
	            }  
	        }  
	    }  
	    return keyword;  
	} 
	/**
	 * 截取字符串，保留最大长度
	 * @param message
	 * @param max
	 * @return
	 */
    public static String LimitMaxLength(String message, Integer max) {
        if (StringUtils.hasText(message) && message.length() > max) {
            return message.substring(0, max);
        }
        return message;
    }

}
