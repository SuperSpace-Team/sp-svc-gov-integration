package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

public class UserCommand implements Serializable
{
	
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2016年4月29日 上午11:25:01
	 */
	private static final long serialVersionUID = 3098759378857393894L;
	
	private Long id;
	
	private String loginName;
	
	private String userName;
	
	private String jobNumber;
	
	private String password;
	
	private String email;
	
	private String phone;
	
	private Date createTime;
	
	private Date lastLoginTime;
	
	private Date lastModifyTime;
	
	private Date lastModifyPwdTime;
	
	private String lastMender;
	
	private Integer lifeCycle;
	
	private String oldUserType;
	
	private Integer userType;
	
	private Date expiryDate;
	
	private String expiryDateString;
	
	private Date lockDate;
	
	private Date sendTime;
	
	//新增字段
	private String department;
	
	private String job;
	
	private String superior;
	
	private String remark;
	
	private Integer userSource;
	
	private String orgCode;
	
	/**
	 * 导入用户时用到
	 */
	private String appkeys;
	
	/**
	 * 是否锁定，1：已锁定，0：未锁定
	 */
	private int lockState;
	
	/**
	 * 是否锁定，1：已过期，0：未过期
	 */
	private int expiryState;
	
	//企业微信帐号
	private String weChat;
	
	//saas租户
	private String saasTenantCode;
	
	public String getExpiryDateString()
	{
		return expiryDateString;
	}
	
	public void setExpiryDateString(String expiryDateString)
	{
		this.expiryDateString = expiryDateString;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	public void setDepartment(String department)
	{
		this.department = department;
	}
	
	public String getJob()
	{
		return job;
	}
	
	public void setJob(String job)
	{
		this.job = job;
	}
	
	public String getSuperior()
	{
		return superior;
	}
	
	public void setSuperior(String superior)
	{
		this.superior = superior;
	}
	
	public String getRemark()
	{
		return remark;
	}
	
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	/**
	 * id的获取.
	 * @return Long
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * 设定id的值.
	 * @param Long
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * loginName的获取.
	 * @return String
	 */
	public String getLoginName()
	{
		return loginName;
	}
	
	/**
	 * 设定loginName的值.
	 * @param String
	 */
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	
	/**
	 * userName的获取.
	 * @return String
	 */
	public String getUserName()
	{
		return userName;
	}
	
	/**
	 * 设定userName的值.
	 * @param String
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * jobNumber的获取.
	 * @return String
	 */
	public String getJobNumber()
	{
		return jobNumber;
	}
	
	/**
	 * 设定jobNumber的值.
	 * @param String
	 */
	public void setJobNumber(String jobNumber)
	{
		this.jobNumber = jobNumber;
	}
	
	/**
	 * password的获取.
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * 设定password的值.
	 * @param String
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * email的获取.
	 * @return String
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * 设定email的值.
	 * @param String
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * phone的获取.
	 * @return String
	 */
	public String getPhone()
	{
		return phone;
	}
	
	/**
	 * 设定phone的值.
	 * @param String
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	/**
	 * createTime的获取.
	 * @return Date
	 */
	public Date getCreateTime()
	{
		return createTime;
	}
	
	/**
	 * 设定createTime的值.
	 * @param Date
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	/**
	 * lastLoginTime的获取.
	 * @return Date
	 */
	public Date getLastLoginTime()
	{
		return lastLoginTime;
	}
	
	/**
	 * 设定lastLoginTime的值.
	 * @param Date
	 */
	public void setLastLoginTime(Date lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}
	
	/**
	 * lastModifyTime的获取.
	 * @return Date
	 */
	public Date getLastModifyTime()
	{
		return lastModifyTime;
	}
	
	/**
	 * 设定lastModifyTime的值.
	 * @param Date
	 */
	public void setLastModifyTime(Date lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}
	
	/**
	 * lastModifyPwdTime的获取.
	 * @return Date
	 */
	public Date getLastModifyPwdTime()
	{
		return lastModifyPwdTime;
	}
	
	/**
	 * 设定lastModifyPwdTime的值.
	 * @param Date
	 */
	public void setLastModifyPwdTime(Date lastModifyPwdTime)
	{
		this.lastModifyPwdTime = lastModifyPwdTime;
	}
	
	/**
	 * lastMender的获取.
	 * @return String
	 */
	public String getLastMender()
	{
		return lastMender;
	}
	
	/**
	 * 设定lastMender的值.
	 * @param String
	 */
	public void setLastMender(String lastMender)
	{
		this.lastMender = lastMender;
	}
	
	/**
	 * lifeCycle的获取.
	 * @return Integer
	 */
	public Integer getLifeCycle()
	{
		return lifeCycle;
	}
	
	/**
	 * 设定lifeCycle的值.
	 * @param Integer
	 */
	public void setLifeCycle(Integer lifeCycle)
	{
		this.lifeCycle = lifeCycle;
	}
	
	/**
	 * oldUserType的获取.
	 * @return String
	 */
	public String getOldUserType()
	{
		return oldUserType;
	}
	
	/**
	 * 设定oldUserType的值.
	 * @param String
	 */
	public void setOldUserType(String oldUserType)
	{
		this.oldUserType = oldUserType;
	}
	
	/**
	 * userType的获取.
	 * @return Integer
	 */
	public Integer getUserType()
	{
		return userType;
	}
	
	/**
	 * 设定userType的值.
	 * @param Integer
	 */
	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}
	
	/**
	 * expiryDate的获取.
	 * @return Date
	 */
	public Date getExpiryDate()
	{
		return expiryDate;
	}
	
	/**
	 * 设定expiryDate的值.
	 * @param Date
	 */
	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}
	
	/**
	 * lockDate的获取.
	 * @return Date
	 */
	public Date getLockDate()
	{
		return lockDate;
	}
	
	/**
	 * 设定lockDate的值.
	 * @param Date
	 */
	public void setLockDate(Date lockDate)
	{
		this.lockDate = lockDate;
	}
	
	/**
	 * sendTime的获取.
	 * @return Date
	 */
	public Date getSendTime()
	{
		return sendTime;
	}
	
	public Integer getUserSource()
	{
		return userSource;
	}
	
	public void setUserSource(Integer userSource)
	{
		this.userSource = userSource;
	}
	
	public String getOrgCode()
	{
		return orgCode;
	}
	
	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}
	
	/**
	 * 设定sendTime的值.
	 * @param Date
	 */
	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}
	
	public int getLockState()
	{
		return lockState;
	}
	
	public void setLockState(int lockState)
	{
		this.lockState = lockState;
	}
	
	public int getExpiryState()
	{
		return expiryState;
	}
	
	public void setExpiryState(int expiryState)
	{
		this.expiryState = expiryState;
	}
	
	public String getAppkeys()
	{
		return appkeys;
	}
	
	public void setAppkeys(String appkeys)
	{
		this.appkeys = appkeys;
	}

	
	public String getWeChat()
	{
		return weChat;
	}

	
	public void setWeChat(String weChat)
	{
		this.weChat = weChat;
	}

	
	public String getSaasTenantCode()
	{
		return saasTenantCode;
	}

	
	public void setSaasTenantCode(String saasTenantCode)
	{
		this.saasTenantCode = saasTenantCode;
	}
	
}
