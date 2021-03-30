package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonOauthUtils;

import java.util.Collections;
import java.util.Map;

public class OauthAttributePrincipalImpl extends OauthSimplePrincipal implements OauthAttributePrincipal{

	private static final long serialVersionUID = -7144490552332173038L;
	/** Map of key/value pairs about this principal. */
    private final Map<String, Object> attributes;

    /**
     * Constructs a new principal with an empty map of attributes.
     *
     * @param name the unique identifier for the principal.
     */
    public OauthAttributePrincipalImpl(final String name) {
        this(name, Collections.<String, Object>emptyMap());
    }

    /**
     * Constructs a new principal witht he supplied name, attributes, and proxying capabilities.
     * @param name
     * @param attributes
     */
    public OauthAttributePrincipalImpl(final String name, final Map<String, Object> attributes) {
        super(name);
        this.attributes = attributes;
        CommonOauthUtils.assertNotNull(this.attributes, "attributes cannot be null.");
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
