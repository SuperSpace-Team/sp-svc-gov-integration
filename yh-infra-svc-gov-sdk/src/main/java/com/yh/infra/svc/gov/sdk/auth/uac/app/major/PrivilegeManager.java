package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeQueryCommand;

import java.util.List;

public interface PrivilegeManager {

    /**
     * 获取该应用下所有的权限信息
     * @return
     */
    List<String> findAllPrivilegeUrlList();
    
    
	/**
	 * 获取该应用的所有权限
	 * @return
	 */
	List<PrivilegeQueryCommand> getPrivilegeCommands();
    
}
