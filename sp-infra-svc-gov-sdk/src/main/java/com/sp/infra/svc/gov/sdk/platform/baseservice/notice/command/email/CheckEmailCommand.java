package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.email;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 获取短信发送日志数据
 * @author zhongbo.zhou
 *
 */
public class CheckEmailCommand implements Serializable{

	
	private static final long serialVersionUID = -549083949582295738L;
	
	/**
	 * 日志ids 选填，与收件人receiver 必填一个
	 */
	private List<Long> ids;
	
	/**
	 * 收件人  选填，与日志id集合ids 必填一个
	 */
	private String receiver;
	
	/**
	 * 时间区间-开始时间 必填
	 */
	private Date startTime;
	
	/**
	 * 时间区间--结束时间 必填
	 */
	private Date endTime;
	
	/**
	 * appKey 必填
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
