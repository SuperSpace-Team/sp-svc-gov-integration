package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author luchao 2019-01-21
 *
 */
public class CollectionUtils {
		public static boolean isEmpty(Collection collection) {
			return (collection == null || collection.isEmpty());
		}
		public static boolean isEmpty(Map map) {
			return (map == null || map.isEmpty());
		}

		public static boolean isEmpty(Object[] array) {
			return (array == null || array.length == 0);
		}

		/**
		 * list->String
		 * [DEBUG, INFO, WARN, ERROR] ---->>>> "DEBUG,INFO,WARN,ERROR"
		 * @param list
		 * @return
		 */
		public static String List2String(List<String> list) {
		    StringBuilder result = new StringBuilder();
		    if (null != list && !list.isEmpty()) {
		        for(String elemet : list) {
		            result.append(SdkCommonConstant.SEPARATOR_COMMA);
		            result.append(elemet);
		        }
		        // 去掉第一个分隔符
		        result.deleteCharAt(0);
		    }
		    return result.toString();
		}
		
		/**
		 * String->list
         * "DEBUG,INFO,WARN,ERROR" ---->>>> [DEBUG, INFO, WARN, ERROR]
         * 
         * 
		 * @param elements
		 * @return
		 */
		public static List<String> String2List(String elements) {
		    List<String> result = new ArrayList<String>();
		    if (StringUtils.isNotBlank(elements)) {
		        String[] array = elements.split(SdkCommonConstant.SEPARATOR_COMMA); //正则
		        if(!CollectionUtils.isEmpty(array)) {
		            for(String element : array) {
		                result.add(element);
		            }
		        }
		    }
		    return result;
		}
		
}
