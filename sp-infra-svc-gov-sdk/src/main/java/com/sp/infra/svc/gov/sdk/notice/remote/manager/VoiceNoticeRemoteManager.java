package com.sp.infra.svc.gov.sdk.notice.remote.manager;

import com.sp.infra.svc.gov.sdk.auth.uac.app.BusinessException;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.voiceNotice.VoiceNoticeCommand;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 * Description: 语音消息公共接口调用
 */
public interface VoiceNoticeRemoteManager {

    /**
     * 语音消息信息
     * @param voiceNoticeCommand
     * @return
     * @throws BusinessException
     */
    Long send(VoiceNoticeCommand voiceNoticeCommand) throws BusinessException;
}
