package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共用户信息
 * @author Justin Hu
 *
 */
public class CommonUserInfo implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7316464744134520161L;
	
	/**
	 * id
	 */
	private Long userId;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 员工工号
	 */
	private String jobNumber;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机
	 */
	private String phone;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 有效期
	 */
	private Date expiryDate;
	
	/**
	 * 生命周期
	 */
	private Integer lifeCycle;
	
	/**
	 * 部门
	 */
	private String department;
	
	/**
	 * 岗位
	 */
	private String job;
	
	/**
	 * 部门
	 */
	private String superior;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 所属运营中心或者root
	 */
	private Long optId;
	
	//企业微信帐号
	private String weChat;
	
	//saas租户
	private String saasTenantCode;
	
	public Long getOptId()
	{
		return optId;
	}
	
	public void setOptId(Long optId)
	{
		this.optId = optId;
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
	
	public String getLoginName()
	{
		return loginName;
	}
	
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getJobNumber()
	{
		return jobNumber;
	}
	
	public void setJobNumber(String jobNumber)
	{
		this.jobNumber = jobNumber;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public Date getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	public Date getLastLoginTime()
	{
		return lastLoginTime;
	}
	
	public void setLastLoginTime(Date lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}
	
	public Date getExpiryDate()
	{
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}
	
	public Integer getLifeCycle()
	{
		return lifeCycle;
	}
	
	public void setLifeCycle(Integer lifeCycle)
	{
		this.lifeCycle = lifeCycle;
	}
	
	public Long getUserId()
	{
		return userId;
	}
	
	public void setUserId(Long userId)
	{
		this.userId = userId;
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

	@Override
	public String toString()
	{
		return "CommonUserInfo [userId=" + userId + ", loginName=" + loginName + ", userName=" + userName + ", jobNumber=" + jobNumber + ", email=" + email + ", phone=" + phone + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime + ", expiryDate=" + expiryDate + ", lifeCycle=" + lifeCycle + ", department=" + department + ", job=" + job + ", superior=" + superior
				+ ", remark=" + remark + ", optId=" + optId + ", weChat=" + weChat + ", saasTenantCode=" + saasTenantCode + "]";
	}
	
}
