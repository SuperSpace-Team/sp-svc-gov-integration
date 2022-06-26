package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.weChat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/5/15
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 * Description: 企业微信发送消息配置信息
 */


public class WeChatCommand implements Serializable {


    private static final Long serialVersionUID =  -4525611478312759064L;

    /**
     * 租户编码
     */
    private String customerCode;
    /**
     * 微信模板编码
     */
    private String templateCode;
    /**
     * 企业微信单发地址
     */
    private String weChatId;
    /**
     * 多个微信群发，收信人地址
     */
    private List<String> weChatIds;
    /**
     * 模板参数getWeChatIds
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

    public List<String> getWeChatIds() {
        if (weChatIds == null) {
            weChatIds = new LinkedList<String>();
        }
        return weChatIds;
    }

    public void setWeChatIds(List<String> weChatIds) {
        this.weChatIds = weChatIds;
    }

    public Map<String, Object> getDataMap() {
        if (this.dataMap == null) {
            this.dataMap = new HashMap<String, Object>();
        }
        return dataMap;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public void putDataMap(String key, Object value) {
        getDataMap().put(key, value);
    }

}