package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public class OauthAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

	private static final long serialVersionUID = -5459273186939363675L;
	
	private final Object principal;
    private final UserDetails userDetails;
    private final int keyHash;
    private final OauthAssertion assertion;

	public Object getCredentials() {
		return null;
	}
    
    public OauthAuthenticationToken(final String key, final Object principal, //final Object credentials,
                                    final Collection<? extends GrantedAuthority> authorities, final UserDetails userDetails, final OauthAssertion assertion) {
        super(authorities);

        if ((key == null) || ("".equals(key)) || (principal == null) || "".equals(principal) //|| (credentials == null)
            ||  (authorities == null) || (userDetails == null) || (assertion == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.keyHash = key.hashCode();
        this.principal = principal;
        this.userDetails = userDetails;
        this.assertion = assertion;
        setAuthenticated(true);
    }

    //~ Methods ========================================================================================================

    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        if (obj instanceof OauthAuthenticationToken) {
        	OauthAuthenticationToken test = (OauthAuthenticationToken) obj;

            if (!this.assertion.equals(test.getAssertion())) {
                return false;
            }

            if (this.getKeyHash() != test.getKeyHash()) {
                return false;
            }

            return true;
        }

        return false;
    }

    public int getKeyHash() {
        return this.keyHash;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public OauthAssertion getAssertion() {
        return this.assertion;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" Assertion: ").append(this.assertion);
        return (sb.toString());
    }

}
