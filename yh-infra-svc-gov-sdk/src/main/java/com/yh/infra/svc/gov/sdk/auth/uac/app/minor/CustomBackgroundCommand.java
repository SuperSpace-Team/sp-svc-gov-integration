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
package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomBackgroundCommand implements Serializable {

    private static final long serialVersionUID = -2925471059280693712L;

    protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    protected static final String DATE_TIME_FORMAT1 = "yyyy-MM-dd";

    private Long id;

    private String appkey;

    private String imgurl;

    private Integer isapp;//(0：平时；1：节日)

    private java.util.Date starttime;

    private String starttimeString;

    private java.util.Date endtime;

    private String endtimeString;

    private String creator;

    private java.util.Date createtime;

    private String editor;

    private java.util.Date updatetime;

    private Integer lifecycle;//(0：禁用；1：正常)

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setAppkey(String value){
        this.appkey = value;
    }

    public String getAppkey(){
        return this.appkey;
    }

    public String getImgurl(){
        return imgurl;
    }

    public void setImgurl(String imgurl){
        this.imgurl = imgurl;
    }

    public Integer getIsapp(){
        return isapp;
    }

    public void setIsapp(Integer isapp){
        this.isapp = isapp;
    }

    public java.util.Date getStarttime(){
        return starttime;
    }

    public void setStarttime(java.util.Date starttime){
        this.starttime = starttime;
        this.starttimeString = new SimpleDateFormat(DATE_TIME_FORMAT).format(starttime);
    }

    public java.util.Date getEndtime(){
        return endtime;
    }

    public void setEndtime(java.util.Date endtime){
        this.endtime = endtime;
        this.endtimeString = new SimpleDateFormat(DATE_TIME_FORMAT).format(endtime);
    }

    public String getCreator(){
        return creator;
    }

    public void setCreator(String creator){
        this.creator = creator;
    }

    public java.util.Date getCreatetime(){
        return createtime;
    }

    public void setCreatetime(java.util.Date createtime){
        this.createtime = createtime;
    }

    public String getEditor(){
        return editor;
    }

    public void setEditor(String editor){
        this.editor = editor;
    }

    public java.util.Date getUpdatetime(){
        return updatetime;
    }

    public void setUpdatetime(java.util.Date updatetime){
        this.updatetime = updatetime;
    }

    public Integer getLifecycle(){
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle){
        this.lifecycle = lifecycle;
    }

    public String getStarttimeString(){
        return starttimeString;
    }

    public String getEndtimeString(){
        return endtimeString;
    }

    public void setStarttimeString(String starttimeString){
        this.starttimeString = starttimeString;
        try{
            this.starttime = new SimpleDateFormat(DATE_TIME_FORMAT).parse(starttimeString);
        }catch (ParseException e){
            try{
                this.starttime = new SimpleDateFormat(DATE_TIME_FORMAT1).parse(starttimeString);
            }catch (ParseException e1){
                e1.printStackTrace();
            }
        }
    }

    public void setEndtimeString(String endtimeString){
        this.endtimeString = endtimeString;
        try{
            this.endtime = new SimpleDateFormat(DATE_TIME_FORMAT).parse(endtimeString);
        }catch (ParseException e){
            try{
                this.endtime = new SimpleDateFormat(DATE_TIME_FORMAT1).parse(endtimeString);
            }catch (ParseException e1){
                e1.printStackTrace();
            }
        }
    }
}
