package com.yh.infra.svc.gov.sdk.auth.uac.app.major;


import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemManagerImpl implements MenuItemManager {

	@Override
	public List<MenuItemCommand> findLeftMenuItems(Long userId, Long ouId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("userId", userId);
		params.put("ouId", ouId);
		String url = UacSdkContext.getDomain() + "/common/auth/menu/findLeftMenu";
		List<MenuItemCommand> menuItemList = CommonAuthUtil.authOpCommonList(params, url, MenuItemCommand.class);
		return menuItemList;
	}

    @Override
    public List<MenuItemCommand> findLeftMenuItems(Long userId, Long ouId, String lang){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", UacSdkContext.getAppKey());
        params.put("userId", userId);
        params.put("ouId", ouId);
        params.put("lang", lang);
        String url = UacSdkContext.getDomain() + "/common/auth/menu/findLeftMenu";
        List<MenuItemCommand> menuItemList = CommonAuthUtil.authOpCommonList(params, url, MenuItemCommand.class);
        return menuItemList;
    }
}
