/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luchao 2018-12-20
 *
 */
public class StringUtils {
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * @from common-lang3
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
    	return !isEmpty(cs);
    }

    /**
     * @from common-lang3
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 非空判断
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * 字符串补齐
     *
     * @param source     源字符串
     * @param fillLength 补齐长度
     * @param fillChar   补齐的字符
     * @param isLeftFill true为左补齐，false为右补齐
     * @return
     */
    public static String stringFill(String source, int fillLength, char fillChar, boolean isLeftFill) {
        if (source == null || source.length() >= fillLength)
            return source;

        StringBuilder result = new StringBuilder(fillLength);
        int len = fillLength - source.length();
        if (isLeftFill) {
            for (; len > 0; len--) {
                result.append(fillChar);
            }
            result.append(source);
        } else {
            result.append(source);
            for (; len > 0; len--) {
                result.append(fillChar);
            }
        }
        return result.toString();
    }


    /**
     * 判断非空字符串
     *
     * @date 2014-3-24 下午2:28:43
     * @return true：非空，false：null或者""
     */
    public static Boolean isNotBlank(Object obj) {
        boolean flag = false;
        if (obj instanceof String) {
            if (isNotBlank((String) obj)) {
                flag = true;
            }
        } else {
            if (null != obj) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 判断字符串是否为null或没有内容或全部为空格。
     *
     * @param inStr 被判断的输入参数
     * @return 布尔值：true=表示输入字符串为null或没有内容或全部为空格
     * false=表示输入字符串不为null或有内容或全部不为空格
     */
    public static boolean empty(String inStr) {
        return (zero(inStr) || (inStr.trim().equals("")));
    }

    /**
     * 判别字符串是否为null或者没有内容
     *
     * @param inStr 被判断的输入参数
     * @return 布尔值：true=表示输入字符串为null或者没有内容
     * false=表示输入字符串不为null或者有内容
     */
    public static boolean zero(String inStr) {
        return ((null == inStr) || (inStr.length() <= 0));
    }


    /**
     * @param str
     * @param ch
     * @return String 返回类型
     * @throws
     * @Title: subStrEndDiffStr
     * @Description: TODO 判断第一个字符串是否为第二个字符串结尾，并且去掉
     * @author hujiuzhou
     */
    public static String subStrEndDiffStr(String str, String ch) {
        str = toEmptySafe(str);
        ch = toEmptySafe(ch);
        return (str.endsWith(ch)) ? str.substring(0, str.length() - ch.length()) : str;
    }

    /**
     * 在str为null或者没有内容，或者全部为空格的情况下，返回空串；否则返回str
     *
     * @param inStr 被判断的输入参数
     * @return 字符串="" 表示输入字符串为null或者没有内容或者全部为空格
     * 字符串!="" 表示输入字符串有内容
     */
    public static String toEmptySafe(String inStr) {
        if (empty(inStr)) {
            return "";
        }
        return inStr;
    }


    public static String join(String separator, Object... items) {
        String tmpKey = join(items, separator);
        tmpKey = subStrEndDiffStr(tmpKey, separator);
        tmpKey = subStrStartDiffStr(tmpKey, separator);
        tmpKey = replace(tmpKey, separator + "+", separator);
        return tmpKey;
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                + separator.length());

//        StrBuilder buf = new StrBuilder(bufSize);
        //TODO 修改为StringBuilder

        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }


        return buf.toString();
    }


    /**
     * @param str
     * @param ch
     * @return String 返回类型
     * @throws
     * @Title: subStrStartDiffStr
     * @Description: TODO 判断第一个字符串是否为第二个字符串开始，并且去掉
     * @author hujiuzhou
     */
    public static String subStrStartDiffStr(String str, String ch) {
        str = str.trim();
        ch = ch.trim();
        return (str.startsWith(ch)) ? str.substring(ch.length()) : str;
    }


    /**
     * 在字符串中，用新的字符串替换指定的字符串
     *
     * @param src    需要替换的字符对象
     * @param strOld 被替换的字符串
     * @param strNew 用于替换的字符串
     * @return 已经被替换的字符串
     */
    public static String replace(String src, String strOld, String strNew) {
        if (null == src) {
            return src;
        }
        if (zero(strOld)) {
            return src;
        }
        if (null == strNew) {
            return src;
        }
        if (equals(strOld, strNew)) {
            return src;
        }

        return src.replaceAll(strOld, strNew);
    }


    /**
     * 判断字符串是否内容相同
     *
     * @param s1 第1个输入字符串
     * @param s2 第2个输入字符串
     * @return 布尔值=true：两个字符串相等
     * =false:两个字符串不相等
     */
    public static boolean equals(String s1, String s2) {
        if (null == s1) {
            return false;
        }
        return s1.equals(s2);
    }
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static long longValue(String v, long defaultValue) {
    	if (isEmpty(v) || ! isNumeric(v)) {
    		logger.warn("invalid string value for LONG type. {}", v);
    		return defaultValue;
    	}
    	try {
			return Long.valueOf(v);
		} catch (NumberFormatException e) {
			logger.error("invalid string format {}", v, e);
			return defaultValue;
		}
    }
	public static String getTransId() {
		StringBuffer st = new StringBuffer();
		st.append(System.currentTimeMillis()).append("-");
		st.append(Thread.currentThread().getId()).append("-");
		st.append(new Object().hashCode());
		return st.toString();
	}
}
