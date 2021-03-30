package com.yh.infra.svc.gov.sdk.auth.uac.app.init;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author: oo
 * @date: 2019/3/12 10:48
 */
public abstract class AbstractLoginSuccessHandler implements AuthenticationSuccessHandler {
    protected String defaultTargetUrl = "/";
}
