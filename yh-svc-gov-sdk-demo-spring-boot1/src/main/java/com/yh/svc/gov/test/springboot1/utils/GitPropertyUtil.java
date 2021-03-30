/**
 * 
 */
package com.yh.svc.gov.test.springboot1.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luchao  2019-04-24
 *
 */
public class GitPropertyUtil
{
	private static final Logger logger = LoggerFactory.getLogger(GitPropertyUtil.class);
	public static final String GIT_FILE = "/META-INF/com.yh.svc.gov.sdk.demo/yh-svc-gov-sdk-demo-spring-boot1/git.properties";
	
	
	
	private GitPropertyUtil() {
	}



	public static Properties loadProps()
	{
		Properties props = new Properties();
		InputStream in = null;
		try
		{
			in = Thread.currentThread().getClass().getResourceAsStream(GIT_FILE);
			
			if (in == null) {
				logger.error("cannot find git.properties. {}", GIT_FILE);
				return props;
			}
			props.load(in);
		}
		catch (FileNotFoundException e)
		{
			logger.error("cannot find git.properties.", e);
		}
		catch (Exception e)
		{
			logger.error("system error. ", e);
		}
		finally
		{
			try
			{
				if (null != in)
				{
					in.close();
				}
			}
			catch (IOException e)
			{
				logger.error("git.properties文件流关闭出现异常");
			}
		}
		return props;
	}
}
