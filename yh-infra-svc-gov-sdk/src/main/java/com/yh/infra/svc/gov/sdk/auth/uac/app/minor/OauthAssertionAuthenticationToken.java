package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

/**
 * 临时身份验证对象需要加载用户详细信息
 * @author wenjin.gao
 * @Date 2015年8月28日  上午11:43:59
 * @Version 
 * @Description 
 *
 */
public class OauthAssertionAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -8722047888482767687L;
	private final OauthAssertion assertion;

    public OauthAssertionAuthenticationToken(final OauthAssertion assertion) {
        super(new ArrayList<GrantedAuthority>());

        this.assertion = assertion;
    }

    public Object getPrincipal() {
        return this.assertion.getPrincipal().getName();
    }

    //因无证书，所以返回null
    public Object getCredentials() {
        return null;
    }

    public OauthAssertion getAssertion() {
        return this.assertion;
    }

}
