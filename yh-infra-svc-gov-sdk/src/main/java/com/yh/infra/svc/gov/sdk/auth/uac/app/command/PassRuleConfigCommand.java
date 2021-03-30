package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class PassRuleConfigCommand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3663134245280004557L;
	private String code;
	private String description;
	private String ruleVal;
	private Integer ruleGroup;
	private Integer selType;
	private Boolean isRequired;
	private Boolean isSelected;
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
	public String getRuleVal() {
		return ruleVal;
	}
	public void setRuleVal(String ruleVal) {
		this.ruleVal = ruleVal;
	}
	public Integer getRuleGroup() {
		return ruleGroup;
	}
	public void setRuleGroup(Integer ruleGroup) {
		this.ruleGroup = ruleGroup;
	}
	public Integer getSelType() {
		return selType;
	}
	public void setSelType(Integer selType) {
		this.selType = selType;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
