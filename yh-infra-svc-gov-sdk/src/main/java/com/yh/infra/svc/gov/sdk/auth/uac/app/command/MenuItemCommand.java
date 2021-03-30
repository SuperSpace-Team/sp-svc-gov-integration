package com.yh.infra.svc.gov.sdk.auth.uac.app.command;


import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author 李辉
 */
public class MenuItemCommand implements Serializable, Treeable, Comparable<MenuItemCommand> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6823169729912963831L;

	/** ID */
    private Long id;
    
    /** 菜单显示名称 */
    private String name;
    
    /** 国际化外键使用 **/
    private String code;

    /** 入口URL，对于菜单组/分隔符忽略此数据 */
    private String url;
    
    /** 序号，仅对同级菜单排序用 */
    private Integer sortNo;
    
    /** 是否有子菜单[菜单组] */
    private Boolean isGroup = Boolean.FALSE;

    /** 权限ACL */
    private String acl;

    /** 父菜单项，如果为空说明是顶层菜单 */
    private Long parentId;
    
    /** 子菜单     */
    private List<MenuItemCommand> childList;
    
    /**
     * @Title type
     * @type Integer
     * @date 2016年3月21日 下午6:31:09
     * 含义 1、应用，2、根节点，3、叶子
     */
    private Integer type;
    
    private Long appId;
    
    private boolean che;
    
    private String src;
    
    private Integer lifecycle;

    
    
	public MenuItemCommand() {	}

	

	public MenuItemCommand(Long id, String name, String url, Integer sortNo,
                           Boolean isGroup, String acl, Long parentId,
                           List<MenuItemCommand> childList) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.sortNo = sortNo;
		this.isGroup = isGroup;
		this.acl = acl;
		this.parentId = parentId;
		this.childList = childList;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<MenuItemCommand> getChildList() {
		return childList;
	}

	public void setChildList(List<MenuItemCommand> childList) {
		this.childList = childList;
	}



	public String getAcl() {
		return acl;
	}



	public void setAcl(String acl) {
		this.acl = acl;
	}

	/**
	 * type的获取.
	 * @return Integer
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设定type的值.
	 * @param Integer
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	

	/**
	 * appId的获取.
	 * @return Long
	 */
	public Long getAppId() {
		return appId;
	}



	/**
	 * 设定appId的值.
	 * @param Long
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * che的获取.
	 * @return boolean
	 */
	public boolean isChe() {
		return che;
	}



	/**
	 * 设定che的值.
	 * @param boolean
	 */
	public void setChe(boolean che) {
		this.che = che;
	}



	@Override
	public String toString() {
		return "MenuItemCommand [id=" + id + ", name=" + name + ", url=" + url
				+ ", sortNo=" + sortNo + ", isGroup=" + isGroup + ", acl="
				+ acl + ", parentId=" + parentId + ", childList=" + childList
				+ "]";
	}



	@Override
	public int compareTo(MenuItemCommand o) {
		return this.sortNo.compareTo(o.sortNo);
	}

	@Override
	public String getText() {
		return this.getName();
	}

	@Override
	public boolean isOpen() {
		if(this.getChildList()!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean isChecked() {
		return this.isChe();
	}

	/**
	 * src的获取.
	 * @return String
	 */
	@Override
	public String getSrc() {
		return src;
	}



	/**
	 * 设定src的值.
	 * @param String
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * lifecycle的获取.
	 * @return Integer
	 */
	public Integer getLifecycle() {
		return lifecycle;
	}

	/**
	 * 设定lifecycle的值.
	 * @param Integer
	 */
	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}
    
    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }
}
