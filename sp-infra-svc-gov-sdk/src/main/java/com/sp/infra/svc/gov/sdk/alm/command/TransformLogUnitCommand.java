package com.sp.infra.svc.gov.sdk.alm.command;

import lombok.Data;

/**
 * @author luchao
 * @date 2021/4/25 6:05 下午
 */
@Data
public class TransformLogUnitCommand {
	private int code;
	private String srcKey;
	private String targetKey;
}
