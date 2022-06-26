package com.sp.infra.svc.gov.sdk.task.command;

public class TaskLog {


    //columns START
    private Long id;
    private Long taskId;
    private Long taskPlanId;
    private java.util.Date triggerStartTime;
    private java.util.Date triggerEndTime;
    private java.util.Date runEndTime;
    private String process;
    private String errorInfo;
    //columns END

    public TaskLog() {
    }

    public TaskLog(
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

    public void setTaskPlanId(Long value) {
        this.taskPlanId = value;
    }

    public Long getTaskPlanId() {
        return this.taskPlanId;
    }


    public void setTriggerStartTime(java.util.Date value) {
        this.triggerStartTime = value;
    }

    public java.util.Date getTriggerStartTime() {
        return this.triggerStartTime;
    }


    public void setTriggerEndTime(java.util.Date value) {
        this.triggerEndTime = value;
    }

    public java.util.Date getTriggerEndTime() {
        return this.triggerEndTime;
    }


    public void setRunEndTime(java.util.Date value) {
        this.runEndTime = value;
    }

    public java.util.Date getRunEndTime() {
        return this.runEndTime;
    }

    public void setProcess(String value) {
        this.process = value;
    }

    public String getProcess() {
        return this.process;
    }

    public void setErrorInfo(String value) {
        this.errorInfo = value;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

}
