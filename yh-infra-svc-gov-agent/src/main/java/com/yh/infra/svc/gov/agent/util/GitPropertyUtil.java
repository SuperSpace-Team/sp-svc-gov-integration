/**
 * 
 */
package com.yh.infra.svc.gov.agent.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author luchao  2019-04-24
 *
 */
public class GitPropertyUtil
{
	private static final Logger logger = Logger.getLogger(GitPropertyUtil.class.getName());
	public static final String GIT_FILE = "/META-INF/com.yh.infra.svc.gov.gsdk/g-agent/agent-git.properties";
	
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
				logger.severe("cannot find agent-git.properties. " + GIT_FILE);
				return props;
			}
			props.load(in);
		}
		catch (FileNotFoundException e)
		{
			logger.severe("cannot find agent-git.properties." + e.getMessage());
		}
		catch (Exception e)
		{
			logger.severe("system error. " + e.getMessage());
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
				logger.severe("agent-git.properties文件流关闭出现异常");
			}
		}
		return props;
	}
}
