package com.sp.infra.svc.gov.sdk.util;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 * Description:数据工具类
 */
public class NumberUtil {

	
	
    private NumberUtil() {
	}

	/**
     * 把一个Object串转换成整数
     *
     * @param str 整型字符串
     * @return 整型数字
     */
    public static int toInt(Object str) {
        return toInt(str+"", 0);
    }

    /**
     * 把一个字符串转换成整数，如果字符串為空就返回指定整形值
     *
     * @param str 整型字符串
     * @param defaultValue 默認的整型值
     * @return 整型数字
     */
    public static int toInt(String str, int defaultValue) {
        if(StringUtils.empty(str)) {
            return defaultValue;
        }
        try {
            if(!isInteger(str)) {
                return defaultValue;
            }
            return toRawInt(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 判断一个字符串是否为int型
     *
     * @param str 被判断的字符串
     * @return =true 是int型的字符串 =false 不是int型的字符串
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch(Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 把一个字符串转换成整数
     *
     * @param str 要转换的字符串
     * @return 整数
     * @throws Exception 转换错误的返回值
     */
    public static int toRawInt(String str) throws Exception {
        return Integer.parseInt(str.trim());
    }

    /**
     * 把字符串转换为long型，转换失败就返回0L
     *
     * @param str 需要转换的字符串
     * @return 转换后的Long型数字
     */
    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    /**
     * 把字符串转换为long型，转换失败就返回指定缺省值
     *
     * @param str 需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @return 转换后的long型数字
     */
    public static long toLong(String str, long defaultValue) {
        if(StringUtils.empty(str)) {
            return defaultValue;
        }
        try {
            return toRawLong(str);
        } catch(Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 把字符串转换成long型
     *
     * @param str 需要转换的字符串
     * @return 转换后的long型数字
     * @throws Exception 转换失败错误
     */
    public static long toRawLong(String str) throws Exception {
        return Long.parseLong(str.trim());
    }
    
    /**
     * 计算n的数字。
     * n=1时，记为时间点0.
     * 
     * @param n
     * @param initInterval
     * @return
     */
	public static long getFibonacciInterval(int n, long initInterval) {
		if (n <= 1)
			return initInterval;
		long pre = 0, cur = initInterval, next = initInterval;
		for (int i = 2; i <= n; i++) {
			pre = cur;
			cur = next;
			next = pre + cur;
		}
		return cur;
	}
	
	public static int toInt(long v) {
		if (v >= Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (v <= Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		} else {
			return (int)v;
		}
	}
}