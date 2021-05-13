package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * @Description
 * @Author Ice丶ola
 * @date 2021.05.11
 */
public class OperationUnitType implements Serializable {

    private static final long serialVersionUID = 4462888646421810987L;

    private Long id;

    /** 组织类型简称/编码 */
    private String code;

    /** 组织类型全称 */
    private String name;

    /** 组织类型描述 */
    private String description;

    /** 父级组织类型 */
    private Long parentUnitTypeId;

    private Long parentOutId;

    private Long appId;

    public OperationUnitType() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentUnitTypeId() {
        return parentUnitTypeId;
    }

    public void setParentUnitTypeId(Long parentUnitTypeId) {
        this.parentUnitTypeId = parentUnitTypeId;
    }

    /**
     * parentOutId的获取.
     * @return Long
     */
    public Long getParentOutId() {
        return parentOutId;
    }

    /**
     * 设定parentOutId的值.
     * @param parentOutId
     */
    public void setParentOutId(Long parentOutId) {
        this.parentOutId = parentOutId;
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
     * @param appId
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
