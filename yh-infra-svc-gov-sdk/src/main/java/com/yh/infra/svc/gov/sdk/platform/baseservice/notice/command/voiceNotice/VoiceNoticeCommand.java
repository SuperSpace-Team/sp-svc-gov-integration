package com.yh.infra.svc.gov.sdk.platform.baseservice.notice.command.voiceNotice;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 * Description: 语音电话配置信息
 */


public class VoiceNoticeCommand implements Serializable{

    private static final long serialVersionUID = -7713421178521334981L;

    /**
     * 租户编码
     */
    private String customerCode;
    /**
     * 语音消息模板编码
     */
    private String templateCode;
    /**
     * 接受语音消息手机号
     */
    private String mobile;
    /**
     * 模板参数
     */
    private Map<String, Object> dataMap;


    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Map<String, Object> getDataMap() {
        if(this.dataMap==null){
            dataMap=new ConcurrentHashMap<String, Object>();
        }
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public void putDataMap(String key, Object value) {
        getDataMap().put(key, value);
    }
}