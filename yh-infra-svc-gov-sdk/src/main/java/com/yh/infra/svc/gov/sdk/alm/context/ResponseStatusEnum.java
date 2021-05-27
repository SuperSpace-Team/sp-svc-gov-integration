package com.yh.infra.svc.gov.sdk.alm.context;

/**
 * @author luchao
 * @date 2021/4/25 8:24 下午
 */
public enum ResponseStatusEnum {
	SUCCESS(0), FAIL(1), REJECT(2);
	private int value;
	private ResponseStatusEnum(int v) {
		value = v;
	}
}
