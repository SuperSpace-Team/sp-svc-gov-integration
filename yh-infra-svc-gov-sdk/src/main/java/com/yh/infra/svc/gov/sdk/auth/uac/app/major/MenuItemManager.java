package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;

import java.util.List;

public interface MenuItemManager  {

	/**
	 * 根据有用户ID和当前组织ID获取左菜单
	 * @param userId  用户ID
	 * @param ouId  组织ID
	 * @return
	 */
    List<MenuItemCommand> findLeftMenuItems(Long userId, Long ouId) ;

    /**
     * 根据有用户ID和当前组织ID和语言获取左菜单
     * @param userId  用户ID
     * @param ouId  组织ID
     * @param lang  语言
     * @return
     */
    List<MenuItemCommand> findLeftMenuItems(Long userId, Long ouId, String lang);
}
