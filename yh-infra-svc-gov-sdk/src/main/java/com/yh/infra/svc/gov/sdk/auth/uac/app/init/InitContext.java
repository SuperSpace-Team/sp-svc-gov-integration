package com.yh.infra.svc.gov.sdk.auth.uac.app.init;

import com.yh.infra.svc.gov.sdk.auth.uac.app.AppContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkConstants;
import com.yh.infra.svc.gov.sdk.auth.uac.app.UacSdkContext;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnitType;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.PrivilegeQueryCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.OperationUnitTypeManager;
import com.yh.infra.svc.gov.sdk.auth.uac.app.major.PrivilegeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化UAC权限上下文
 * @author: luchao
 * @date: 2019/3/11 23:38
 */
public class InitContext implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(InitContext.class);

    private void initOperationUnitTypes(ApplicationContext applicationContext){
        OperationUnitTypeManager operationUnitTypeManager = applicationContext.getBean("operationUnitTypeManager", OperationUnitTypeManager.class);
        for (OperationUnitType out : operationUnitTypeManager.getAllOperationUnitTypes()){
            AppContext.getInstance().getOuTypesMap().put(out.getCode(), out);
        }
    }

    private void initSdkContext(ApplicationContext applicationContext){
        UacSdkConstants sdkConstants = applicationContext.getBean("sdkConstant", UacSdkConstants.class);
        UacSdkContext.init(sdkConstants);
    }

    private void initAppContext(ApplicationContext applicationContext){

        PrivilegeManager privilegeManager = applicationContext.getBean("privilegeManager", PrivilegeManager.class);

        log.info("start to load controlled privilege urls");
        // 获取所有受管控的URL
        List<String> urlList = privilegeManager.findAllPrivilegeUrlList();
        AppContext.getInstance().setControlledUrlSet(AppContext.initControlledUrlSet(urlList));

        //初始化组织对应的全部权限
        List<PrivilegeQueryCommand> pqcList = privilegeManager.getPrivilegeCommands();
        Map<Long, List<PrivilegeQueryCommand>> orgToPrivilege = new LinkedHashMap<Long, List<PrivilegeQueryCommand>>();

        //如果没有初始化的权限，则不需要执行下面的内容
        if (pqcList != null){
            for (PrivilegeQueryCommand pqc : pqcList){
                Long orgId = pqc.getOuTypeId();
                if (orgToPrivilege.containsKey(orgId)){
                    orgToPrivilege.get(orgId).add(pqc);
                }else{
                    List<PrivilegeQueryCommand> list = new ArrayList<PrivilegeQueryCommand>();
                    list.add(pqc);
                    orgToPrivilege.put(orgId, list);
                }
            }
        }
        AppContext.getInstance().setOrgToPrivilege(orgToPrivilege);
        //初始化基础组织
        this.initOperationUnitTypes(applicationContext);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            initSdkContext(applicationContext);
            initAppContext(applicationContext);
//
        }

    }
}
