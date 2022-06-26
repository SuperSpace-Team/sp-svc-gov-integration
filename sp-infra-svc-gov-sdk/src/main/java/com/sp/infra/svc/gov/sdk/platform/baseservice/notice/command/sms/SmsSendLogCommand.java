package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询短信发送成功返回command对象
 * @author zhongbo.zhou
 *
 */
public class SmsSendLogCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1794634823665389561L;
	
	private Long logId;
	
	private Long templateId;
	
	private String reciver;
	
	private Boolean isSuccess;
	
	private Date createTime;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	
	
}
