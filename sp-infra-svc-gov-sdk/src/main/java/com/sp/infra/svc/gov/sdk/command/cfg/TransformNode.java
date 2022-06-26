package com.sp.infra.svc.gov.sdk.command.cfg;

import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;

/**
 * @author luchao 2018-12-14
 *
 */
public class TransformNode {
	
	private Integer code;
	private Integer bizCode;
	private Integer entryCode;
	private String matchExp;
	private String sourceKeyExp;
	private String sourceAlias;
	private String targetKeyExp;
	private String targetAlias;
	private int type = SdkCommonConstant.ALM_NODE_TYPE_CUSTOMIZE;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public Integer getBizCode() {
		return bizCode;
	}
	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}
	public Integer getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(Integer entryCode) {
		this.entryCode = entryCode;
	}
	public String getMatchExp() {
		return matchExp;
	}
	public void setMatchExp(String matchExp) {
		this.matchExp = matchExp;
	}
	public String getSourceKeyExp() {
		return sourceKeyExp;
	}
	public void setSourceKeyExp(String sourceKeyExp) {
		this.sourceKeyExp = sourceKeyExp;
	}
	public String getSourceAlias() {
		return sourceAlias;
	}
	public void setSourceAlias(String sourceAlias) {
		this.sourceAlias = sourceAlias;
	}
	public String getTargetKeyExp() {
		return targetKeyExp;
	}
	public void setTargetKeyExp(String targetKeyExp) {
		this.targetKeyExp = targetKeyExp;
	}
	public String getTargetAlias() {
		return targetAlias;
	}
	public void setTargetAlias(String targetAlias) {
		this.targetAlias = targetAlias;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "TransformNode [code=" + code + ", bizCode=" + bizCode + ", entryCode=" + entryCode + ", matchExp=" + matchExp + ", sourceKeyExp=" + sourceKeyExp
				+ ", sourceAlias=" + sourceAlias + ", targetKeyExp=" + targetKeyExp + ", targetAlias=" + targetAlias + ", type=" + type + "]";
	}
	
}
