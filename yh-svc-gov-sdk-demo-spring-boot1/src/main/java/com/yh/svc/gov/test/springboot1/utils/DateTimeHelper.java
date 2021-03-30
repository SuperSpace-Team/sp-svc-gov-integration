package com.yh.svc.gov.test.springboot1.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.yh.svc.gov.test.springboot1.exception.BusinessException;

/**
 * 时间日期类。<br/>
 * 用于替换static 情况下的 SimpleDateFormat. 因为它不是线程安全的。
 * 
 * @author luchao
 *
 */
public class DateTimeHelper {

	private static DateTimeFormatter fmtDefault = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static Long getTimestamp(String tsStr) {
		LocalDateTime ldt = LocalDateTime.parse(tsStr, fmtDefault);
		return ldt.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
	}

	public static String toString(final Date date) {
		final LocalDateTime localDateTime = getLocalDateTime(date);
		return localDateTime.format(fmtDefault);
	}

	/**
	 * 根据date，生成LocalDateTime
	 *
	 * @param date
	 * @return LocalDateTime
	 */
	public static LocalDateTime getLocalDateTime(final Date date) {
		final Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
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

	public static long getFutureTimestamp(int num, TimeUnit tu) {
		switch (tu) {
		case DAYS: {
			return getFutureTimestamp(num * 24 * 60 * 60 * 1000L);
		}
		case HOURS: {
			return getFutureTimestamp(num * 60 * 60 * 1000L);
		}
		case MINUTES: {
			return getFutureTimestamp(num * 60 * 1000L);
		}
		case SECONDS: {
			return getFutureTimestamp(num * 1000L);
		}
		case MILLISECONDS: {
			return getFutureTimestamp(1000);
		}
		default: {
			throw new BusinessException("unsupported time unit.");
		}
		}
	}

	/**
	 * 仅用于毫秒的增减。
	 *
	 * @param date
	 * @param millisecond
	 * @return
	 */
	public static Date addMillSeconds(Date date, int millisecond) {
		long ts = date.getTime();
		ts += millisecond;
		Date result = new Date();
		result.setTime(ts);
		return result;
	}

	/**
	 * 仅用于毫秒的增减。
	 *
	 * @param date
	 * @param millisecond
	 * @return
	 */
	public static Date addMinute(Date date, int minutes) {
		long ts = date.getTime();
		ts += minutes * 60 * 1000;
		Date result = new Date();
		result.setTime(ts);
		return result;
	}


	public static int minuteConvertToMs(int minute) {
		return minute * 60 * 1000;
	}
	public static String nowStr() {
		final LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(fmtDefault);
	}

	
}