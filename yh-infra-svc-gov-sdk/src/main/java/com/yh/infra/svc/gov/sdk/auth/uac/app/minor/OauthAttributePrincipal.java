package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

public interface OauthAttributePrincipal extends Principal, Serializable {

    /**
     * The Map of key/value pairs associated with this principal.
     * @return the map of key/value pairs associated with this principal.
     */
    Map<String, Object> getAttributes();

}
