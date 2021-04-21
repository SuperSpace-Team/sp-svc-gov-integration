package com.yh.infra.svc.gov.sdk.command;

import java.util.ArrayList;
import java.util.List;

/**
 *  定时任务 非实时日志存在
 *  一个任务的process一般在10条以内
 * @author Alex Lu
 *
 */
public class TaskLogCommand {
	
	private List<String> processList;
	
	private List<String> errorInfoList;

	private Boolean result;

	
	public synchronized List<String> getProcessList() {
		return processList;
	}

	public synchronized void setProcessList(String process) {
		if(null == processList) {
			processList = new ArrayList<String>();
		}
		this.processList.add(process);
	}

	public List<String> getErrorInfoList() {
		return errorInfoList;
	}

	public void setErrorInfoList(String errorInfo) {
		if(null == errorInfoList) {
			errorInfoList = new ArrayList<String>();
		}
		this.errorInfoList.add(errorInfo);
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
}
