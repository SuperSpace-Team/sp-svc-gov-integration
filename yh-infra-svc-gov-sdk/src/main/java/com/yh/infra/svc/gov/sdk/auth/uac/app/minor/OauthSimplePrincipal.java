package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonOauthUtils;

import java.io.Serializable;
import java.security.Principal;

public class OauthSimplePrincipal implements Principal, Serializable {

	private static final long serialVersionUID = -3305938147610234953L;
	
	/** The unique identifier for this principal. */
    private final String name;

    /**
     * Creates a new principal with the given name.
     * @param name Principal name.
     */
    public OauthSimplePrincipal(final String name) {
        this.name = name;
        CommonOauthUtils.assertNotNull(this.name, "name cannot be null.");
    }

    public final String getName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof OauthSimplePrincipal)) {
            return false;
        } else {
            return getName().equals(((OauthSimplePrincipal)o).getName());
        }
    }

    public int hashCode() {
        return 37 * getName().hashCode();
    }

}
