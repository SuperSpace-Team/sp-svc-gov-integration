package com.sp.infra.svc.gov.sdk.command;

import java.io.Serializable;

/**
 * HttpJsonResponse
 * *http返回对象
 * @time: 下午7:19:35
 * @author mazan
 */
public class HttpClientResponse implements Serializable {
    /**
     * _请求成功
     */
    private static final int SUCCESS = 200;
    /**
     * UID
     */
    private static final long serialVersionUID = -5883767102336209653L;

    private Integer statusCode; //状态码
    
    private String entity; // 返回值
    
    private String message; 
    
    private String error;

    /**
     * _请求是否成功
     * @return
     */
    public boolean isOk() {
        return this.statusCode == SUCCESS;
    }
    
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
	public String toString() {
		return "HttpClientResponse [statusCode=" + statusCode + ", entity=" + entity + ", message=" + message + ", error=" + error + "]";
	}
    
    
    
    
}

