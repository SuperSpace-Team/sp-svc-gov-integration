package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.AppCommand;

import java.util.List;

public interface AdminAppManager {
	
	AppCommand get(Long id);
	
	Integer state(List<Long> ids, Integer lifecycle);
	
	AppCommand saveOrUpdate(AppCommand command);
	
	List<AppCommand> hasAuthAppSystem(Long userId);
	
	Integer batchSyncInfo(List<Long> ids, Long appId);
}	
