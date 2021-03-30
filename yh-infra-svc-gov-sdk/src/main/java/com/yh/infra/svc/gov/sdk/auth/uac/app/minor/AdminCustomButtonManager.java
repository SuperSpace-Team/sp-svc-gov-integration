package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.CustomButtonCommand;

import java.util.List;

public interface AdminCustomButtonManager {
    CustomButtonCommand saveOrUpdate(CustomButtonCommand command);
    

    CustomButtonCommand get(Long id);
    

    Integer delete(List<Long> ids);
}	
