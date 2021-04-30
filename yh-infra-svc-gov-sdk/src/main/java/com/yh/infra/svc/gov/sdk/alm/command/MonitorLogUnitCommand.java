package com.yh.infra.svc.gov.sdk.alm.command;

import lombok.Data;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:05 下午
 */
@Data
public class MonitorLogUnitCommand {
	private int code;
	private String key;
	private String inLog;
	private String outLog;
	private String exceptionLog;
}
