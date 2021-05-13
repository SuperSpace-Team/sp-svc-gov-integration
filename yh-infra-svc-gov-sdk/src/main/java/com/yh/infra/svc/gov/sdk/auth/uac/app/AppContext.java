package com.yh.infra.svc.gov.sdk.auth.uac.app;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeAndUrlCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeQueryCommand;

import java.util.*;

public final class AppContext {
    private static AppContext appContext = new AppContext();
    
    private Set<String> controlledUrlSet;
    
    private Map<Long, List<PrivilegeQueryCommand>> orgToPrivilege ;
    
    private Map<Long, List<PrivilegeQueryCommand>> localOrgToPrivilege ;


    private Map<String, OperationUnitType> ouTypesMap =
            new LinkedHashMap<String, OperationUnitType>(5);
    
    private Map<String, List<PrivilegeAndUrlCommand>> privilegeAndUrl ;
    
    private AppContext() {}
    
    public static AppContext getInstance() {
        return appContext;
    }

    /**
     * 受管控的URL列表
     * @author 李光辉
     * @return
     * @since
     */
    public Set<String> getControlledUrlSet() {
        return controlledUrlSet;
    }

    public void setControlledUrlSet(Set<String> controlledUrlSet) {
        this.controlledUrlSet = controlledUrlSet;
    }
    
    public static Set<String> initControlledUrlSet(List<String> urlList) {
        Set<String> set = new HashSet<String>(urlList.size());
        for (String url : urlList) {
            set.add(url);
        }
        
        return set;
    }

	public Map<Long, List<PrivilegeQueryCommand>> getOrgToPrivilege() {
		return orgToPrivilege;
	}

	public void setOrgToPrivilege(
			Map<Long, List<PrivilegeQueryCommand>> orgToPrivilege) {
		this.orgToPrivilege = orgToPrivilege;
	}

	public Collection<OperationUnitType> getOperationUnitTypes() {
		return ouTypesMap.values();
	}

	public Map<Long, List<PrivilegeQueryCommand>> getLocalOrgToPrivilege() {
		return localOrgToPrivilege;
	}

	public void setLocalOrgToPrivilege(
			Map<Long, List<PrivilegeQueryCommand>> localOrgToPrivilege) {
		this.localOrgToPrivilege = localOrgToPrivilege;
	}

    public Map<String, OperationUnitType> getOuTypesMap() {
        return ouTypesMap;
    }
    public Map<String, List<PrivilegeAndUrlCommand>> getPrivilegeAndUrl() {
		return privilegeAndUrl;
	}

	public void setPrivilegeAndUrl(
			Map<String, List<PrivilegeAndUrlCommand>> privilegeAndUrl) {
		this.privilegeAndUrl = privilegeAndUrl;
	}
}
