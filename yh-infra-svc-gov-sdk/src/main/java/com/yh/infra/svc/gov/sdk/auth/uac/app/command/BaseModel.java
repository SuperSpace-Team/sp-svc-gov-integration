package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import com.yh.infra.svc.gov.sdk.util.DateConvertUtils;

import java.io.Serializable;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = -1494181848524897472L;


	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


    /**
	 * 正常
	 */
	public static final Integer LIFECYCLE_NORMAL=1;
	
	/**
	 * 禁用
	 */
	public static final Integer LIFECYCLE_DISABLE=0;
	
	/**
	 * 已删除
	 */
	public static final Integer LIFECYCLE_DELETED=2;
	
	
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public static String date2String(java.util.Date date, String dateFormat) {
		return DateConvertUtils.format(date, dateFormat);
	}

	public static <T extends java.util.Date> T string2Date(String dateString, String dateFormat,
                                                           Class<T> targetResultType) {
		return DateConvertUtils.parse(dateString, dateFormat, targetResultType);
	}
}
