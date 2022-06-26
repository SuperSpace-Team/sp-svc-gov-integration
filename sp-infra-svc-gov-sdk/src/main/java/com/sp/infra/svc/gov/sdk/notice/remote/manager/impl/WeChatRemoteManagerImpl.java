package com.sp.infra.svc.gov.sdk.notice.remote.manager.impl;

import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.notice.remote.manager.WeChatRemoteManager;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.weChat.WeChatCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.UrlCheckUtil;

/**
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 14:45
 * Description:
 */
public class WeChatRemoteManagerImpl implements WeChatRemoteManager {

    private String noticeBaseUrl;

    @Override
    public Long send(WeChatCommand WeChatCommand) throws BusinessException {
        UrlCheckUtil.checkUrl(noticeBaseUrl);
        String jsonData = JsonUtil.toJson(WeChatCommand);
        if (!noticeBaseUrl.endsWith("/")) {
            noticeBaseUrl += "/";
        }
        return Long.valueOf(HttpJsonClient.postJsonData(noticeBaseUrl + "send/weChat", jsonData, 0, null).getEntity());
    }

    public String getNoticeBaseUrl() {
        return noticeBaseUrl;
    }

    public void setNoticeBaseUrl(String noticeBaseUrl) {
        this.noticeBaseUrl = noticeBaseUrl;
    }
}