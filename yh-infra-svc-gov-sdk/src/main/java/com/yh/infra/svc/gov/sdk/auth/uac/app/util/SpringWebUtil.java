package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SpringWebUtil {

	/**
	 * request的获取.
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes != null) {
			request = ((ServletRequestAttributes)requestAttributes).getRequest();
		}
		
		return request;
	}
}
