package com.sp.infra.svc.gov.sdk.task.command;

/**
 *
 *@Title:任务列表
 *@Description:
 *@Author:long.cheng
 *@Since:2016年4月25日
 *@Copyright:Copyright (c) 2014
 *@ModifyDate:
 *@Version:1.1.0
 */
public class SchedulerTaskCommand extends SchedulerTask {

    /**
     *
     */
    private static final long serialVersionUID = 4134232990301883382L;
    /**

     /**
     * 分区编码
     */
    private String areaCode;
    /**
     * 分区名字
     */
    private String areaName;

    /**
     * 集群分组
     */
    private String registerGroup;

    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getRegisterGroup() {
        return registerGroup;
    }
    public void setRegisterGroup(String registerGroup) {
        this.registerGroup = registerGroup;
    }

}
