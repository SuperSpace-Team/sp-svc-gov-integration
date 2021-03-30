package com.yh.infra.svc.gov.sdk.auth.uac.account;

import java.util.Date;

/**
 * @author hsh10697
 */
public class SaasTenantInfo {

    private Long id;
    /**
     * 租户号
     */
    private String saasTenantCode;
    /**
     * 租户名称
     */
    private String saasTenantName;
    /**
     * 0:有效记录 1:逻辑删除
     */
    private Boolean deleteFlag;
    /**
     * 创建人ID
     */
    private Long creatorId;

    private Date createdAt;
    /**
     * 修改人ID
     */
    private Long modifierId;

    private Date modifiedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaasTenantCode() {
        return saasTenantCode;
    }

    public void setSaasTenantCode(String saasTenantCode) {
        this.saasTenantCode = saasTenantCode;
    }

    public String getSaasTenantName() {
        return saasTenantName;
    }

    public void setSaasTenantName(String saasTenantName) {
        this.saasTenantName = saasTenantName;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
