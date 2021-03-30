package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.email;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取邮件日志详细返回信息
 *
 */
public class EmailSendLogCommand implements Serializable{


	private static final long serialVersionUID = 4649042858302723672L;
	
	/**
	 * 日志id
	 */
	private Long logId;
	
	/**
	 * 邮件模板id
	 */
	private Long templateId;
	
	/**
	 * 收件人
	 */
	private String reciver;
	
	/**
	 * 当前状态 5 初始化, 1 发送成功0发送失败
	 */
	private Boolean isSuccess;
	/**
	 * 发送时间
	 */
	private Date createTime;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}
	

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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
	
	
	

}
