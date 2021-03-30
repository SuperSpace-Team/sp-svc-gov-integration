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
 * 用户
 * AU_USER
 * @author 李光辉
 * 
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7810928960965567029L;

    private Long id;
    
    /** 用户所属的组织 */
    private Long ouId;

    /** 登录名 */
    private String loginName;
    
    /** 用户姓名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 用户帐号是否未过期，过期帐号无法登录系统 */
    private Boolean isAccNonExpired = true;

    /** 用户帐号是否未被锁定，被锁定的用户无法使用系统 */
    private Boolean isAccNonLocked = true;

    private Integer lifecycle ;

    /** 创建时间 */
    private Date createTime;

    /** 最后修改时间 */
    private Date latestUpdateTime;

    /** 最后登录时间 */
    private Date latestAccessTime;

    /** 邮箱 */
    private String email;
    
    /** 工号 */
    private String jobNumber;

    /** 备注 */
    private String memo;
    
    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getIsAccNonExpired() {
        return isAccNonExpired;
    }

    public void setIsAccNonExpired(Boolean isAccNonExpired) {
        this.isAccNonExpired = isAccNonExpired;
    }

    public Boolean getIsAccNonLocked() {
        return isAccNonLocked;
    }

    public void setIsAccNonLocked(Boolean isAccNonLocked) {
        this.isAccNonLocked = isAccNonLocked;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public Date getLatestAccessTime() {
        return latestAccessTime;
    }

    public void setLatestAccessTime(Date latestAccessTime) {
        this.latestAccessTime = latestAccessTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

}
