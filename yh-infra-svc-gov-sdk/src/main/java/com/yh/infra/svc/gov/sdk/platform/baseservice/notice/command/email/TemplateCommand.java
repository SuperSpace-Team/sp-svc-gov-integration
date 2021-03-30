package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email;

import java.io.Serializable;

public class TemplateCommand implements Serializable {

	private static final long serialVersionUID = -8495769975685123897L;
	
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
	 * title 标题
	 */
	private String title;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	

}
