package com.yh.infra.svc.gov.sdk.alm.command;

import com.yh.infra.svc.gov.sdk.init.context.ResponseStatusEnum;

/**
 * 计数器，熔断用
 *
 * @author luchao
 * @date 2021/4/25 6:13 下午
 */
public class FusingBucket {
	private long timeStamp = -1;
	private int totalNum;
	private int successNum;
	private int failNum;   // reject，fail的都在这计数。
	private FusingBucket next = null;
	private FusingBucket previous = null;

	public synchronized void clear(long newTs) {
		timeStamp = newTs;
		successNum = 0;
		failNum = 0;
		totalNum = 0;
	}

	public synchronized void increase(ResponseStatusEnum status) {
		switch (status) {
		case SUCCESS: {
			successNum++;
			break;
		}
		case FAIL: 
		case REJECT: {
			failNum++;
			break;
		}
		}
		totalNum++;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getSuccessNum() {
		return successNum;
	}

	public int getFailNum() {
		return failNum;
	}

	public FusingBucket getNext() {
		return next;
	}

	public void setNext(FusingBucket next) {
		this.next = next;
	}

	public FusingBucket getPrevious() {
		return previous;
	}

	public void setPrevious(FusingBucket previous) {
		this.previous = previous;
	}

	public int getTotalNum() {
		return totalNum;
	}

	@Override
	public String toString() {
		return "FusingBucket [timeStamp=" + timeStamp + ", totalNum=" + totalNum + ", successNum=" + successNum + ", failNum=" + failNum + "]";
	}

}
