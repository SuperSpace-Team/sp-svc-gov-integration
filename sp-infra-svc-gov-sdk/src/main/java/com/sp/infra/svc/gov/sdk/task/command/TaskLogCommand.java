package com.sp.infra.svc.gov.sdk.task.command;

public class TaskLogCommand extends TaskLog {

    /**
     *
     */
    private static final long serialVersionUID = 1881308449125796771L;

    /**
     * 任务编码
     */
    private String taskCode;
    /**
     * 租户编码
     */
    private String customerCode;
    /**
     * int 结果用于接收数据库数据
     */
    private Integer intResult;

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Integer getIntResult() {
        return intResult;
    }

    public void setIntResult(Integer intResult) {
        this.intResult = intResult;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


}
