package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonOauthUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * {@link OauthAssertion}具体实现 
 * @author wenjin.gao
 * @Date 2015年8月28日  上午11:40:18
 * @Version 
 * @Description 
 *
 */
public final class OauthAssertionImpl implements OauthAssertion {
	
	private static final long serialVersionUID = -3317757783727922268L;

	/** The date from which the assertion is valid. */
    private final Date validFromDate;

    /** The date the assertion is valid until. */
    private final Date validUntilDate;

    /** Map of key/value pairs associated with this assertion. I.e. authentication type. */
    private final Map<String, Object> attributes;

    /** The principal for which this assertion is valid for. */
    private final OauthAttributePrincipal principal;

	@Override
	public Date getValidFromDate() {
		return this.validFromDate;
	}
	
	public OauthAssertionImpl(final OauthAttributePrincipal principal, final Date validFromDate, final Date validUntilDate, final Map<String, Object> attributes) {
        this.principal = principal;
        this.validFromDate = validFromDate;
        this.validUntilDate = validUntilDate;
        this.attributes = attributes;

        CommonOauthUtils.assertNotNull(this.principal, "principal cannot be null.");
        CommonOauthUtils.assertNotNull(this.validFromDate, "validFromDate cannot be null.");
        CommonOauthUtils.assertNotNull(this.attributes, "attributes cannot be null.");
    }
	
	public OauthAssertionImpl(final OauthAttributePrincipal principal) {
        this(principal, new Date(), null, Collections.<String, Object>emptyMap());
    }
	
    public Date getValidUntilDate() {
        return this.validUntilDate;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public OauthAttributePrincipal getPrincipal() {
        return this.principal;
    }
	
}
