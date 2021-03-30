/**
 * Copyright (c) 2012 YongHui All Rights Reserved.
 *
 * This software is the confidential and proprietary information of YongHui.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with YongHui.
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


/**
 * 用户、组织、角色的关联表<br/>
 * 有三个外键<br/>
 * <li>用户表</li>
 * <li>组织表</li>
 * <li>角色表</li>
 * AU_USER_ROLE_OU
 * @author 李光辉
 *
 */
public class UserRole extends BaseModel {

    private static final long serialVersionUID = 3370648468453284200L;

	/** 用户 */
	private Long userId;
    
    /** 角色 */
    private Long roleId;

	/** 组织ID */
	private Long ouId;
	
	private Long appId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
    
}
