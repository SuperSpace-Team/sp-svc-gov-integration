/**
 * Copyright (c) 2013 Yonghui All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yonghui.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Yonghui.
 *
 * YONGHUI MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. YONGHUI SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
 package com.yh.svc.gov.test.springboot1.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 
 * @author larkark
 *
 */
public class PgAppSystem extends BaseModel {
	private static final long serialVersionUID = 1659806803369631872L;
	//alias
	public static final String TABLE_ALIAS = "PgAppSystem";
	public static final String ALIAS_VERSION = "乐观锁";
	public static final String ALIAS_APP_ID = "应用的key";
	public static final String ALIAS_APP_NAME = "应用名称";
	public static final String ALIAS_LIFECYCLE = "0: 禁用     1:启用";
	public static final String ALIAS_OPERATOR = "最后修改的操作员ID";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_LAST_MODIFIED_TIME = "lastModifiedTime";
	
	//columns START
	private String appId;
	//columns END

	public PgAppSystem(){
	}

	public PgAppSystem(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setAppId(java.lang.String value) {
		this.appId = value;
	}
	
	public java.lang.String getAppId() {
		return this.appId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Version",getVersion())		
		.append("AppId",getAppId())		
		.append("Lifecycle",getLifecycle())		
		.append("Operator",getOperator())		
		.append("CreateTime",getCreateTime())		
		.append("LastModifiedTime",getLastModifiedTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVersion())
		.append(getAppId())
		.append(getLifecycle())
		.append(getOperator())
		.append(getCreateTime())
		.append(getLastModifiedTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof PgAppSystem == false) return false;
		if(this == obj) return true;
		PgAppSystem other = (PgAppSystem)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVersion(),other.getVersion())

		.append(getAppId(),other.getAppId())

		.append(getLifecycle(),other.getLifecycle())

		.append(getOperator(),other.getOperator())

		.append(getCreateTime(),other.getCreateTime())

		.append(getLastModifiedTime(),other.getLastModifiedTime())

			.isEquals();
	}
}

