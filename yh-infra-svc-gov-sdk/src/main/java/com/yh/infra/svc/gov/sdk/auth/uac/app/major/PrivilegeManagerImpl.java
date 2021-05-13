package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeQueryCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public class PrivilegeManagerImpl implements PrivilegeManager {

    @Override
    public List<String> findAllPrivilegeUrlList() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", UacSdkContext.getAppKey());
        String url = UacSdkContext.getDomain() + "/common/auth/privilege/findAllList";
        List<String> privilegeCmdList = CommonAuthUtil.authOpCommonList(params, url, String.class);
        return privilegeCmdList;
    }

    @Override
    public List<PrivilegeQueryCommand> getPrivilegeCommands() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", UacSdkContext.getAppKey());
        String url = UacSdkContext.getDomain() + "/common/auth/privilege/ouList";
        List<PrivilegeQueryCommand> privilegeCmdList = CommonAuthUtil.authOpCommonList(params, url, PrivilegeQueryCommand.class);
        return privilegeCmdList;
    }
}
