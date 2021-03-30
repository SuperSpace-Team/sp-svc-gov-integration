package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OauthSingleSignOutHttpSessionListener implements HttpSessionListener {
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	/**
	 * 登出处理
	 */
	@Override
	public void sessionDestroyed(final HttpSessionEvent se) {
		final HttpSession session = se.getSession();
		
	}
	
}
