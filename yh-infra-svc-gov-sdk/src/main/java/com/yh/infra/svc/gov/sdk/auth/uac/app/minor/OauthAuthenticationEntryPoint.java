package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonOauthUtils;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OauthAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean
{
	
	//登录地址
	private String loginUrl;
	
	//登录成功后跳转地址
	private String serviceUrl;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.hasLength(this.loginUrl, "loginUrl must be specified");
		Assert.hasLength(this.serviceUrl, "serviceUrl must be specified");
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
	{
		System.out.println("-----------------进入------------------------");
		final String urlEncodedService = createServiceUrl(request, response);
		String redirectUrl = null;
		String code = request.getParameter("code");
		String appKey = request.getParameter("appkey");
		String saasToken = request.getParameter("saasTenantToken");//saas化token
		String logoutUrl = request.getRequestURL().toString();
		if (!CommonUtil.isEmpty(logoutUrl) && logoutUrl.contains("/person/logout"))
		{
			redirectUrl = logoutUrl;
		}
		else
		{
			//如果code为空，则跳转到登录url
			if (CommonUtil.isEmpty(code))
			{
				System.out.println("loginUrl:" + this.loginUrl + " urlEncodeService:" + urlEncodedService);
				redirectUrl = createRedirectUrl(this.loginUrl, urlEncodedService, "");
			}
			//如果code不为空，说明已经登录，后台验证
			else
			{
				System.out.println("urlEncodedService:" + urlEncodedService + " code:" + code);
				if (appKey != null && appKey.contains("oms4"))
				{
					request.getSession().setAttribute("appkey", appKey);
				}
				redirectUrl = createRedirectUrl("", urlEncodedService, code);
			}
		}
		request.getSession().setAttribute("saasToken", saasToken);
		//重定向前预处理
		preCommence(request, response);
		System.out.println("-----------------redirectUrl：" + redirectUrl + "------------------------");
		response.sendRedirect(redirectUrl);
	}
	
	/**
	 * 创建服务端url
	 * @author wenjin.gao
	 * @param request
	 * @param response
	 * @return
	 */
	protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response)
	{
		return CommonOauthUtils.constructServiceUrl(null, response, this.serviceUrl, null, null, true);
	}
	
	/**
	 * 创建重定向url
	 * @author wenjin.gao
	 * @param serviceUrl
	 * @return
	 */
	protected String createRedirectUrl(final String loginUrl, final String serviceUrl, String code)
	{
		return CommonOauthUtils.constructRedirectUrl(loginUrl, code, serviceUrl);
	}
	
	/**
	 * 在此处可以做重定向发生前预处理
	 * @author wenjin.gao
	 * @param request
	 * @param response
	 */
	protected void preCommence(final HttpServletRequest request, final HttpServletResponse response)
	{
		
	}
	
	public String getLoginUrl()
	{
		return loginUrl;
	}
	
	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}
	
	public String getServiceUrl()
	{
		return serviceUrl;
	}
	
	public void setServiceUrl(String serviceUrl)
	{
		this.serviceUrl = serviceUrl;
	}
	
}
