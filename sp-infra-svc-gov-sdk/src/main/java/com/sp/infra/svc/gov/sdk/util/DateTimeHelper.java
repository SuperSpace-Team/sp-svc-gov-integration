package com.sp.infra.svc.gov.sdk.util;

import com.sp.infra.svc.gov.sdk.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String MINUTE = "m";
	public static final String HOUR = "h";
	public static final String SECOND = "s";
	private static ThreadLocal<SimpleDateFormat> sdfMap = new ThreadLocal<SimpleDateFormat>();

	private static SimpleDateFormat get() {
		SimpleDateFormat sdf = sdfMap.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			sdfMap.set(sdf);
		}
		return sdf;
	}
	
	/**
	 * Convert from java.util.Date to String
	 *
	 * @param date
	 * @return String
	 */
	public static String toString(final Date date) {
		SimpleDateFormat sdf = get();
		return sdf.format(date);
	}

	/**
	 * 根据string， 使用默认的 格式pattern，取得一个Date
	 * 
	 * @param dateTime     time string in your format
	 * @return Date
	 */
	public static Date getDate(final String dateTime) {
		SimpleDateFormat sdf = get();
		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 解析  12m，   22h 这样格式的时间长度字符串。
	 * @param duration
	 * @return
	 */
	public static long getMillSeconds(final String duration) {
		return getSeconds(duration) * 1000;
	}
	/**
	 * 解析  12m，   22h 这样格式的时间长度字符串。
	 * @param duration
	 * @return
	 */
	public static long getSeconds(final String duration) {
		long sec = 0;
		String v = duration.substring(0,duration.length()-1);
		if (duration.endsWith(SECOND)) {
			sec = Long.valueOf(v);
		} else if (duration.endsWith(MINUTE)) {
			sec = Long.valueOf(v) * 60;
		} else if (duration.endsWith(HOUR)) {
			sec = Long.valueOf(v) * 60 * 60;
		} 
		return sec;
	}
	
	/**
	 * 当前时间 往前 num 毫秒。
	 * 
	 * @param num
	 * @return
	 */
	public static long getFutureTimestamp(long num) {
		return System.currentTimeMillis() + num;
	}
	public static long getFutureTimestamp(Date start, long num) {
		return start.getTime() + num;
	}

	public static long getFutureTimestamp(int num, TimeUnit tu) {
		return getFutureTimestamp(new Date(), num, tu);
	}
	public static long getFutureTimestamp(Date start, int num, TimeUnit tu) {
		switch (tu) {
		case DAYS: {
			return getFutureTimestamp(start, num * 24 * 60 * 60 * 1000L);
		}
		case HOURS: {
			return getFutureTimestamp(start, num * 60 * 60 * 1000L);
		}
		case MINUTES: {
			return getFutureTimestamp(start, num * 60 * 1000L);
		}
		case SECONDS: {
			return getFutureTimestamp(start, num * 1000L);
		}
		case MILLISECONDS: {
			return getFutureTimestamp(start, 1000);
		}
		default: {
			throw new BusinessException("unsupported time unit.");
		}
		}
	}
    public static String now() {
    	SimpleDateFormat sdf = get();
        return sdf.format(new Date());
    }

}
