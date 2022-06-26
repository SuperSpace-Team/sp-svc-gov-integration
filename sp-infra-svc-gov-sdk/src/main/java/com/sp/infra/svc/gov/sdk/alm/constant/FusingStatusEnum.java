package com.sp.infra.svc.gov.sdk.alm.constant;

/**
 * @author luchao
 * @date 2021/4/25 6:11 下午
 */
public enum FusingStatusEnum {
	OPEN(0),
	CLOSE(1),
	HALF(2);

	private int value;

	private FusingStatusEnum(int v) {
		value = v;
	}
}
