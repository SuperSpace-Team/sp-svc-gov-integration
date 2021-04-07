package com.yh.infra.svc.gov.sdk.notice.remote.manager;

import com.yh.infra.svc.gov.sdk.log.command.Pagination;

import java.util.List;
import java.util.Map;

public interface LogQueryManager {
    /**
     * 查询接口
     *
     * @param startDate，查询开始日期，默认可以为null，标识全部，可以设置时间，如（2019-08-08）查询当天的记
     * @param endDate，查询结束日期，默认可以为null，标识全部，可以设置时间，如（2019-08-08）查询当天的记
     * @param customerCode，租户编码
     * @param type，日志类型
     * @param param                                                      1.查询参数list，map 的key必须为searchKey，filterKey，selectValue，分别为查询的字段，查询条件，查询值
     *                                                                   2.filter可以支持四个值：less，larger，equal，like，分别为小于，大于，等于，模糊
     * @param pageIndex                                                  分页页面
     * @param pageSize                                                   分页大小，默认20条，最多100条
     * @return 1.返回值包含两部分，此次查询的数据记录结果集，最后一条记录保存此次条件查询的记录数，用以下方法获取：
     * Long.valueOf(String.valueOf(list.get(list.size() - 1).get("mongoTotalCount")));
     */
    Pagination query(String startDate, String endDate, String customerCode, String type, List<Map<String, Object>> param, Integer pageIndex, Integer pageSize);

    /**
     * 统计接口
     *
     * @param customerCode 租户编码
     * @param type 日志类型
     * @param params 统一map的property-value
     * @param repeat 是否去重，ture标识去重，repeatKey不为空，并且为需要去重的字段
     *               false标识不去重，repeat值为null
     * @param repeatKey 需要去重字段，填写map字段
     * @return
     */
    long count(String customerCode, String type, Map<String, String> params, boolean repeat, String repeatKey);

    /**
     * hub统计接口
     * @param customerCode
     * @param type
     * @param params 查询参数
     * @param groupValue 分组参数
     * @return
     */
    List<Map> groupQuery(String customerCode, String type, Map<String, String> params, String groupValue);

}
