package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * 树节点
 * 
 * @author 李舜
 *
 */
public class TreeNode {
	private Treeable data;
	private Collection<TreeNode> children = new ArrayList<TreeNode>();
	private String startXML = "<item>";
	private String endXML = "</item>";
	private String midXML = "";
	private String startOption = "<option></option>";
	private String endOption = "";
	private String startJson = "";
	private String endJson = "";
	
	public String getStartJson() {
		return startJson;
	}
	public void setStartJson(String startJson) {
		this.startJson = startJson;
	}
	public String getEndJson() {
		return endJson;
	}
	public void setEndJson(String endJson) {
		this.endJson = endJson;
	}
	public String getStartOption() {
		return startOption;
	}
	public void setStartOption(String startOption) {
		this.startOption = startOption;
	}
	public String getEndOption() {
		return endOption;
	}
	public void setEndOption(String endOption) {
		this.endOption = endOption;
	}
	public String getMidXML() {
		return midXML;
	}
	public void setMidXML(String midXML) {
		this.midXML = midXML;
	}
	public String getStartXML() {
		return startXML;
	}
	public void setStartXML(String startXML) {
		this.startXML = startXML;
	}
	public String getEndXML() {
		return endXML;
	}
	public void setEndXML(String endXML) {
		this.endXML = endXML;
	}
	public String getAllJson() {
		return this.getStartJson() + this.getEndJson();
	}
	public String getAllXML() {
		return this.getStartXML()+this.getMidXML()+this.getEndXML();
	}
	public String getAllOption() {
		return this.getStartOption() + this.getEndOption();
	}
	public Treeable getData() {
		return data;
	}
	public void setData(Treeable data) {
		this.data = data;
		this.startXML = "<item id='"+data.getId()+"' text='"+data.getText()+"' ";
		if(data.isChecked()) {
			this.startXML += "checked='1' ";
		}
		if(data.isOpen()) {
			this.startXML += " open='1'";
		}
		
		this.startXML += " >";
		if(data.getSrc()!=""){
			this.startXML +=" <userdata name='url'>"+data.getSrc()+"</userdata> ";
		}
		//===============option================
		if(data.isChecked()) {
			this.startOption = " <option value='"+data.getId()+"' selected='selected' >"+data.getText()+"</option> ";
		}else {
			this.startOption = " <option value='"+data.getId()+"'>"+data.getText()+"</option> ";
		}
		//===============json==================
		if(data.isChecked()) {
			this.startJson = " {\"id\":\""+data.getId()+"--\",\"text\":\""+data.getText()+"\",\"selected\":true} ";
		}else {
			this.startJson = " {\"id\":\""+data.getId()+"--\",\"text\":\""+data.getText()+"\"} ";
		}
	}
	public Collection<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(Collection<TreeNode> children) {
		this.children = children;
		int i = 0;
		for(TreeNode tn : children) {
			if(i==0) {
				this.midXML = "";
				this.endOption = "";
				this.endJson = ",";
			}
			this.midXML += tn.getAllXML();
			//this.endOption += tn.getAllOption();
			i++;
		}
	}
	
	public Serializable getParentId() {
		return this.data.getParentId();
	}
	
	public Serializable getId() {
		return this.data.getId();
	}
}
