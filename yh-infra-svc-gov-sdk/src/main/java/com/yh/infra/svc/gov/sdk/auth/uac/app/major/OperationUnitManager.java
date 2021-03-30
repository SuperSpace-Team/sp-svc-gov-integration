package com.yh.infra.svc.gov.sdk.auth.uac.app.major;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OpUnitTreeCommand;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.OperationUnit;

import java.util.List;

public interface OperationUnitManager  {



    /**
     * 根据用户ID获取组织
     * 
     * @param userId
     * @return
     */
    public List<OpUnitTreeCommand> findOpUnitTreeByUserId(Long userId) ;

    /**
     * @author 周中波 基础信息查询
     * @param operationUnit
     * @return
     */
    List<OperationUnit> findListByParam(OperationUnit operationUnit);

    /**
     * 未实现
     * @author wenjin.gao
     * @param parentId
     * @return
     */
    @Deprecated
    List<OpUnitTreeCommand> findListByParentId(Long parentId);

    /**
     * 未实现
     * @author wenjin.gao
     * @param id
     * @return
     */
    @Deprecated
    OperationUnit findOperationUnitById(Long id);

}
