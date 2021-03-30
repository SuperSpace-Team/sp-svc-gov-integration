package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.service.voiceNotice;

import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.voiceNotice.VoiceNoticeCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/30
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 * Description: 语音电话客户端调用接口
 */


public interface VoiceNoticeService {

    /**
     * 发送企业微信消息
     *
     * @param voiceNoticeCommand
     * @return
     */
    Long send(VoiceNoticeCommand voiceNoticeCommand) throws BusinessException;
}