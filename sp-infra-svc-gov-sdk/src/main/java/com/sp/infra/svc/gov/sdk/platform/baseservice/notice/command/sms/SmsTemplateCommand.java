package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms;

import java.io.Serializable;

public class SmsTemplateCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1301749744143403310L;
	
	
	/**
	 * 应用code
	 */
	private String customerCode;
	
	/**
	 * 模板code
	 */
	private String templateCode;
	
	/**
	 * 模板名称
	 */
	private String templateName;
	
	/**
	 * 模板描述
	 */
	private String desc;
	
	/**
	 * 短信的最大长度
	 */
	private Integer maxLength;
	
	/**
	 * 正文
	 */
	private String body;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	

}
