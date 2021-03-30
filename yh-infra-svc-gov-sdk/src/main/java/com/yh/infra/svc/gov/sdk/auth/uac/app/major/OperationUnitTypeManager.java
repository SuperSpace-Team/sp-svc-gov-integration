package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;

import java.util.List;

public interface OperationUnitTypeManager {
	
	/**
	 * 获取所有基础组织
	 * @author wenjin.gao
	 * @return
	 */
	List<OperationUnitType> getAllOperationUnitTypes() ;
	
	/**
	 * 根据组织ID获取组织类型
	 * @param orgId
	 * @return
	 */
	OperationUnitType findUnitTypeByOrgId(Long orgId) ;
}
