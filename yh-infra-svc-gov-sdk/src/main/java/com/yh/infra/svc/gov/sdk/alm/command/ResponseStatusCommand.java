package com.yh.infra.svc.gov.sdk.alm.command;

import com.yh.infra.svc.gov.sdk.init.context.ResponseStatusEnum;

/**
 * 发送返回状态。发送给熔断守护服务使用。
 *
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:09 下午
 */
public class ResponseStatusCommand {
	private long timeStamp;
	private ResponseStatusEnum status;
	
	public static ResponseStatusCommand reject() {
		ResponseStatusCommand ret = new ResponseStatusCommand();
		ret.setTimeStamp(System.currentTimeMillis());
		ret.setStatus(ResponseStatusEnum.REJECT);
		return ret;
	}
	public static ResponseStatusCommand success() {
		ResponseStatusCommand ret = new ResponseStatusCommand();
		ret.setTimeStamp(System.currentTimeMillis());
		ret.setStatus(ResponseStatusEnum.SUCCESS);
		return ret;
	}
	public static ResponseStatusCommand fail() {
		ResponseStatusCommand ret = new ResponseStatusCommand();
		ret.setTimeStamp(System.currentTimeMillis());
		ret.setStatus(ResponseStatusEnum.FAIL);
		return ret;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ResponseStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ResponseStatusEnum status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ResponseStatusCommand [timeStamp=" + timeStamp + ", status=" + status + "]";
	}
	
}
