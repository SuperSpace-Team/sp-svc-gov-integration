package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CheckSmsCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -157380431232430189L;

	/**
	 * 日志ids 选填，与短信收信人receiver 必填一个
	 */
	private List<Long> ids;
	
	/**
	 * 短信收信人  选填，与日志id集合ids 必填一个
	 */
	private String receiver;
	
	/**
	 * 时间区间-开始时间 必填，开始时间和结束时间不能跨天
	 */
	private Date startTime;
	
	/**
	 * 时间区间--结束时间 必填 开始时间和结束时间不能跨天
	 */
	private Date endTime;

	/**
	 * 租户编码
	 */
	private String customerCode;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	

}
