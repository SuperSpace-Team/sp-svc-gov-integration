package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AccessTokenCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
	
    public OauthAuthenticationFilter() {
        super("/j_spring_oauth_security_check");
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    }

    //~ Methods ========================================================================================================

    @Override
    protected final void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
            super.successfulAuthentication(request, response, chain, authResult);
            return;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException {
       
        String code = request.getParameter("code");
        String appkey = (String) request.getSession().getAttribute("appkey");
        OauthProcess oauth = new OauthProcess();
        String token = null;
        try{
        	AccessTokenCommand accessToken = null;
	        //获取accessToken对象,用以得到token
        	if(appkey!=null){
        	   System.out.println("sppkey:" + appkey);
        		accessToken = oauth.queryAccessToken(code,appkey, UacSdkContext.getSecret());
        	}else{
	         accessToken = oauth.queryAccessToken(code, UacSdkContext.getAppKey(), UacSdkContext.getSecret());
        	}
	        token = accessToken.getAccessToken();
        }catch(Exception e){
        	//秘钥验证失败、IP不在白名单内，都会导致异常 
        	throw new RuntimeException("授权失败,有可能没有" + UacSdkContext.getAppKey() + "系统的操作权限或秘钥验证失败！");
        }
        
        if(CommonUtil.isNotBlank(token)&&token.equals("get_cache_token_error")){
            throw new RuntimeException("get_cache_token_error");
        }
        
        //如果token为空
        if(CommonUtil.isEmpty(token)){
        	throw new RuntimeException("授权失败,有可能没有" + UacSdkContext.getAppKey() + "系统的操作权限或秘钥验证失败！");
        }

        //token 验证,并存入 UsernamePasswordAuthenticationToken 对象 
        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("token", token);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
