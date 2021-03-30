/**
 * Copyright (c) 2013 YongHui All Rights Reserved.
 *
 * This software is the confidential and proprietary information of YongHui.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with YongHui.
 *
 * YongHui MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. YongHui SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class InternationalCommand implements Serializable {

    private static final long serialVersionUID = 358797817463150227L;

    private Long id;

    private String name;

    private String code;

    private String lang;

    public InternationalCommand(){
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getLang(){
        return lang;
    }

    public void setLang(String lang){
        this.lang = lang;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this).append("Id", getId()).append("Name", getName()).append("Lang", getLang()).append("Code", getCode()).toString();
    }
}
