package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeQueryCommand;

import java.util.List;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public interface PrivilegeManager {
    /**
     * 获取该应用下所有的权限信息
     * @return
     */
    List<String> findAllPrivilegeUrlList();

    /**
     * 获取该应用下所有的权限信息
     * @return
     */
    List<PrivilegeQueryCommand> getPrivilegeCommands();
}
