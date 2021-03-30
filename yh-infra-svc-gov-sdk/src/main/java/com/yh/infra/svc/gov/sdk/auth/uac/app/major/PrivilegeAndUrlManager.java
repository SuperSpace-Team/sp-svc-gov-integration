package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeAndUrlCommand;

import java.util.List;
import java.util.Map;

public interface PrivilegeAndUrlManager  {

	/**
	 * 根据参数获取URL权限列表
	 * @param groupName
	 * @param ouType
	 * @param type
	 * @return
	 */
	List<PrivilegeAndUrlCommand> findPrivilegeAndUrl(String groupName, Long ouType, Integer type) ;

	/**
	 * 批量保存URL与权限(未实现)
	 * @param urlMap
	 * key:acl+!+type
	 * value:List
	 * @return
	 */
	@Deprecated
	long savePrivilegeAndUrls(Map<String, List<PrivilegeAndUrlCommand>> urlMap);
}
