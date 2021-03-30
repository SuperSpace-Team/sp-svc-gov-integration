package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;


import java.util.Date;
import java.util.List;

public interface AdminCustomBgManager {


    CustomBackgroundCommand get(Long id);

    Integer state(List<Long> ids, Integer lifecycle);

    Integer delete(List<Long> ids);

    CustomBackgroundCommand saveOrUpdate(CustomBackgroundCommand command);

    CustomBackgroundCommand customBg(Date time, String appkey);
    
    List<String> queryAppkey();
}
