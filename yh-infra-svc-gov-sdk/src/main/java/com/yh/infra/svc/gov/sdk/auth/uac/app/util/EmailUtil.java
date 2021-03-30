/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import com.yh.infra.svc.gov.sdk.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author LSH10022
 *验证输入的邮箱格式是否合法
 */

public class EmailUtil {

	
//1.邮箱格式验证
public static boolean emailFormat(String email)
    {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }


//2.手机号格式验证
public static boolean isMobile(String str) {
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    String s2="^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";// 验证手机号
    if(StringUtils.isNotBlank(str)){
        p = Pattern.compile(s2);
        m = p.matcher(str);
        b = m.matches();
    }
    return b;
}


//3.1登录名格式验证(不允许空格、特殊字符、邮箱;支持中文，大小写字母，数字)
public static boolean isLoginName(String str) {
  Pattern p = null;
  Matcher m = null;
  boolean b = false;
  String s2="^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+)$";// 验证登录名
  if(StringUtils.isNotBlank(str)){
      p = Pattern.compile(s2);
      m = p.matcher(str);
      b = m.matches();
  }
  return b;
}





/**
 * 判断是否是中日韩文字
 * @param c     要判断的字符
 * @return      true或false
 */
private static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
        return true;
    }
    return false;
}


/**
 * 判断是否是数字或者是英文字母
 * @param c
 * @return
 */
public static boolean judge(char c){
    if((c >='0' && c<='9')||(c >='a' && c<='z' ||  c >='A' && c<='Z')){
        return true;
    }
    return false;
}

//3.2登录名格式验证(不允许包含乱码)
public static boolean isMessyCode(String strName) {
    //去除字符串中的空格 制表符 换行 回车
    Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
    Matcher m = p.matcher(strName);
    String after = m.replaceAll("");
    //去除字符串中的标点符号
    String temp = after.replaceAll("\\p{P}", "");
    //处理之后转换成字符数组
    char[] ch = temp.trim().toCharArray();
    for (int i = 0; i < ch.length; i++) {
        char c = ch[i];
        //判断是否是数字或者英文字符
        if (!judge(c)) {
            //判断是否是中日韩文
            if (!isChinese(c)) {
                //如果不是数字或者英文字符也不是中日韩文则表示是乱码返回true
                return true;
            }
        }
    }
    //表示不是乱码 返回false
    return false;
}

//3.3登录名格式验证(不允许纯数字)
public  static  boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
        return false;
    }
    return true;
}




//4.密码格式校验（大小写字母,数字,特殊字符中的至少3种.8位以上,正确返回true）
public static boolean rexCheckPassword(String input) {
    // 8-20 位，字母、数字、字符
    String regStr = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$";
    return input.matches(regStr);
}
public static void main(String args[]){
	String  phone ="19901708547";
	String email = "sunpeng01061452@163.com";
//	String email="11111@qq.com";
//	EmailUtil emailUtil = new EmailUtil();
//  	if(emailUtil.emailFormat(email)){
//  		System.out.println("√");
// 
//  	}
//  	else{
//  		System.out.println("您的邮箱格式不正确，请重新输入！");
//  	  	}	
//  	 String str="0123456789";//随机生成数字验证码
//  	 String uuid=new String();
//     for(int i=0;i<4;i++)
//     {
//         char ch=str.charAt(new Random().nextInt(str.length()));
//         uuid+=ch;
//     }
//     System.out.println(uuid);//控制台输出验证码
//     
//     
//     String a="锘挎槬鐪犱";
//
//     System.out.println(EmailUtil.isSpecialChar(a));
//     
	System.out.println(EmailUtil.emailFormat(email));
	System.out.println(EmailUtil.isMobile(phone));
}





}
