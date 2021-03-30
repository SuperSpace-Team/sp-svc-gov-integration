package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;

import java.util.List;

public interface AdminOpTypeUnitManager {
	
	List<MenuItemCommand> tree(String appKey);
	
	OperationUnitType saveOrUpdate(OperationUnitType command);
	
	OperationUnitType get(Long id);
}	
