package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.sms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信参数command
 * @Description:短信目前支持单个接收人
 * @Version:1.0
 */
public class SmsCommand implements Serializable {


	private static final long serialVersionUID = -7782049941419763445L;

	/**
	 * @Title mobile
	 * @type String
	 * @date 2016年1月12日 下午6:03:13
	 * 含义 手机号码，必填
	 */
	private String mobile;

	/**
	 * @Title templateCode
	 * @type String
	 * @date 2015年12月28日 下午2:36:23
	 * 含义 模板编码
	 */
	private String templateCode;
	
	/**
	 * @Title customerCode
	 * @type String
	 * @date 2015年12月28日 下午2:36:29
	 * 含义 客户编码
	 */
	private String customerCode;
	
	/**
	 * @Title dataMap
	 * @type Map<String,Object>
	 * @date 2015年12月29日 上午10:18:51
	 * 含义 模板中的变量
	 */
	private Map<String, Object> dataMap;

	/**
	 * @Description 获得模板变量列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:19:09
	 * @return
	 */
	public Map<String, Object> getDataMap() {
		if (this.dataMap == null) {
			this.dataMap = new HashMap<String, Object>(5);
		}
		return dataMap;
	}
	
	/**
	 * @Description 设置模板变量列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:19:21
	 * @param dataMap
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @Description 添加模板变量的值
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2015年12月29日 上午10:20:30
	 * @param key		模板中的变量名称
	 * @param value		模板中的变量值
	 */
	public void putDataMap(String key, Object value) {
		getDataMap().put(key, value);
	}

	/**
	 * @Description 获得模板编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:19:55
	 * @return
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @Description 设置模板编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:20:06
	 * @param templateCode
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @Description 获得租户编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:20:47
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @Description 设置租户编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:21:00
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * mobile的获取.
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @Description 设置手机号码
	 * 手机号码如果有国际区号，需要去掉前面的+号
	 * 手机号码正则表达式规则配置在  pattern.sms.regex 属性中
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午6:07:15
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
