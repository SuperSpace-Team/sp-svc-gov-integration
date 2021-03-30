package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;


import com.yh.infra.svc.gov.sdk.auth.uac.app.command.BackWarnEntity;

import java.util.List;

public interface AdminPassRuleConfigManager {
	

	BackWarnEntity update(List<String> selectStr, List<String> selectValue);
}	
