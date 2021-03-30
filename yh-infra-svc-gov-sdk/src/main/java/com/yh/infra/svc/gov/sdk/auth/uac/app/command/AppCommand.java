package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用中心交互对象
 */
public class AppCommand implements Serializable {
    private static final long serialVersionUID = -987503705317547865L;

    public static final Integer APP_TYPE = 1;//应用租户

    public static final Integer BRAND_TYPE = 2;//品牌租户

    private Long id;

    private String appName;

    private String appkey;

    private String secret;

    private String authBackUrl;

    private java.util.Date createTime;

    private String ipwriteList;

    private String logoUrl;

    private Integer lifeCycle;

    private Integer accessTokenDuedate;

    private Boolean registerByUser;

    private String syncUserInfoUrl;

    private String syncUnitInfoUrl;

    private Integer type;//租户类型 0系统 1 品牌

    private Integer encryptDecryptType;//加解密类型（1.aes-默认;2.aes-通用）

    private Integer syncDelUserType;//删除用户是否同步（1.同步;2.不同步）

    private Boolean international = false;//是否支持国际化(0：不支持；1：支持)

    /**
     * id的获取.
     * 
     * @return Long
     */
    public Long getId(){
        return id;
    }

    /**
     * 设定id的值.
     * 
     * @param Long
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * appName的获取.
     * 
     * @return String
     */
    public String getAppName(){
        return appName;
    }

    /**
     * 设定appName的值.
     * 
     * @param appName
     */
    public void setAppName(String appName){
        this.appName = appName;
    }

    /**
     * appkey的获取.
     * 
     * @return String
     */
    public String getAppkey(){
        return appkey;
    }

    /**
     * 设定appkey的值.
     * 
     * @param appKey
     */
    public void setAppkey(String appKey){
        this.appkey = appKey;
    }

    /**
     * secret的获取.
     * 
     * @return String
     */
    public String getSecret(){
        return secret;
    }

    /**
     * 设定secret的值.
     * 
     * @param secret
     */
    public void setSecret(String secret){
        this.secret = secret;
    }

    /**
     * authBackUrl的获取.
     * 
     * @return String
     */
    public String getAuthBackUrl(){
        return authBackUrl;
    }

    /**
     * 设定authBackUrl的值.
     * 
     * @param authBackUrl
     */
    public void setAuthBackUrl(String authBackUrl){
        this.authBackUrl = authBackUrl;
    }

    /**
     * createTime的获取.
     * 
     * @return java.util.Date
     */
    public java.util.Date getCreateTime(){
        return createTime;
    }

    /**
     * 设定createTime的值.
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
     * ipwriteList的获取.
     * 
     * @return String
     */
    public String getIpwriteList(){
        return ipwriteList;
    }

    /**
     * 设定ipwriteList的值.
     * 
     * @param ipwriteList
     */
    public void setIpwriteList(String ipwriteList){
        this.ipwriteList = ipwriteList;
    }

    /**
     * logoUrl的获取.
     * 
     * @return String
     */
    public String getLogoUrl(){
        return logoUrl;
    }

    /**
     * 设定logoUrl的值.
     * 
     * @param String
     */
    public void setLogoUrl(String logoUrl){
        this.logoUrl = logoUrl;
    }

    /**
     * lifeCycle的获取.
     * 
     * @return Integer
     */
    public Integer getLifeCycle(){
        return lifeCycle;
    }

    /**
     * 设定lifeCycle的值.
     * 
     * @param Integer
     */
    public void setLifeCycle(Integer lifeCycle){
        this.lifeCycle = lifeCycle;
    }

    /**
     * accessTokenDuedate的获取.
     * 
     * @return Integer
     */
    public Integer getAccessTokenDuedate(){
        return accessTokenDuedate;
    }

    /**
     * 设定accessTokenDuedate的值.
     * 
     * @param Integer
     */
    public void setAccessTokenDuedate(Integer accessTokenDuedate){
        this.accessTokenDuedate = accessTokenDuedate;
    }

    /**
     * registerByUser的获取.
     * 
     * @return Boolean
     */
    public Boolean getRegisterByUser(){
        return registerByUser;
    }

    /**
     * 设定registerByUser的值.
     * 
     * @param Boolean
     */
    public void setRegisterByUser(Boolean registerByUser){
        this.registerByUser = registerByUser;
    }

    /**
     * syncUserInfoUrl的获取.
     * 
     * @return String
     */
    public String getSyncUserInfoUrl(){
        return syncUserInfoUrl;
    }

    /**
     * 设定syncUserInfoUrl的值.
     * 
     * @param String
     */
    public void setSyncUserInfoUrl(String syncUserInfoUrl){
        this.syncUserInfoUrl = syncUserInfoUrl;
    }

    /**
     * syncUnitInfoUrl的获取.
     * 
     * @return String
     */
    public String getSyncUnitInfoUrl(){
        return syncUnitInfoUrl;
    }

    /**
     * 设定syncUnitInfoUrl的值.
     * 
     * @param String
     */
    public void setSyncUnitInfoUrl(String syncUnitInfoUrl){
        this.syncUnitInfoUrl = syncUnitInfoUrl;
    }

    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getEncryptDecryptType(){
        return encryptDecryptType;
    }

    public void setEncryptDecryptType(Integer encryptDecryptType){
        this.encryptDecryptType = encryptDecryptType;
    }

    public Integer getSyncDelUserType(){
        return syncDelUserType;
    }

    public void setSyncDelUserType(Integer syncDelUserType){
        this.syncDelUserType = syncDelUserType;
    }

    public Boolean getInternational(){
        return international;
    }

    public void setInternational(Boolean international){
        this.international = international;
    }

}
