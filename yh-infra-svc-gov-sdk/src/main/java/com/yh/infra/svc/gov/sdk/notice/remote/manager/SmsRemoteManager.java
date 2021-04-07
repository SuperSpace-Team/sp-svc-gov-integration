package com.yh.infra.svc.gov.sdk.notice.remote.manager;

import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.SmsCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 * Description: 短信消息公共接口
 */


public interface SmsRemoteManager {

    /**
     * 短信发送，返回发送记录日志id
     * @param smsCommand
     * @return
     */
    Long send(SmsCommand smsCommand) throws BusinessException;
}