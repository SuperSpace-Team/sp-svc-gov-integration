/**
 * Copyright (c) 2013 Yonghui All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of Yonghui.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Yonghui.
 * <p>
 * YONGHUI MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. YONGHUI SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.yh.svc.gov.test.springboot1.dao;

import com.yh.svc.gov.test.springboot1.model.PgAppSystem;
import com.yh.common.lark.common.annotation.CommonQuery;
import com.yh.common.lark.common.annotation.QueryPage;
import com.yh.common.lark.common.dao.Page;
import com.yh.common.lark.common.dao.Pagination;
import com.yh.common.lark.common.dao.Sort;
import lark.orm.dao.supports.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface PgAppSystemDao extends BaseDao<PgAppSystem, Long> {


    @QueryPage("findListCountByQueryMap")
    Pagination<PgAppSystem> findListByQueryMapWithPage(Page page, Sort[] sorts, Map<String, Object> params);

    @CommonQuery
    int saveOrUpdate(PgAppSystem o);


    /**
     * 根据appId查询应用
     *
     * @param appId
     * @return
     */
    PgAppSystem getByAppId(@Param("appId") String appId);

}
