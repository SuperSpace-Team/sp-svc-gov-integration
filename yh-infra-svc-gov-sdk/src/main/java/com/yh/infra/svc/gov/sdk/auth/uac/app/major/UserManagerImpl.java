package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagerImpl implements UserManager {

	@Override
	public User findUserById(Long id, String loginName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("loginName", loginName);
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("secret", UacSdkContext.getSecret());
		String url = UacSdkContext.getDomain() + "/common/auth/user/findUserById";
		User user = CommonAuthUtil.authOpCommon(params, url, User.class);
		return user;
	}
	@Override
	public UserOperateCommand findUserOperateByUserId(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);
		params.put("appKey", UacSdkContext.getAppKey());
		String url = UacSdkContext.getDomain() + "/common/auth/user/findUseOpByUserId";
		UserOperateCommand uoc = CommonAuthUtil.authOpCommon(params, url, UserOperateCommand.class);
		return uoc;
	}

	@Override
	public OperationPrivilegeCommand findOperationPrivilegeCommandByOuTypeId(Long ouTypeId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ouTypeId", ouTypeId);
		params.put("roleId", roleId);
		params.put("appKey", UacSdkContext.getAppKey());
		String url = UacSdkContext.getDomain() + "/common/auth/user/findPrivilegeById";
		OperationPrivilegeCommand opc = CommonAuthUtil.authOpCommon(params, url, OperationPrivilegeCommand.class);
		return opc;
	}

	@Override
	//X
	public User saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPrivilegeCommand findUserPrivilegeByLoginname(String loginName, Long ouId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("ouId", ouId);
		params.put("appKey", UacSdkContext.getAppKey());
		String url = UacSdkContext.getDomain() + "/common/auth/user/findPrivilegeByLoginname";
		UserPrivilegeCommand upc = CommonAuthUtil.authOpCommon(params, url, UserPrivilegeCommand.class);
		return upc;
	}

	@Override
	//X
	public int saveUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, List<String>> rolePrivilegeToMap(List<RolePrivilege> rpList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserCommand> findTheUserInfo(UserCommand command) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", command.getLoginName());
		params.put("email", command.getEmail());
		params.put("phone", command.getPhone());
		params.put("userName", command.getUserName());
		params.put("jobNumber", command.getJobNumber());
		params.put("lifeCycle", command.getLifeCycle());
		String url = UacSdkContext.getDomain() + "/common/auth/user/findTheUserInfo";
		List<UserCommand> list = CommonAuthUtil.authOpCommonList(params, url, UserCommand.class);
		return list;
	}
}
