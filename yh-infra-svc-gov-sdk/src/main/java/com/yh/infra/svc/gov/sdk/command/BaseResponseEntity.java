package com.yh.infra.svc.gov.sdk.command;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此实体为通用后端反馈前端实体对象<br/>
 * {<br/>
 * --"isSuccess":"true/false", --根据这个区分业务成功与否<br/>
 * --"responseTime":"2017-01-01 14:00:00", --响应时间<br/>
 * --"code":"00001",--这个是后端的errorCode,便于后端快�?�定位问�?<br/>
 * --"msg":"商品不存在? ", --这个是前端需要提示的信息<br/>
 * --"data":"Array<>[]", --这个类似于查询反馈的结果<br/>
 * --"stackTrace":".....",--异常堆栈信息<br/>
 * }<br/>
 * 
 * 
 */
public class BaseResponseEntity<VO> implements Serializable {

	/**
	 * 成功的状态
	 */
	public static final String SUCCESS_CODE="200";
	
    private static final long serialVersionUID = 3681501520884268066L;
    // 是否执行成功并无业务异常，在没有抛出业务异常的情况下，默认为true，正常返回data 如查询数据列表等，如果为false,则需要封装code和msg，前端根据msg进行相关提示
    private Boolean isSuccess = true;
    // 响应时间 :2017-01-01 14:00:00
    private String responseTime;
    // 此处为业务异常编码如0001，跟msg成对 msg的取值应该为 bussiness_exception_+code在国际化文件里面查找
    // 如果没有异常，此处为200
    private String code;
    // 响应描述，错误信息
    private String msg;
    // json对象
    private VO data;

    // exception info
    private String stackTrace;
    
    public static final BaseResponseEntity<?> success = new BaseResponseEntity<>();

    public BaseResponseEntity() {}

    public BaseResponseEntity(int code, String msg) {
        this.isSuccess = false;
        this.code = code + "";
        this.msg = msg;
    }

    public BaseResponseEntity(Boolean isSuccess, String responseTime, String code, String msg, VO data) {
        this.isSuccess = isSuccess;
        this.responseTime = responseTime;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponseEntity(Boolean isSuccess, String code, String msg, VO data) {
        this.isSuccess = isSuccess;
        this.responseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public BaseResponseEntity<VO> error(String code, String msg) {
    	isSuccess = false;
    	this.code = code;
    	this.msg = msg;
    	return this;
    }
    public BaseResponseEntity<VO> error(int code, String msg) {
    	isSuccess = false;
    	this.code = code + "";
    	this.msg = msg;
    	return this;
    }
    public void reset() {
    	isSuccess = false;
    	code = null;
    	msg = null;
    	data = null;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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


    public String getStackTrace() {
        return stackTrace;
    }


    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

   
}
