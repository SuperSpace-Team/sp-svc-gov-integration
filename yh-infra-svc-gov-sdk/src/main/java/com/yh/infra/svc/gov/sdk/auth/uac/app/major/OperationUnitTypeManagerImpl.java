package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Ice丶cola
 * @date 2021.05.13
 */
public class OperationUnitTypeManagerImpl implements OperationUnitTypeManager {

    @Override
    public List<OperationUnitType> getAllOperationUnitTypes() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", UacSdkContext.getAppKey());
        params.put("secret", UacSdkContext.getSecret());
        String url = UacSdkContext.getDomain() + "/common/auth/opUnitType/findAllList";
        List<OperationUnitType> operationUnitTypeList = CommonAuthUtil.authOpCommonList(params, url, OperationUnitType.class);
        return operationUnitTypeList;
    }

    @Override
    public OperationUnitType findUnitTypeByOrgId(Long orgId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey", UacSdkContext.getAppKey());
        params.put("orgId", orgId);
        String url = UacSdkContext.getDomain() + "/common/auth/opUnitType/findUnitTypeByOrgId";
        OperationUnitType out = CommonAuthUtil.authOpCommon(params, url, OperationUnitType.class);
        return out;
    }
}
