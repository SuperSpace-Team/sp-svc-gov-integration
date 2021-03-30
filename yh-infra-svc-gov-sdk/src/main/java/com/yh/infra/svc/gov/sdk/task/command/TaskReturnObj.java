package com.yh.infra.svc.gov.sdk.task.command;

import java.io.Serializable;

/**
 * 触发器调用返回的信息
 * @author wenjin.gao
 * @Date 2016年1月12日  上午10:22:19
 * @Version 
 * @Description 
 */
public class TaskReturnObj implements Serializable{
	/**
     * UID
     */
    private static final long serialVersionUID = 6270864505974370748L;

    //返回状态(true: 正常返回; false: 异常)
	private boolean resultFlag;
	
	//返回对象
	private Object data;
	
	//错误信息
	private String errorMsg;
	
	//错误编码
	private int errorCode;
	
	public TaskReturnObj() {
	    
	}
	
	public TaskReturnObj (boolean result,Object data) {
		this.resultFlag = result;
		this.data = data;
	}
	
	public TaskReturnObj (boolean result,Object data,int errorCode) {
		this.resultFlag = result;
		this.data = data;
		this.errorCode = errorCode;
	}
	
	public TaskReturnObj (boolean result,Object data,int errorCode, String errorMsg) {
        this.resultFlag = result;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

    @Override
    public String toString() {
        return "TaskReturnObj [resultFlag=" + this.isResultFlag() 
            + ", data=" + this.getData() 
            + ", errorMsg=" + this.getErrorMsg()
            + ", errorCode=" + this.getErrorCode() 
            + "]";
    }
	
	


}
