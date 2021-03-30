package com.yh.infra.svc.gov.sdk.command;

/**
 * 用户权限返回对象
 * @author wenjin.gao
 * @Date 2016年1月12日  上午10:22:19
 * @Version 
 * @Description 
 *
 */
public class AccountAuthReturnObj {
	
	//返回状态(true: 正常返回; false: 异常)
	private boolean resultFlag;
	
	//返回对象
	private String data;
	
	//错误信息
	private String errorMsg;
	
	//错误编码
	private int errorCode;
	
	public AccountAuthReturnObj(){}
	
	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
		return "AccountAuthReturnObj [resultFlag=" + resultFlag + ", data=" + data + ", errorMsg=" + errorMsg + ", errorCode=" + errorCode + "]";
	}
	
}
