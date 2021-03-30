package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * 返回提示.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-6-24 上午03:14:56
 * @since 1.0
 */
public class BackWarnEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/** 是否成功. */
	private boolean				isSuccess;

	/**
	 * 错误码,某些时候需要 设置 ,而前台判断(可选)
	 */
	private Integer errorCode			= null;

	/** 描述. */
	private Object description;
	
	
	/** 跳转地址 **/
	private String returnUrl;
	

	/**
	 * Instantiates a new back warn entity.
	 */
	public BackWarnEntity(){}

	/**
	 * Instantiates a new back warn entity.
	 * 
	 * @param isSuccess
	 *            the is success
	 * @param description
	 *            the description
	 */
	public BackWarnEntity(boolean isSuccess, Object description){
		this.isSuccess = isSuccess;
		this.description = description;
	}
	
	public BackWarnEntity(Integer errorCode, boolean isSuccess, Object description){
		this.isSuccess = isSuccess;
		this.description = description;
		this.errorCode=errorCode;
	}
	
	public BackWarnEntity(boolean isSuccess, String returnUrl){
		this.isSuccess = isSuccess;
		this.returnUrl = returnUrl;
	}
	
	public BackWarnEntity(boolean isSuccess, Object description, String returnUrl) {
		super();
		this.isSuccess = isSuccess;
		this.description = description;
		this.returnUrl = returnUrl;
	}

	/**
	 * Gets the 是否成功.
	 * 
	 * @return the isSuccess
	 */
	public boolean getIsSuccess(){
		return isSuccess;
	}



	/**
	 * Gets the 描述.
	 * 
	 * @return the description
	 */
	public Object getDescription(){
		return description;
	}



	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode(){
		return errorCode;
	}



	public String getReturnUrl() {
		return returnUrl;
	}



}
