/**
 * 
 */
package com.sp.infra.svc.gov.agent.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;

/**
 * @author luchao 2018-12-20
 *
 */
public class StringUtils {
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}

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

	public static String getTransId() {
		StringBuffer st = new StringBuffer();
		st.append(System.currentTimeMillis()).append("-");
		st.append(Thread.currentThread().getId()).append("-");
		st.append(new Object().hashCode());
		return st.toString();
	}

	/**
	 * 切分字符串
	 * 
	 * @param cs
	 * @return
	 */
	public static String[] split(String inStr, String splitter) {
		if (isBlank(inStr))
			return null;

		String ret[] = inStr.split(splitter);
		for (int i = 0; i < ret.length; i++) {
			String s = ret[i];
			ret[i] = s.trim();
		}
		return ret;
	}

	/**
	 * 判断字符串相等
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean equals(String src, String target) {
		if ((src == null) || (target == null))
			return false;

		return src.equals(target);
	}

	public static String format(String message, Object... parameters) {
		try {
			int startSize = 0;
			int parametersIndex = 0;
			int index;
			String tmpMessage = message;
			while ((index = message.indexOf("{}", startSize)) != -1) {
				if (parametersIndex >= parameters.length) {
					break;
				}
				tmpMessage = tmpMessage.replaceFirst("\\{\\}",
						Matcher.quoteReplacement(String.valueOf(parameters[parametersIndex++])));
				startSize = index + 2;
			}
			return tmpMessage;
		} catch (Exception e) {
			e.printStackTrace(); //NOSONAR
			return message;
		}
	}
	public static String format(String message, Throwable exp) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bos);
			exp.printStackTrace(ps);
			bos.flush();
			bos.close();
			return message + bos.toString();
		} catch (Exception e) {
			e.printStackTrace(); //NOSONAR
			return message;
		}
	}
	
}
