package com.sp.infra.svc.gov.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/4/16
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class AESGeneralUtil {

    private static final Logger logger= LoggerFactory.getLogger(AESGeneralUtil.class);

    private AESGeneralUtil() {
	}

	/**
     * 加密，password为16位md5，这个要在线加密后，双方约定 password
     *
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encrypt(String content, String password) {
        try {

            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");// enCodeFormat的长度必须为16否则报错
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return BASE64Util.encode(result); // 加密
        } catch (Exception e) {
            logger.warn("AESGeneralUtil.encrypt exception .." ,e);
        }
        return null;
    }


    /**
     * 解密
     *
     * @param content 待解密内容
     * @param password 解密密钥--一定要16位长度
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
            byte[] contentByte = BASE64Util.decode(content);
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(contentByte);
            // 加密
            return new String(result, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
