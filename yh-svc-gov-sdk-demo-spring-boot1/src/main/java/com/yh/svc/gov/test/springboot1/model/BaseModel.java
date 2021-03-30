package com.yh.svc.gov.test.springboot1.model;

public class BaseModel extends com.yh.common.lark.common.model.BaseModel{
    private static final long serialVersionUID = 1L;

    /**
     * 正常
     */
    public static final Integer LIFECYCLE_NORMAL = 1;

    /**
     * 禁用
     */
    public static final Integer LIFECYCLE_DISABLE = 0;

    /**
     * 已删除
     */
    public static final Integer LIFECYCLE_DELETED = 2;

    public static final int SUCCESS_RESULT = 1;//成功

    public static final int ERROR_RESULT = 0;//失败
    
    public static final String ERROR_MSG_EXISTS_CODE="code已存在";

    protected String errorCode;

    protected String resultMsg = "成功";

    protected int result = SUCCESS_RESULT;//0-失败  1-成功

    
    
    protected java.lang.Integer version;
    protected java.lang.Integer code;
	protected java.lang.Boolean lifecycle;
	private java.lang.String operator;
	private java.util.Date createTime;
	private java.util.Date lastModifiedTime;


	public void setVersion(java.lang.Integer value) {
		this.version = value;
	}
	
	public java.lang.Integer getVersion() {
		return this.version;
	}
	public void setCode(java.lang.Integer value) {
		this.code = value;
	}
	public java.lang.Integer getCode() {
		return this.code;
	}
	public void setLifecycle(java.lang.Boolean value) {
		this.lifecycle = value;
	}
	
	public java.lang.Boolean getLifecycle() {
		return this.lifecycle;
	}
	public void setOperator(java.lang.String value) {
		this.operator = value;
	}
	
	public java.lang.String getOperator() {
		return this.operator;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setLastModifiedTime(java.util.Date value) {
		this.lastModifiedTime = value;
	}
	
	public java.util.Date getLastModifiedTime() {
		return this.lastModifiedTime;
	}
	
	
    public String getErrorCode(){
        return errorCode;
    }

    
    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    
    public String getResultMsg(){
        return resultMsg;
    }

    
    public void setResultMsg(String resultMsg){
        this.resultMsg = resultMsg;
    }

    
    public int getResult(){
        return result;
    }

    
    public void setResult(int result){
        this.result = result;
    }
    
    
}
