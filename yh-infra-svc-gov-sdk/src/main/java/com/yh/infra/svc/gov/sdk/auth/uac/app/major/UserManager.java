package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;

import java.util.List;
import java.util.Map;

public interface UserManager  {

    /**
     * 通过id或登录名获取用户信息
     * @author 李光辉
     * @param loginName
     * @return
     * @since 1.0.0
     */
    User findUserById(Long id, String loginName) ;
    

    /**
     * 根据员工id，获取对应的员工信息
     * @author 周中波
     * @param page
     * @param sorts
     * @param user
     * @return
     */
    UserOperateCommand findUserOperateByUserId(Long userId);
    
    /**
     * 根据角色id和组织类型id获取对应的角色信息
     * @author 周中波
     * @param ouTypeId
     * @param roleId
     * @return
     */
    OperationPrivilegeCommand findOperationPrivilegeCommandByOuTypeId(Long ouTypeId, Long roleId) ;
    
    /**
     * 添加或者修改用户
     * @author 周中波
     * @param user
     * @return User
     */
    @Deprecated
    User saveOrUpdate(User user);

    /**
     * 获取用户信息、组织信息、权限信息
     * @author 李光辉
     * @param loginName
     * @return
     * @since
     */
    UserPrivilegeCommand findUserPrivilegeByLoginname(String loginName, Long ouId) ;
    
    /**
     * 添加用户
     * @author 张磊
     * @param user
     * @return int
     */
    @Deprecated
    int saveUser(User user) ;
    
    /**
     * 把角色权限放入map
     * @param rpList
     * @return
     */
    @Deprecated
    Map<String, List<String>> rolePrivilegeToMap(List<RolePrivilege> rpList);
    
    /**
     * @Description:条件查询用户
     * @return
     * @author long.cheng
     */
    List<UserCommand> findTheUserInfo(UserCommand command);
}
