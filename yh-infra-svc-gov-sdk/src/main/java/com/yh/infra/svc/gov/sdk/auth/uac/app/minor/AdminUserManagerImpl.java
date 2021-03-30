package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.common.utilities.type.StringUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserManagerImpl extends Uac2BaseManager implements AdminUserManager
{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminUserManagerImpl.class);
	
	@Override
	public BackWarnEntity saveOrUpdate(UserCommand command, List<Long> ids)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commonUser", command);
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/user/saveOrUpdate";
		return CommonAuthUtil.authOpCommon(params, url, BackWarnEntity.class);
	}
	
	@Override
	public Integer update(Integer lifecycle, List<Long> ids)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("lifecycle", lifecycle);
		String url = UacSdkContext.getDomain() + "/json/admin/user/update/lifecycle";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	@Override
	public UserCommand get(Long id)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/user/get";
		return CommonAuthUtil.authOpCommon(params, url, UserCommand.class);
	}
	
	@Override
	public Integer unlockUser(Long id)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/user/unlock/user";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	@Override
	public Integer batchSyncInfo(List<Long> ids)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/user/batch/syncInfo";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> resetPwd(Long id)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String url = UacSdkContext.getDomain() + "/json/admin/user/resetPwd";
		return CommonAuthUtil.authOpCommon(params, url, Map.class);
	}
	
	@Override
	public UserCommand checkLoginName(String loginName)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		String url = UacSdkContext.getDomain() + "/json/admin/user/checkLoginName";
		return CommonAuthUtil.authOpCommon(params, url, UserCommand.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkPwdRule(String loginName, String password)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("password", password);
		String url = UacSdkContext.getDomain() + "/json/admin/user/checkPwdRule";
		return CommonAuthUtil.authOpCommon(params, url, Map.class);
	}
	
	@Override
	public Integer addAppUser(Long userId, List<Long> appIds)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("appIds", appIds);
		String url = UacSdkContext.getDomain() + "/json/admin/user/addAppUser";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> update(Long id, String addFlag)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("addFlag", addFlag);
		String url = UacSdkContext.getDomain() + "/json/admin/user/update";
		return CommonAuthUtil.authOpCommon(params, url, Map.class);
	}
	
	@Override
	public BackWarnEntity delAppUser(Long userId, Long appId)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("appId", appId);
		String url = UacSdkContext.getDomain() + "/json/admin/user/delAppUser";
		return CommonAuthUtil.authOpCommon(params, url, BackWarnEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> userRoleDetail(Long userId, String appKey)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("param_appKey", appKey);
		String url = UacSdkContext.getDomain() + "/json/admin/user/role/detail";
		return CommonAuthUtil.authOpCommon(params, url, Map.class);
	}
	
	@Override
	public StdRoleCommand findRoleByRoleId(Long roleId, String appKey)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("param_appKey", appKey);
		String url = UacSdkContext.getDomain() + "/json/admin/user/ouType/role";
		return CommonAuthUtil.authOpCommon(params, url, StdRoleCommand.class);
	}
	
	@Override
	public UserRole saveOrUpdate(UserRole userRole)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userRole", userRole);
		String url = UacSdkContext.getDomain() + "/json/admin/user/role/add";
		return CommonAuthUtil.authOpCommon(params, url, UserRole.class);
	}
	
	@Override
	public Integer deleteRoles(List<Long> ids)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		String url = UacSdkContext.getDomain() + "/json/admin/user/role/remove";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	@Override
	public BackWarnEntity upload(List<UserCommand> list)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("list", list);
		String url = UacSdkContext.getDomain() + "/json/admin/user/upload";
		return CommonAuthUtil.authOpCommon(params, url, BackWarnEntity.class);
	}
	
	@Override
	public Integer batchSetExpiryDate(List<Long> ids, String date)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("date", date);
		String url = UacSdkContext.getDomain() + "/json/admin/user/update/expiryDate";
		return CommonAuthUtil.authOpCommon(params, url, Integer.class);
	}
	
	//外部用戶註冊接口
    @Override
	public String saveOrUpdate2(UserRegisterCommod command)
	{
	    if (null == command || StringUtil.isEmpty(command.getSaasTenantCode())){
	        return "注册用户saas租户编号不能为空";
        }
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commonUser", command);
		if (startUrl.endsWith("/"))
		{
			startUrl = startUrl + "uac2/uac2/json/admin/user/saveOrUpdate";
		}
		else
		{
			startUrl = startUrl + "/uac2/uac2/json/admin/user/saveOrUpdate";
		}
		return CommonUtil.sendRequestString(startUrl,params);
	}
	
	
	
}
