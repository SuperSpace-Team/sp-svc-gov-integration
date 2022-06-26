package com.sp.infra.svc.gov.sdk.notice.remote.manager;

import com.sp.infra.svc.gov.sdk.auth.uac.app.BusinessException;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.weChat.WeChatCommand;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 * Description: 企业微信公共api接口调用
 */
public interface WeChatRemoteManager {

    /**
     * 企业微信信息
     * @param WeChatCommand
     * @return
     * @throws BusinessException
     */
    Long send(WeChatCommand WeChatCommand) throws BusinessException;
}
