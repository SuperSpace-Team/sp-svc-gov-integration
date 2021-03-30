/**
 * 
 */
package com.yh.svc.gov.test.springboot1.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author luchao 2018-12-19
 *
 */
public class ThreadUtil {
	public static int getPid() {
	    RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
	    String name = runtime.getName(); // format: "pid@hostname"
	    try {
	        return Integer.parseInt(name.substring(0, name.indexOf('@')));
	    } catch (Exception e) {
	        return -1;
	    }
	}
	public static long getThreadId() {
		return Thread.currentThread().getId();
	}
	
	public static void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	public static void sleep(int seconds, BoolCallback check) {
		try {
			for (int i = 0; i < seconds; i++) {
				Thread.sleep(1000);
				if (check.call())
					break;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
}
