/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织 AU_OPERATION_UNIT
 * 
 * @author 李光辉
 * 
 */
public class OperationUnit implements Serializable {

    private static final long serialVersionUID = -3182027629656273218L;
	
    private Long id;

    /** 组织编码 */
    private String code;

    /** 组织名称 */
    private String name;

    /** 组织全称 */
    private String fullName;

    private Integer lifecycle;

    /** 组织类型 */
    private Long ouTypeId;

    /** 父组织 */
    private Long parentUnitId;

    /** 备注 */
    private String comment;

    /** 最后修改时间 */
    private Date lastModifyTime;
    
	private Long appId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getOuTypeId() {
        return ouTypeId;
    }

    public void setOuTypeId(Long ouTypeId) {
        this.ouTypeId = ouTypeId;
    }

    public Long getParentUnitId() {
        return parentUnitId;
    }

    public void setParentUnitId(Long parentUnitId) {
        this.parentUnitId = parentUnitId;
    }
    
    public void setParentOuId(Long parentOuId) {
        this.parentUnitId = parentOuId;
    }
    
    public Long getParentOuId() {
        return parentUnitId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getOuComment() {
        return comment;
    }

    public void setOuComment(String ouComment) {
        this.comment = ouComment;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * appId的获取.
	 * @return Long
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 * 设定appId的值.
	 * @param Long
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}
    
}
