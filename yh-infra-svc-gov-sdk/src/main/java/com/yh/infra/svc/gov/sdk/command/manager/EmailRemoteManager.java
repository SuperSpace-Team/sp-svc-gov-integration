package com.yh.infra.svc.gov.sdk.command.manager;

import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email.EmailCommand;
import com.yh.infra.svc.gov.sdk.platform.baseservice.notice.exception.BusinessException;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 * Description: 邮件公共接口，
 */
public interface EmailRemoteManager {


    /**
     * 邮件发送
     * @param emailCommand
     * @return
     */
    Long send(EmailCommand emailCommand) throws BusinessException;
}
