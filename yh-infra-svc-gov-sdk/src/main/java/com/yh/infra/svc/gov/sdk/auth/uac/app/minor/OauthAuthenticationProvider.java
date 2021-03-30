package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CommonUserInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.*;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class OauthAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

	// ~ Instance fields
	// ================================================================================================

	private AuthenticationUserDetailsService<OauthAssertionAuthenticationToken> authenticationUserDetailsService;

	private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private String key;
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	private OauthSyncUserInfo oauthSyncUserInfo;

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.authenticationUserDetailsService, "An authenticationUserDetailsService must be set");
		Assert.hasText(this.key,
				"A Key is required so OauthAuthenticationProvider can identify tokens it previously authenticated");
		Assert.notNull(this.messages, "A message source must be set");
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		OauthAuthenticationToken result = this.authenticateNow(authentication);
		result.setDetails(authentication.getDetails());
		return result;
	}

	private OauthAuthenticationToken authenticateNow(final Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
		OauthProcess oauth = new OauthProcess();
		System.out.println("-------------------authenticateNow token:" + authToken.getCredentials().toString()+"-----");
		//通过 token 获取用户信息
		CommonUserInfo commonUser = oauth.queryUserInfo(authToken.getCredentials().toString());
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("username", commonUser.getLoginName());
		attributes.put("gonghao", commonUser.getJobNumber());
		
		//同步用户信息，如果同步失败，则抛出异常
//		if(!oauthSyncUserInfo.syncUserInfo(commonUser)){
//			throw new RuntimeException("用户"+commonUser.getLoginName()+"同步信息失败，请联系管理员。");
//		}
		
		//验证用户
		OauthAssertion assertion = new OauthAssertionImpl(new OauthAttributePrincipalImpl(commonUser.getLoginName(), attributes));
		final UserDetails userDetails = loadUserByAssertion(assertion);
		userDetailsChecker.check(userDetails);
		return new OauthAuthenticationToken(this.key, userDetails, 
				authoritiesMapper.mapAuthorities(userDetails.getAuthorities()), userDetails, assertion);
	}

	protected UserDetails loadUserByAssertion(final OauthAssertion assertion) {
		final OauthAssertionAuthenticationToken token = new OauthAssertionAuthenticationToken(assertion);
		return this.authenticationUserDetailsService.loadUserDetails(token);
	}

	@Deprecated
	public void setUserDetailsService(final UserDetailsService userDetailsService) {
		this.authenticationUserDetailsService = new UserDetailsByNameServiceWrapper(userDetailsService);
	}

	public void setAuthenticationUserDetailsService(
			final AuthenticationUserDetailsService<OauthAssertionAuthenticationToken> authenticationUserDetailsService) {
		this.authenticationUserDetailsService = authenticationUserDetailsService;
	}

	protected String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setMessageSource(final MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}

	public boolean supports(final Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication))
				|| (OauthAssertionAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public OauthSyncUserInfo getOauthSyncUserInfo() {
		return oauthSyncUserInfo;
	}

	public void setOauthSyncUserInfo(OauthSyncUserInfo oauthSyncUserInfo) {
		this.oauthSyncUserInfo = oauthSyncUserInfo;
	}
}
