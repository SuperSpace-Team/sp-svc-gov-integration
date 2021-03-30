package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCustomBgManagerImpl implements AdminCustomBgManager{

    @Override
    public CustomBackgroundCommand get(Long id){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        String url = UacSdkContext.getDomain() + "/json/admin/bg/get";
        return CommonAuthUtil.authOpCommon(params, url, CustomBackgroundCommand.class);
    }

    @Override
    public Integer state(List<Long> ids, Integer lifecycle){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lifecycle", lifecycle);
        params.put("ids", ids);
        String url = UacSdkContext.getDomain() + "/json/admin/bg/state";
        Integer count = CommonAuthUtil.authOpCommon(params, url, Integer.class);
        return count;
    }

    @Override
    public Integer delete(List<Long> ids){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        String url = UacSdkContext.getDomain() + "/json/admin/bg/del";
        Integer count = CommonAuthUtil.authOpCommon(params, url, Integer.class);
        return count;
    }

    @Override
    public CustomBackgroundCommand saveOrUpdate(CustomBackgroundCommand command){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("command", command);
        String url = UacSdkContext.getDomain() + "/json/admin/bg/add";
        CustomBackgroundCommand app = CommonAuthUtil.authOpCommon(params, url, CustomBackgroundCommand.class);
        return app;
    }

    @Override
    public CustomBackgroundCommand customBg(Date time, String appkey){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appkey", appkey);
        params.put("time", time);
        String url = UacSdkContext.getDomain() + "/json/admin/bg/del";
        CustomBackgroundCommand app = CommonAuthUtil.authOpCommon(params, url, CustomBackgroundCommand.class);
        return app;
    }

    @Override
    public List<String> queryAppkey(){
        String url = UacSdkContext.getDomain() + "/json/admin/bg/getAppkey";
        List<String> appkeys = CommonAuthUtil.authOpCommonList(new HashMap<String, Object>(), url, String.class);
        return appkeys;
    }
}
