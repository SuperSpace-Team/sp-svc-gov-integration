package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.BackWarnEntity;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.UrlCommand;

import java.util.List;

public interface AdminUrlManager {
	UrlCommand saveOrUpdate(UrlCommand commond);
	
	Integer delete(List<Long> ids);
	
	UrlCommand get(Long id);
	
	BackWarnEntity upload(List<UrlCommand> list);
}
