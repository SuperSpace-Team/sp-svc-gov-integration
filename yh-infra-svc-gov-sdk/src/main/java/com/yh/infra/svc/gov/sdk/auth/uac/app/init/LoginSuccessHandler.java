package com.yh.infra.svc.gov.sdk.auth.uac.app.init;

import com.yh.common.utilities.type.JsonUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.AdminOpUnitManager;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.MenuItemManager;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.OperationUnitManager;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSuccessHandler extends AbstractLoginSuccessHandler {


	@Autowired
    private UserManager userManager;
	@Autowired
	private MenuItemManager menuItemManager;
	@Autowired
	private OperationUnitManager operationUnitManager;
	@Autowired
	private AdminOpUnitManager opUnitManager;



	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsCommand userDetails = (UserDetailsCommand) authentication.getPrincipal();
        System.out.println("-----------------onAuthenticationSuccess：" + userDetails + "---------------------");
        UserPrivilegeCommand userCommand = this.userManager.findUserPrivilegeByLoginname(userDetails.getUsername(), userDetails.getCurrentOuId());
        userDetails.setCommand(userCommand);
        
        initialize(request, response, userDetails);
        
//        response.sendRedirect(request.getContextPath()+"/index");
        response.sendRedirect(defaultTargetUrl);
    }
    
    /**
	 * 初始化菜单和组织
	 * 
	 * @param session
	 */
	public void initialize(HttpServletRequest request,
                           HttpServletResponse response, UserDetailsCommand udc) {
		HttpSession session = request.getSession();
		
		Long user_id = udc.getUser().getId();
		
		Cookie orgIdCookie = getCookieByName(request, UserConstants.COOKIE_NAME_PREFIX + user_id);

		String orgTree = (String) session.getAttribute(UserConstants.ORG_LIST);

		if (orgTree == null || "".equals(orgTree)) {
		    
		    //保存appkey到session/cookie中
		    String appKey = UacSdkContext.getAppKey();
		    if(StringUtils.isNotBlank(appKey)){
		        session.setAttribute("appkey", appKey);
		        // 新建COOKIE
                Cookie cookie = new Cookie(Constants.COOKIE_NAME_APPKEY, appKey);
                cookie.setMaxAge(24*60*60);
                response.addCookie(cookie);
		    }
			List<OpUnitTreeCommand> otcList = operationUnitManager.findOpUnitTreeByUserId(user_id);//获取组织
			OpUnitTreeCommand unit =null;
			if(otcList!=null){
				unit=otcList.get(otcList.size()-1);//获取默认组织
				session.setAttribute(UserConstants.ORG_LIST, JsonUtil.buildNormalBinder().toJson(otcList));// 初始化组织
				
				Long orgid = null;
				if (orgIdCookie != null) {
					String org = orgIdCookie.getValue();
					orgid = Long.parseLong(org);// 获取上次退出时使用的组织ID
				} else {
					// 获取默认组织
					orgid = unit.getId();
					// 新建COOKIE
					Cookie cookie = new Cookie(UserConstants.COOKIE_NAME_PREFIX
							+ udc.getUser().getId(), orgid.toString());
					cookie.setMaxAge(7*24*60*60);
					response.addCookie(cookie);
				}
				OperationUnit opunit = opUnitManager.get(orgid);
				//判断该组织生命周期是否正常，不正常则选择默认组织生成菜单
				if(opunit.getLifecycle()!= Constants.LIFECYCLE_NORMAL) {
					orgid = unit.getId();
				}
				udc.setCurrentOuId(orgid);
				
				List<MenuItemCommand> miList = menuItemManager.findLeftMenuItems(udc.getUser().getId(), orgid);
				
				
				session.setAttribute(UserConstants.MENU_ITEMS, miList);// 初始化左菜单
				
				OperationUnit ou=new OperationUnit();
				ou.setId(orgid);
				List<OperationUnit> oulist=operationUnitManager.findListByParam(ou);
				if(oulist!=null&&!oulist.isEmpty()){
					session.setAttribute(UserConstants.ORG_TYPE, oulist.get(0));
				}
			}
		}
	}


	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	
	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

}
