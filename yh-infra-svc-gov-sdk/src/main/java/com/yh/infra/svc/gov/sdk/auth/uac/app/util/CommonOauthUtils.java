package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonOauthUtils {
	
	private  static final Logger LOG = LoggerFactory.getLogger(CommonOauthUtils.class);

	private CommonOauthUtils() {
		throw new IllegalStateException("commonOauthUtils class unable to instance");
	}

	public static String constructRedirectUrl(final String ServerLoginUrl, final String serviceParameterName, final String serviceUrl) {
		StringBuilder buffer = new StringBuilder();
		boolean paramFlag = false;
		if(CommonUtil.isEmpty(ServerLoginUrl)){
			buffer.append(serviceUrl);
			paramFlag = serviceUrl.indexOf("?") != -1;
		}else{
			buffer.append(ServerLoginUrl);
			paramFlag = ServerLoginUrl.indexOf("?") != -1;
		}
		if(!CommonUtil.isEmpty(serviceParameterName)){
			if(paramFlag){
				buffer.append("&");
			}else{
				buffer.append("?");
			}
			buffer.append("code=" + serviceParameterName);
		}
		
        return buffer.toString();
    }

	public static String constructServiceUrl(final HttpServletRequest request, final HttpServletResponse response,
                                             final String service, final String serverName, final String artifactParameterName, final boolean encode) {
		if (CommonUtil.isNotBlank(service)) {
			return encode ? response.encodeURL(service) : service;
		}

		final StringBuilder buffer = new StringBuilder();

		if (!serverName.startsWith("https://") && !serverName.startsWith("http://")) {
			buffer.append(request.isSecure() ? "https://" : "http://");
		}

		buffer.append(serverName);
		buffer.append(request.getRequestURI());

		if (CommonUtil.isNotBlank(request.getQueryString())) {
			final int location = request.getQueryString().indexOf(artifactParameterName + "=");

			if (location == 0) {
				final String returnValue = encode ? response.encodeURL(buffer.toString()) : buffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("serviceUrl generated: " + returnValue);
				}
				return returnValue;
			}

			buffer.append("?");

			if (location == -1) {
				buffer.append(request.getQueryString());
			} else if (location > 0) {
				final int actualLocation = request.getQueryString().indexOf("&" + artifactParameterName + "=");

				if (actualLocation == -1) {
					buffer.append(request.getQueryString());
				} else if (actualLocation > 0) {
					buffer.append(request.getQueryString().substring(0, actualLocation));
				}
			}
		}

		final String returnValue = encode ? response.encodeURL(buffer.toString()) : buffer.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("serviceUrl generated: " + returnValue);
		}
		return returnValue;
	}
	
	public static void assertNotNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
	
	

}
