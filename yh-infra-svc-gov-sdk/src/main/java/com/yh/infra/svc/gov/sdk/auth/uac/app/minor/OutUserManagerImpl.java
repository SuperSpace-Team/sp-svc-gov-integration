/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.minor;

import cn.hutool.core.exceptions.ValidateException;
import com.yh.infra.svc.gov.sdk.auth.uac.app.command.*;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.CommonUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.EmailUtil;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.ValidateItem;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.ValidateUtil;
import com.yh.infra.svc.gov.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LSH10022
 *
 */
public class OutUserManagerImpl extends Uac2BaseManager implements OutUserManager {
	private static final Logger logger = LoggerFactory.getLogger(OutUserManagerImpl.class);

	// 1.修改用户信息接口
	@Override
	public String update(UserRegisterCommod command) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commonUser", command);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/update";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/update";
		}
		return CommonUtil.sendRequestString(startUrl, params);
	}

	// 2.重置密码接口
	@Override
	public String resetPassword(String LoginName) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", LoginName);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/resetPassword";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/resetPassword";
		}
		return CommonUtil.sendRequestString(startUrl, params);
	}

	// 3.设置生命周期接口（即启用、禁用接口）
	// 注意参数type:传入Constants.LIFECYCLE_NORMAL表示启用；传入Constants.LIFECYCLE_DISABLE表示禁用
	@Override
	public String editLifeCycle(String LoginName, Integer type) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", LoginName);
		params.put("type", type);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/editLifeCycle";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/editLifeCycle";
		}
		return CommonUtil.sendRequestString(startUrl, params);
	}

	// 4.查询用户登录日志接口
	@Override
	public QueryUserLoginLogResult queryUserLoginLogInfo(Integer userSource, String orgCode) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userSource", userSource);
		params.put("orgCode", orgCode);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/queryUserLoginLog";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/queryUserLoginLog";
		}
		QueryUserLoginLogResult queryUserLoginLogResult = new QueryUserLoginLogResult();
		BackEntity backEntity = CommonUtil.sendRequestBackEntity(startUrl, params);
		List<UserLoginLogCommand> list = JsonUtil.objectToList(backEntity.getData(), UserLoginLogCommand.class);
		queryUserLoginLogResult.setList(list);
		queryUserLoginLogResult.setSuccess(true);
		return queryUserLoginLogResult;
	}

	// 5.给外部用户绑定角色
	@Override
	public BackEntity addRole(OutUserAddRoleCommand command) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("outUserAddRoleCommand", command);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/addRole";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/addRole";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 6.根据用户查询角色列表
	@Override
	public List<RoleInfoCommand> findRoleList(QueryCommand queryCommand) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("queryCommand", queryCommand);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/findRoleList";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/findRoleList";
		}
		BackEntity backEntity = CommonUtil.sendRequestBackEntity(startUrl, params);
		List<RoleInfoCommand> list = JsonUtil.objectToList(backEntity.getData(), RoleInfoCommand.class);
		return list;
	}

	// 7.全网唯一性校验(邮箱、手机号、登录名)+格式校验
	@Override
	public BackEntity valite(ValiteCommand valiteCommand) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		if (StringUtils.isEmpty(valiteCommand)) {// 判断对象是否为空
			BackEntity backEntity = new BackEntity();
			backEntity.setResultFlag(false);
			backEntity.setErrorCode(900001);
			backEntity.setErrorMsg("传入的对象为空");
			return backEntity;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		// 邮箱格式检验
		String email = null;
		if (!StringUtils.isEmpty(valiteCommand.getEmail())) {
			email = valiteCommand.getEmail();
			Boolean tag = EmailUtil.emailFormat(email);
			if (!tag) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900002);
				backEntity.setErrorMsg("邮箱格式不正确");
				return backEntity;
			}
		}
		// 手机号格式校验
		String phone = null;
		if (!StringUtils.isEmpty(valiteCommand.getPhone())) {
			phone = valiteCommand.getPhone();
			Boolean tag = EmailUtil.isMobile(phone);
			if (!tag) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900003);
				backEntity.setErrorMsg("手机号格式不正确");
				return backEntity;
			}
		}
		// 登录名格式校验
		String loginName = null;
		if (!StringUtils.isEmpty(valiteCommand.getLoginName())) {
			loginName = valiteCommand.getLoginName();
			// 长度校验
			try {
				ValidateUtil.valid("login.name", loginName, new ValidateItem(ValidateItem.ValidType.notnull),
						new ValidateItem(ValidateItem.ValidType.range, "2-50"));
			} catch (ValidateException e) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900004);
				backEntity.setErrorMsg("登录名格式不正确:长度应该在2-50字符之间！");
				return backEntity;
			}
			// 一般校验
			Boolean tag = EmailUtil.isLoginName(loginName);
			if (!tag) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900005);
				backEntity.setErrorMsg("登录名格式不正确");
				return backEntity;
			}
			// 乱码校验
			Boolean tag1 = EmailUtil.isMessyCode(loginName);
			if (tag1) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900006);
				backEntity.setErrorMsg("登录名格式不正确:登录名中有乱码！");
				return backEntity;
			}
			// 纯数字校验
			Boolean tag2 = EmailUtil.isNumeric(loginName);
			if (tag2) {
				BackEntity backEntity = new BackEntity();
				backEntity.setResultFlag(false);
				backEntity.setErrorCode(900007);
				backEntity.setErrorMsg("登录名格式不正确:登录名不允许纯数字！");
				return backEntity;
			}
		}
		String accessToken = null;
		if (StringUtils.isEmpty(valiteCommand.getAccessToken())) {
			BackEntity backEntity = new BackEntity();
			backEntity.setResultFlag(false);
			backEntity.setErrorCode(900033);
			backEntity.setErrorMsg("accessToken未传");
			return backEntity;
		}
		accessToken = valiteCommand.getAccessToken();
		params.put("loginName", loginName);
		params.put("phone", phone);
		params.put("email", email);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valite";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valite";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 8.发送验证码
	@Override
	public BackEntity sendCheckCode(String email, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/sendCheckCode";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/sendCheckCode";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 9.验证码校验
	@Override
	public BackEntity valiteCheckCode(String checkCode, String email, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("checkCode", checkCode);
		params.put("email", email);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valiteCheckCode";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valiteCheckCode";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 12.hub外部用户注册
	@Override
	public BackEntity hubRegister(RegisetrCommand hubRegisetrCommand) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isEmpty(hubRegisetrCommand.getSecondPassword())) {
			hubRegisetrCommand.setSecondPassword(hubRegisetrCommand.getPassword());
		}
		params.put("hubRegisetrCommand", hubRegisetrCommand);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/hubRegister";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/hubRegister";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 13.找回密码
	@Override
	public BackEntity findPassword(String checkCode, String email, String password, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Date date = new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("checkCode", checkCode);
		params.put("email", email);
		params.put("password", password);
		params.put("date", date);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/findPassword";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/findPassword";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 13.找回密码（新版）
	@Override
	public BackEntity findPasswordNew(String firstPassword, String secondPassword, String email, String accessToken) {
		Date date = new Date();
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("firstPassword", firstPassword);
		params.put("secondPassword", secondPassword);
		params.put("email", email);
		params.put("date", date);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/findPasswordNew";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/findPasswordNew";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 14.更换手机号或者更换邮箱--验证当前登陆用户的原始邮箱是否和输入的邮箱相同。
	@Override
	public BackEntity valiteOldEmail(String oldEmail, String loginName, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oldEmail", oldEmail);
		params.put("loginName", loginName);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valiteOldEmail";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valiteOldEmail";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 15.更换手机号--验证原有手机号是否与当前登陆用户输入的原手机号一致
	@Override
	public BackEntity valiteOldPhone(String oldPhone, String loginName, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oldPhone", oldPhone);
		params.put("loginName", loginName);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valiteOldPhone";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valiteOldPhone";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 16.更换手机号--新手机号
	@Override
	public BackEntity updatePhone(String newPhone, String loginName, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newPhone", newPhone);
		params.put("loginName", loginName);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/updatePhone";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/updatePhone";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 17.更换邮箱--新邮箱
	@Override
	public BackEntity updateEmail(String newEmail, String loginName, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newEmail", newEmail);
		params.put("loginName", loginName);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/updateEmail";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/updateEmail";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 18.修改密码-验证当前用户输入的密码和数据库中的密码是否一致
	@Override
	public BackEntity valiteOldPassword(String oldPassword, String loginName, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oldPassword", oldPassword);
		params.put("loginName", loginName);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valiteOldPassword";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valiteOldPassword";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 19.修改密码-验证当前用户输入的新密码和旧密码是否一致
	@Override
	public BackEntity valitePassword(String oldPassword, String newPassword, String accessToken) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oldPassword", oldPassword);
		params.put("newPassword", newPassword);
		params.put("accessToken", accessToken);// 租户secret
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/valitePassword";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/valitePassword";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}

	// 20.新增获取accesstoken接口
	@Override
	public BackEntity getAccessToken(String appkey, String secret) {
		Map<String, Object> map = paramsMap();
		String startUrl = (String) map.get("startUrl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appkey", appkey);
		params.put("secret", secret);
		if (startUrl.endsWith("/")) {
			startUrl = startUrl + "uac2/uac2/json/admin/user/getAccessToken";
		} else {
			startUrl = startUrl + "/uac2/uac2/json/admin/user/getAccessToken";
		}
		return CommonUtil.sendRequestBackEntity(startUrl, params);
	}
}
