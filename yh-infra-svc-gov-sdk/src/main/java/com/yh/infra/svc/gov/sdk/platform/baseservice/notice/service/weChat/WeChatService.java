package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.service.weChat;

import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.weChat.WeChatCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

/**
 * 发送企业微信消息
 */
public interface WeChatService {

    /**
     * 发送企业微信消息
     *
     * @param weChatCommand
     * @return
     */
    Long send(WeChatCommand weChatCommand) throws BusinessException;
}
