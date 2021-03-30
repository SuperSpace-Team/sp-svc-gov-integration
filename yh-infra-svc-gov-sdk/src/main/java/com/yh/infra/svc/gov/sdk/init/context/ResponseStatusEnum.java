/**
 * 
 */
package com.yh.infra.svc.gov.sdk.init.context;

/**
 * @author luchao 2018-12-21
 *
 */
public enum ResponseStatusEnum {
	SUCCESS(0), FAIL(1), REJECT(2);

	private int value;
	private ResponseStatusEnum(int v) {
		value = v;
	}
}
