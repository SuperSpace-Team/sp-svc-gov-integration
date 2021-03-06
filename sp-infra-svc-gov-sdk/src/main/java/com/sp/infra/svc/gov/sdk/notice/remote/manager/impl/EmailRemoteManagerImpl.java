package com.sp.infra.svc.gov.sdk.notice.remote.manager.impl;

import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.notice.remote.manager.EmailRemoteManager;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.email.EmailCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.UrlCheckUtil;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class EmailRemoteManagerImpl implements EmailRemoteManager {


    private String noticeBaseUrl;

    @Override
    public Long send(EmailCommand emailCommand) throws BusinessException {

        UrlCheckUtil.checkUrl(noticeBaseUrl);
        String jsonData = JsonUtil.toJson(emailCommand);
        if (!noticeBaseUrl.endsWith("/")) {
            noticeBaseUrl += "/";
        }
        return Long.valueOf(HttpJsonClient.postJsonData(noticeBaseUrl + "send/email", jsonData, 0, null).getEntity());
    }

    public String getNoticeBaseUrl() {
        return noticeBaseUrl;
    }

    public void setNoticeBaseUrl(String noticeBaseUrl) {
        this.noticeBaseUrl = noticeBaseUrl;
    }
}