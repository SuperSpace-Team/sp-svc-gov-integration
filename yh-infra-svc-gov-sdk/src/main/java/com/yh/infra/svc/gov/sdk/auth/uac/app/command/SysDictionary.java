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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author lark
 *
 */
public class SysDictionary  {


    private static final long serialVersionUID = -708253166662697498L;

    protected Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    //alias
    public static final String TABLE_ALIAS = "SysDictionary";
    public static final String ALIAS_DIC_LABEL = "显示名称";
    public static final String ALIAS_DIC_VALUE = "数据值";
    public static final String ALIAS_DESCRIPTION = "描述";
    public static final String ALIAS_LIFECYCLE = "生命周期";
    public static final String ALIAS_GROUP_NAME = "分组";
    public static final String ALIAS_LANG = "lang";
    public static final String ALIAS_FUNCTION_NAME = "功能(高于分组，将不同的功能进行区分，可以为null)";
    public static final String ALIAS_ORDER_NUM = "排序号";
    
    //date formats
    
    //columns START
    private String dicLabel;
    private String dicValue;
    private String description;
    private Integer lifecycle;
    private String groupName;
    private String lang;
    private String functionName;
    private Integer orderNum;
    //columns END

    public SysDictionary(){
    }

    public SysDictionary(
        Long id
    ){
        this.id = id;
    }

    public void setDicLabel(String value) {
        this.dicLabel = value;
    }

    public String getDicLabel() {
        return this.dicLabel;
    }
    public void setDicValue(String value) {
        this.dicValue = value;
    }

    public String getDicValue() {
        return this.dicValue;
    }
    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }
    public void setLifecycle(Integer value) {
        this.lifecycle = value;
    }

    public Integer getLifecycle() {
        return this.lifecycle;
    }
    public void setGroupName(String value) {
        this.groupName = value;
    }

    public String getGroupName() {
        return this.groupName;
    }
    public void setLang(String value) {
        this.lang = value;
    }

    public String getLang() {
        return this.lang;
    }
    public void setFunctionName(String value) {
        this.functionName = value;
    }

    public String getFunctionName() {
        return this.functionName;
    }
    public void setOrderNum(Integer value) {
        this.orderNum = value;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
        .append("Id",getId())       
        .append("DicLabel",getDicLabel())       
        .append("DicValue",getDicValue())       
        .append("Description",getDescription())     
        .append("Lifecycle",getLifecycle())     
        .append("GroupName",getGroupName())     
        .append("Lang",getLang())       
        .append("FunctionName",getFunctionName())       
        .append("OrderNum",getOrderNum())       
            .toString();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        .append(getId())
        .append(getDicLabel())
        .append(getDicValue())
        .append(getDescription())
        .append(getLifecycle())
        .append(getGroupName())
        .append(getLang())
        .append(getFunctionName())
        .append(getOrderNum())
            .toHashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SysDictionary == false) return false;
        if(this == obj) return true;
        SysDictionary other = (SysDictionary)obj;
        return new EqualsBuilder()
        .append(getId(),other.getId())

        .append(getDicLabel(),other.getDicLabel())

        .append(getDicValue(),other.getDicValue())

        .append(getDescription(),other.getDescription())

        .append(getLifecycle(),other.getLifecycle())

        .append(getGroupName(),other.getGroupName())

        .append(getLang(),other.getLang())

        .append(getFunctionName(),other.getFunctionName())

        .append(getOrderNum(),other.getOrderNum())

            .isEquals();
    }
}

