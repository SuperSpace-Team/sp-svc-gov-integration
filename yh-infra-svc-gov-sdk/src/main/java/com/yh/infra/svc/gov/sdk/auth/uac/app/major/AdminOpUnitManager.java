package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.MenuItemCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnit;

import java.util.List;

public interface AdminOpUnitManager {
	
	List<MenuItemCommand> tree(String appKey);
	
	OperationUnit saveOrUpdate(OperationUnit command);
	
	OperationUnit get(Long id);
	
	Integer updateLifecycle(List<Long> ids, Integer lifecycle);
	

}	
