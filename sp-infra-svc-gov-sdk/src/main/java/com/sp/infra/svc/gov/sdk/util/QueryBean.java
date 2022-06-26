package com.sp.infra.svc.gov.sdk.util;

import com.sp.framework.orm.lark.common.dao.Page;
import com.sp.framework.orm.lark.common.dao.Sort;

import java.util.Map;

public class QueryBean {

    private Page page = new Page();
    /**
     * 排序
     */
    private Sort[] sorts;
    /**
     * 查询参数
     */
    private Map<String, Object> paraMap;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Sort[] getSorts() {
        return sorts;
    }

    public void setSorts(Sort[] sorts) {
        this.sorts = sorts;
    }

    public Map<String, Object> getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map<String, Object> paraMap) {
        this.paraMap = paraMap;
    }
}