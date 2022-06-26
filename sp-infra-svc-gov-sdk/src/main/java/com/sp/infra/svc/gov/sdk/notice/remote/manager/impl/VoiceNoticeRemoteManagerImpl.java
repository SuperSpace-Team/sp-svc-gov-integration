package com.sp.infra.svc.gov.sdk.notice.remote.manager.impl;

import com.sp.infra.svc.gov.sdk.net.impl.HttpJsonClient;
import com.sp.infra.svc.gov.sdk.notice.remote.manager.VoiceNoticeRemoteManager;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.voiceNotice.VoiceNoticeCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;
import com.sp.infra.svc.gov.sdk.util.JsonUtil;
import com.sp.infra.svc.gov.sdk.util.UrlCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class VoiceNoticeRemoteManagerImpl implements VoiceNoticeRemoteManager {
    private static final Logger logger = LoggerFactory.getLogger(VoiceNoticeRemoteManagerImpl.class);

    private String noticeBaseUrl;

    @Override
    public Long send(VoiceNoticeCommand voiceNoticeCommand) throws BusinessException {
        UrlCheckUtil.checkUrl(noticeBaseUrl);
        String jsonData = JsonUtil.toJson(voiceNoticeCommand);
        if (!noticeBaseUrl.endsWith("/")) {
            noticeBaseUrl += "/";
        }
        return Long.valueOf(HttpJsonClient.postJsonData(noticeBaseUrl + "send/voice", jsonData, 0, null).getEntity());
    }

    public String getNoticeBaseUrl() {
        return noticeBaseUrl;
    }

    public void setNoticeBaseUrl(String noticeBaseUrl) {
        this.noticeBaseUrl = noticeBaseUrl;
    }
}