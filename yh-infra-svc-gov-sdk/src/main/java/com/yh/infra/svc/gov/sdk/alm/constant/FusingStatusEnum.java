package com.yh.infra.svc.gov.sdk.alm.constant;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
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
