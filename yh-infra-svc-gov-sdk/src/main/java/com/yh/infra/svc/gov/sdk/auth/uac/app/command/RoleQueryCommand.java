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
 package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;


public class RoleQueryCommand implements Serializable {
	
	private static final long serialVersionUID = 5923776309709928903L;
	
	/** 主键 */
	private Long id;
	/** 角色名称 */
	private String name;
	/** 1.可用;2.已删除;0.禁用 */
	private Integer lifecycle;
	/** 组织类型 */
	private Long ouTypeId;
	
	private String ouName;

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long value) {
		this.id = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public Integer getLifecycle() {
		return this.lifecycle;
	}
	
	public void setLifecycle(Integer value) {
		this.lifecycle = value;
	}
	
	public Long getOuTypeId() {
		return this.ouTypeId;
	}
	
	public void setOuTypeId(Long value) {
		this.ouTypeId = value;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}
	

	
}

