package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;

import java.util.List;
import java.util.Map;


public interface AdminUserManager {
	

	BackWarnEntity saveOrUpdate(UserCommand command, List<Long> ids);
	
	Map<String, Object> update(Long id, String addFlag);
	
	Integer update(Integer lifecycle, List<Long> ids);
	

	UserCommand get(Long id);
	
	Integer unlockUser(Long id);
	
	Integer batchSyncInfo(List<Long> ids);
	
	Map<String, Object> resetPwd(Long id);
	
	UserCommand checkLoginName(String loginName);
	
	Map<String, Object> checkPwdRule(String loginName, String password);
	
	Integer addAppUser(Long userId, List<Long> appIds);
	
	BackWarnEntity delAppUser(Long userId, Long appId);
	
	Map<String, Object> userRoleDetail(Long userId, String appKey);
	
	StdRoleCommand findRoleByRoleId(Long roleId, String appKey);
	
	UserRole saveOrUpdate(UserRole userRole);
	
	Integer deleteRoles(List<Long> ids);
	
	
	BackWarnEntity upload(List<UserCommand> list);

	Integer batchSetExpiryDate(List<Long> ids, String date);
	
	String saveOrUpdate2(UserRegisterCommod command);
}
