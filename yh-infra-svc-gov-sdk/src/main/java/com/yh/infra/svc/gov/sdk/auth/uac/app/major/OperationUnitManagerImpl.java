package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OpUnitTreeCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnit;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonAuthUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationUnitManagerImpl implements OperationUnitManager {

	@Override
	public List<OpUnitTreeCommand> findOpUnitTreeByUserId(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("userId", userId);
		String url = UacSdkContext.getDomain() + "/common/auth/opUnit/findOpUnitByUserId";
		List<OpUnitTreeCommand> opList = CommonAuthUtil.authOpCommonList(params, url, OpUnitTreeCommand.class);
		return opList;
	}

	@Override
	public List<OperationUnit> findListByParam(OperationUnit operationUnit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appKey", UacSdkContext.getAppKey());
		params.put("operationUnit", JsonUtil.writeValue(operationUnit));
		String url = UacSdkContext.getDomain() + "/common/auth/opUnit/findListByParam";
		List<OperationUnit> opList = CommonAuthUtil.authOpCommonList(params, url, OperationUnit.class);
		return opList;
	}

	@Override
	public List<OpUnitTreeCommand> findListByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationUnit findOperationUnitById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
