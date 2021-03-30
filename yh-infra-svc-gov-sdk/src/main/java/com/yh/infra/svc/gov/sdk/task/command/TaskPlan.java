package com.yh.infra.svc.gov.sdk.task.command;

public class TaskPlan {

    //columns START
    private Long id;
    private Long taskId;
    private String param;
    private java.util.Date runPlanTime;
    private java.util.Date runRealTime;
    private Boolean result;
    private Integer compensateCount;
    private Boolean untriggerFlag; //add at 2018-7-16 @since 2.1.3
    //columns END

    public TaskPlan() {
    }

    public TaskPlan(
            Long id
    ) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskId(Long value) {
        this.taskId = value;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setRunPlanTime(java.util.Date value) {
        this.runPlanTime = value;
    }

    public java.util.Date getRunPlanTime() {
        return this.runPlanTime;
    }


    public void setRunRealTime(java.util.Date value) {
        this.runRealTime = value;
    }

    public java.util.Date getRunRealTime() {
        return this.runRealTime;
    }

    public void setResult(Boolean value) {
        this.result = value;
    }

    public Boolean getResult() {
        return this.result;
    }


    public Integer getCompensateCount() {
        return compensateCount;
    }

    public void setCompensateCount(Integer compensateCount) {
        this.compensateCount = compensateCount;
    }

    public Boolean getUntriggerFlag() {
        return untriggerFlag;
    }

    public void setUntriggerFlag(Boolean untriggerFlag) {
        this.untriggerFlag = untriggerFlag;
    }

}
