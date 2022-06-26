package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.service.sms;


import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.CheckSmsCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.SmsCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.SmsSendLogCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms.SmsTemplateCommand;
import com.sp.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

import java.util.List;

/**
 * 短信发送接口
 */
public interface SmsService {
    /**
     *
     * @param sms
     * @return
     * @throws BusinessException
     */
    Long send(SmsCommand sms) throws BusinessException;

    List<SmsSendLogCommand> checkSms(CheckSmsCommand command);

    void saveSmsTemplate(SmsTemplateCommand command);
}
