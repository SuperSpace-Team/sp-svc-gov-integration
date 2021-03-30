package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.service.email;

import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email.CheckEmailCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email.EmailCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email.EmailSendLogCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email.TemplateCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

import java.util.List;

/**
 * 邮件发送接口
 */
public interface EmailService {
    Long send(EmailCommand email) throws BusinessException;

    List<EmailSendLogCommand> checkEmail(CheckEmailCommand command);

    void saveEmailTemplate(TemplateCommand command);
}