package com.sp.infra.svc.gov.sdk.notice.remote.manager.impl;

import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.notice.remote.manager.SmsRemoteManager;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.SmsCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.UrlCheckUtil;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class SmsRemoteManagerImpl implements SmsRemoteManager {

    private String noticeBaseUrl;

    @Override
    public Long send(SmsCommand smsCommand) throws BusinessException {
        UrlCheckUtil.checkUrl(noticeBaseUrl);
        String jsonData= JsonUtil.writeValue(smsCommand);
        if (!noticeBaseUrl.endsWith("/")) {
            noticeBaseUrl += "/";
        }
        return Long.valueOf(HttpJsonClient.postJsonData(noticeBaseUrl + "send/sms", jsonData, 0, null).getEntity());
    }

    public String getNoticeBaseUrl() {
        return noticeBaseUrl;
    }

    public void setNoticeBaseUrl(String noticeBaseUrl) {
        this.noticeBaseUrl = noticeBaseUrl;
    }
}