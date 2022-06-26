package com.sp.infra.svc.gov.sdk.task.command;

public class TaskPlanCommand extends TaskPlan {

    /**
     *
     */
    private static final long serialVersionUID = -3169236809850646896L;
    /**
     * 任务编码
     */
    private String taskCode;
    /**
     * s是否补偿 int，1为已补偿，0未补偿
     */
    private Integer isIntCompensated;
    /**
     * 租户编码
     */
    private String customerCode;

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public Integer getIsIntCompensated() {
        return isIntCompensated;
    }

    public void setIsIntCompensated(Integer isIntCompensated) {
        this.isIntCompensated = isIntCompensated;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
