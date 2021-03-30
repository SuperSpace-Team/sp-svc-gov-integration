package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.Role;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RoleCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.RolePrivilege;

import java.util.List;


public interface RoleManager   {
	/**
	* @author 何波
	* @Description: 根据是否有id进行判断,有id值修改,反之新增
	* @param command
	* @return
	* RoleCommand
	* @throws
	 */
	@Deprecated
	RoleCommand addOrUpdate(RoleCommand command);


	/**
	 *
	* @author 何波
	* @Description: 删除角色
	* @param ids  角色id
	* void
	* @throws
	 */
	@Deprecated
	int remove(List<Long> ids);
	
	
	/**
	 * 
	* @author 何波
	* @Description: 获取角色与权限id
	* @param id 角色id
	* @return   
	* Role   
	* @throws
	 */
	RoleCommand getById(Long id);
	
	/**
	 * 
	* @author 何波
	* @Description: 获取角色
	* @param ids
	* @return   
	* List<Role>   
	* @throws
	 */
	List<Role> getRoles(List<Long> ids);

	/**
	 * @author 周中波
	 * @Description: 基础常规查询
	 * @param ouTypeId
	 * @return
	 */
	List<Role> findListByParam(Role role);
	
	/**
	 * 根据用户id和组织id获取角色权限
	 * @author gianni.zhang
	 * @param userId
	 * @param ouId
	 * @return
	 */
	@Deprecated
    List<RolePrivilege> findRolePrivilegeByUserIdAndOuId(Long userId, Long ouId);
}

