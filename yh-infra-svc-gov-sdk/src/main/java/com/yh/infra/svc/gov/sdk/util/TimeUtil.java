package com.yh.infra.svc.gov.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 * Description:时间类工具类
 */
public class TimeUtil {

    /**
     * 存放各种时间格式的集合自定义
     */
    private static Map<String, Object> theFastTimeFormatterMap = new HashMap<String, Object>();

    /**
     * 是否使用存放各种时间格式的集合自定义标志
     */
    private static final boolean useFastDateFormatter = true;

    
    private TimeUtil() {
	}


    /**
     *按照format格式，把date对象转换成字符串
     *
     * @param date 需要转换的时间对象
     * @param format 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Date date, String format) {
        if(null == date) {
            return "";
        }
        if(StringUtils.empty(format)) {
            format = "yyyy-MM-dd";
        }
        return toString(date, getTimeFormatter(format));
    }

    /**
     *按照formator格式，把date对象转换成字符串
     *
     * @param date 需要转换的时间对象
     * @param formator 转换的时间格式
     * @return 时间对象的格式字符串
     */
    public static String toString(Date date, DateFormat formator) {
        if(null == date) {
            return "";
        }
        return formator.format(date);
    }

    /**
     * 日期/时间格式化子类对象的实现
     *
     * @param format 时间格式
     * @return 日期/时间格式化子类对象
     */
    public static final DateFormat getTimeFormatter(String format) {
        if(useFastDateFormatter) {
            DateFormat sdf = (DateFormat) theFastTimeFormatterMap.get(format);
            if(sdf != null) {
                return sdf;
            }
        }

        return new SimpleDateFormat(format);
    }
}