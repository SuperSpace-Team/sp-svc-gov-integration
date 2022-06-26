package com.sp.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

public class PrivilegeAndUrlCommand implements Serializable {
	private static final long serialVersionUID = 1852951866988529815L;
	
	private Long id;
	private Long urlId;
	private String acl;
	private String name;
	private String typeName;
	private String type;
	private String url;
	private String groupName;
	
	public PrivilegeAndUrlCommand() {}
	
	

	public PrivilegeAndUrlCommand(Long id, Long urlId, String acl, String name,
                                  String typeName, String type, String url, String groupName) {
		super();
		this.id = id;
		this.urlId = urlId;
		this.acl = acl;
		this.name = name;
		this.typeName = typeName;
		this.type = type;
		this.url = url;
		this.groupName = groupName;
	}

	public String getAcl() {
		return acl;
	}
	public void setAcl(String acl) {
		this.acl = acl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUrlId() {
		return urlId;
	}
	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}



	@Override
	public String toString() {
		return "PrivilegeAndUrlCommand [id=" + id + ", urlId=" + urlId
				+ ", acl=" + acl + ", name=" + name + ", typeName=" + typeName
				+ ", type=" + type + ", url=" + url + ", groupName="
				+ groupName + "]";
	}

}
