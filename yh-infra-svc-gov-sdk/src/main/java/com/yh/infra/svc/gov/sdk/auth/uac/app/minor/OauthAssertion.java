package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 验证响应请求
 * @author wenjin.gao
 * @Date 2015年8月28日  上午11:39:15
 * @Version 
 * @Description 
 *
 */
public interface OauthAssertion extends Serializable {
	 /**
     * The date from which the assertion is valid from.
     *
     * @return the valid from date.
     */
    Date getValidFromDate();

    /**
     * The date which the assertion is valid until.
     *
     * @return the valid until date.
     */
    Date getValidUntilDate();

    /**
     * The key/value pairs associated with this assertion.
     *
     * @return the map of attributes.
     */
    Map<String, Object> getAttributes();

    /**
     * The principal for which this assertion is valid.
     *
     * @return the principal.
     */
    OauthAttributePrincipal getPrincipal();
}
