/**
 * 
 */
package com.sp.infra.svc.gov.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author luchao 2018-12-20
 *
 */
public class NetUtils
{
	
	private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);
	
	public static String getLocalhostName()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch (final UnknownHostException e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getExternalIp()
	{
		try
		{
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements())
			{
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements())
				{
					ip = ips.nextElement();
					if (ip instanceof Inet6Address)
						continue;
					if (!ip.isLoopbackAddress())
					{
						return ip.getHostAddress();
					}
				}
			}
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static InetAddress getInetAddress()
	{
		try
		{
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			ArrayList<InetAddress> ipv4Result = new ArrayList<InetAddress>();
			ArrayList<InetAddress> ipv6Result = new ArrayList<InetAddress>();
			while (enumeration.hasMoreElements())
			{
				final NetworkInterface networkInterface = enumeration.nextElement();
				final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
				while (en.hasMoreElements())
				{
					final InetAddress address = en.nextElement();
					if (!address.isLoopbackAddress())
					{
						if (address instanceof Inet6Address)
						{
							ipv6Result.add(address);
						}
						else
						{
							ipv4Result.add(address);
						}
					}
				}
			}
			
			// prefer ipv4
			if (!ipv4Result.isEmpty())
			{
				for (InetAddress inetAddress : ipv4Result)
				{
					if (inetAddress.getHostAddress().startsWith("127.0") || inetAddress.getHostAddress().startsWith("192.168"))
					{
						continue;
					}
					return inetAddress;
				}
				return ipv4Result.get(ipv4Result.size() - 1);
			}
			else if (!ipv6Result.isEmpty())
			{
				return ipv6Result.get(0);
			}
			//If failed to find,fall back to localhost
			final InetAddress localHost = InetAddress.getLocalHost();
			return localHost;
		}
		catch (Exception e)
		{
			logger.error("Failed to obtain local address", e);
		}
		
		return null;
	}
	
}
