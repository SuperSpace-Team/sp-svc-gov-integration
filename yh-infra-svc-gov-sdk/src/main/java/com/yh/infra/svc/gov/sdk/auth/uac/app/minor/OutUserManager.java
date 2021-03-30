/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;

import java.util.List;

/**
 * @author LSH10022
 *
 */
public interface OutUserManager {

	//1.修改用户信息接口
	String update(UserRegisterCommod command);
	
	//2.重置密码接口
	String resetPassword(String LoginName);
	
	//3.设置生命周期接口（即启用、禁用接口）   注意参数type:传入Constants.LIFECYCLE_NORMAL表示启用；传入Constants.LIFECYCLE_DISABLE表示禁用
	String editLifeCycle(String LoginName, Integer type);

	//4.查询用户登录日志接口
	QueryUserLoginLogResult queryUserLoginLogInfo(Integer userSource, String orgCode);


	//5.给外部用户绑定角色
	BackEntity addRole(OutUserAddRoleCommand command);

	//6.根据用户查询角色列表
	List<RoleInfoCommand> findRoleList(QueryCommand queryCommand);

	//7.全网唯一性校验(支持邮箱、手机号、登录名)+格式校验
	BackEntity valite(ValiteCommand valiteCommand);

	//8.发送验证码
	BackEntity sendCheckCode(String email, String accessToken);

	//9.验证码校验
	BackEntity valiteCheckCode(String checkCode, String email, String accessToken);


	//10.hub外部用户注册接口
	BackEntity hubRegister(RegisetrCommand hubRegisetrCommand);

	//11.找回密码
	BackEntity findPassword(String checkCode, String email, String password, String accessToken);

	//12.(新版)找回密码
	BackEntity findPasswordNew(String firstPassword, String secondPassword, String email, String accessToken);

	//13.更换手机号-检验当前登陆的用户的手机号是否与输入的原有手机号码一致。
	BackEntity valiteOldPhone(String oldPhone, String loginName, String accessToken);

	//14.更换手机号-新手机
	BackEntity updatePhone(String newPhone, String loginName, String accessToken);

	//15.更换手机号或者更换邮箱-验证当前登陆用户的原始邮箱是否和输入的邮箱相同。
	BackEntity valiteOldEmail(String oldEmail, String loginName, String accessToken);

	//16.更换邮箱--新邮箱
	BackEntity updateEmail(String newEmail, String loginName, String accessToken);

	//17.修改密码-验证当前用户输入的密码和数据库中的密码是否一致
	BackEntity valiteOldPassword(String oldPassword, String loginName, String accessToken);

	//18.修改密码-验证当前用户输入的新密码和旧密码是否一致
	BackEntity valitePassword(String oldPassword, String newPassword, String accessToken);

	//19.新增获取accesstoken接口（这个与appcontroller-登录相关接口的token无关系）
	BackEntity getAccessToken(String appkey, String secret);

	

}
