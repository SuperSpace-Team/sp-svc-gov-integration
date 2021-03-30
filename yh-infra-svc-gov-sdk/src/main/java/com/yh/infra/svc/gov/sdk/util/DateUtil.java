package com.yh.infra.svc.gov.sdk.util;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/14
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 正常
     */
    public static final Integer LIFECYCLE_NORMAL=1;

    /**
     * 禁用
     */
    public static final Integer LIFECYCLE_DISABLE=0;

    /**
     * 已删除
     */
    public static final Integer LIFECYCLE_DELETED=2;

    public static final String FORMAT_TIME ="yyyyMMdd";

    protected static final String DATE_FORMAT = "yyyy-MM-dd";

    protected static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    public static String date2String(java.util.Date date, String dateFormat) {
        return DateConvertUtils.format(date, dateFormat);
    }

    public static <T extends java.util.Date> T string2Date(String dateString, String dateFormat, Class<T> targetResultType) {
        return DateConvertUtils.parse(dateString, dateFormat, targetResultType);
    }
}