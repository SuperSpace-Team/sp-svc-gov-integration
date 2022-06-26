package com.sp.infra.svc.gov.sdk.command;

/**
 * 
 */
public class BaseReturnObj<VO> {

    private boolean success = true;
    private int code;
    private String msg;
    private VO data;

    public BaseReturnObj() {}

    public BaseReturnObj(int code, String msg) {
        this.success = false;
        this.code = code;
        this.msg = msg;
    }
    public BaseReturnObj(int code, String msg, VO data) {
        this.success = false;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseReturnObj<VO> error(int code, String msg) {
    	success = false;
    	this.code = code;
    	this.msg = msg;
    	return this;
    }
    public BaseReturnObj<VO> error(int code, String msg, VO data) {
    	success = false;
    	this.code = code;
    	this.msg = msg;
    	this.data = data;
    	return this;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public VO getData() {
        return data;
    }

    public void setData(VO data) {
        this.data = data;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "BaseReturnObj [success=" + success + ", code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

   
}
