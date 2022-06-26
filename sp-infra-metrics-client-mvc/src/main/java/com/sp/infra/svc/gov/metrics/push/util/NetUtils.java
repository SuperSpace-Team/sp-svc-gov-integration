/**
 * 
 */
package com.sp.infra.svc.gov.metrics.push.util;

import java.lang.management.ManagementFactory;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luchao 2018-12-20
 *
 */
public class NetUtils {

	private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

	public static String getInstance() {
		return getExternalIp() + ":" + getPort();
	}
	
	public static String getExternalIp() {
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = ips.nextElement();
					if (ip instanceof Inet6Address)
						continue;
					if (!ip.isLoopbackAddress()) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error("failed to get ip. ", e);
		}

		return "";
	}
	

	public static String getPort() {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objs = mbs.queryNames(
					new ObjectName("*:type=Connector,*")
					, Query.match(Query.attr("protocol")
							, Query.value("HTTP/1.1")));
			
			for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
				ObjectName obj = i.next();
				String scheme = mbs.getAttribute(obj, "scheme").toString();
				if ("HTTP".equalsIgnoreCase(scheme)) {
					return obj.getKeyProperty("port");
				}
			}
		} catch (Exception e) {
			logger.error("failed to get port. ", e);
		}
		return "";
	}
}
