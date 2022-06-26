package com.sp.infra.svc.gov.sdk.platform.baseservice.notice.command.email;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发邮件参数
 */
public class EmailCommand  implements Serializable {

	private static final long serialVersionUID = 3845638922062409953L;

	/**
	 * @Title templateCode
	 * @type String
	 * @date 2015年12月28日 下午2:36:23
	 * 含义 模板编码，必须
	 */
	private String templateCode;
	
	/**
	 * @Title customerCode
	 * @type customerCode
	 * @date 2015年12月28日 下午2:36:29
	 * 含义 客户编码，必须
	 */
	private String customerCode;
	
	/**
	 * @Title to
	 * @type List<String>
	 * @date 2015年12月28日 下午2:35:39
	 * 含义 邮件接收者的地址，必须，至少有一个收件人
	 */
	private List<String> to;
	
	/**
	 * @Title cc
	 * @type List<String>
	 * @date 2015年12月28日 下午2:35:44
	 * 含义 抄送的地址，可选
	 */
	private List<String> cc;
	
	/**
	 * @Title bcc
	 * @type List<String>
	 * @date 2015年12月28日 下午2:35:44
	 * 含义 密送的地址，可选
	 */
	private List<String> bcc;
	
	/**
	 * @Title attachFile
	 * @type Map<String,String>
	 * @date 2015年12月28日 下午2:36:07
	 * 含义 邮件附件
	 * 在common-utilities-1.3.6.jar中工具类
	 * com.sp.framework.utilities.type.FileUtil.readToByte可转换byte[]
	 */
	private Map<String, byte[]> attachFile;
	
	/**
	 * @Title dataMap
	 * @type Map<String,Object>
	 * @date 2015年12月29日 上午10:18:51
	 * 含义 模板中的变量
	 */
	private Map<String, Object> dataMap;
	/**
	 * @Description 添加附件
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月11日 下午6:20:31
	 * @param fileName	附件显示名称
	 * @param bytes		附件转化的流
	 * @throws IOException
	 */
	public void putAttachFile(String fileName, byte[] bytes) throws IOException {
		getAttachFile().put(fileName, bytes);
	}

	public Map<String, byte[]> getAttachFile() {
		if (this.attachFile == null) {
			this.attachFile = new HashMap<String, byte[]>(5);
		}
		return attachFile;
	}

	/**
	 * @Description 设置附件
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:12:41
	 * @param attachFile
	 */
	public void setAttachFile(Map<String, byte[]> attachFile) {
		this.attachFile = attachFile;
	}
	
	/**
	 * @Description 获得收件人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:13:56
	 * @return
	 */
	public List<String> getTo() {
		if(this.to == null) {
			this.to = new ArrayList<String>(0);
		}
		return to;
	}

	/**
	 * @Description 设置收件人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:14:18
	 * @param to
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}
	
	/**
	 * @Description 添加接收人地址
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2015年12月28日 下午2:35:05
	 * @param to
	 */
	public void addTo(String... to) {
		for(String tmp : to) {
			this.getTo().add(tmp);
		}
	}

	/**
	 * @Description 获得抄送人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:14:54
	 * @return
	 */
	public List<String> getCc() {
		if(this.cc == null) {
			this.cc = new ArrayList<String>(0);
		}
		return cc;
	}

	/**
	 * @Description 设置抄送人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:15:06
	 * @param cc
	 */
	public void setCc(List<String> cc) {
		this.cc = cc;
	}
	/**
	 * @Description 添加抄送地址
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2015年12月28日 下午2:34:45
	 * @param cc
	 */
	public void addCc(String... cc) {
		for(String tmp : cc) {
			this.getCc().add(tmp);
		}
	}
	
	/**
	 * @Description 获得密送人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:15:36
	 * @return
	 */
	public List<String> getBcc() {
		if(this.bcc == null) {
			this.bcc = new ArrayList<String>(0);
		}
		return bcc;
	}

	/**
	 * @Description 设置密送人地址列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:15:52
	 * @param bcc
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}
	/**
	 * @Description 添加密送地址
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2015年12月28日 下午2:34:45
	 * @param bcc
	 */
	public void addBcc(String... bcc) {
		for(String tmp : bcc) {
			this.getBcc().add(tmp);
		}
	}

	/**
	 * @Description 获得附件列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:16:09
	 * @return
	 */
	public Map<String, Object> getDataMap() {
		if (this.dataMap == null) {
			this.dataMap = new HashMap<String, Object>(5);
		}
		return dataMap;
	}
	
	/**
	 * @Description 设置附件列表
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:16:20
	 * @param dataMap
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @Description 添加模板变量的值
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2015年12月29日 上午10:20:30
	 * @param key		模板变量名称
	 * @param value		模板变量值
	 */
	public void putDataMap(String key, Object value) {
		getDataMap().put(key, value);
	}

	/**
	 * @Description 获得模板代码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:17:05
	 * @return
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @Description 设置模板代码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:17:20
	 * @param templateCode
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @Description 获得租户编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:17:31
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @Description 设置租户编码
	 * @author jiuzhou.hu@superspace.cn
	 * @date 2016年1月12日 下午5:17:44
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}
