package com.sp.infra.svc.gov.sdk.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * 此方法用于与js端的加密进行联动操作，如果只使用服务端的 aes加密,请使用AESEncryptor
 * 
 * @author Alex Lu
 *
 */
public class AesAuthUtil {
	private static final int keySize = 128;
	private static final int iterationCount = 800;
	private static final String iv = "FF245C99227E6B2EFE7510B35DD3D137";
	private static final String salt = "3FF2EC0C9C6B7B945225DEBAD71A01B6965FE84C95A70EB132A82F88C0A59A55";
	private static final String passphrase = "AB33T33##aasd*93339+_2";
	private static ThreadLocal<Cipher> cipherLocal = new ThreadLocal<Cipher>();

	private static AesAuthUtil aesUtil = new AesAuthUtil();

	public AesAuthUtil() {
	}

	public static String encrypt(String plaintext) {
		Cipher cipher = getCipher();

		return encrypt(cipher, salt, iv, passphrase, plaintext);
	}

	private static String encrypt(Cipher cipher, String salt, String iv, String passphrase, String plaintext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] encrypted = doFinal(cipher, Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes("UTF-8"));
			return base64(encrypted);
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}

	private static Cipher getCipher() {
		Cipher cipher = null;
		try {
			cipher = cipherLocal.get();
			if (cipher == null)
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			throw fail(e);
		}
		return cipher;
	}
	
	public static String decrypt(String ciphertext) {
		if (StringUtils.isBlank(ciphertext))
			return null;
		Cipher cipher = getCipher();
		return aesUtil.decrypt(cipher, salt, iv, passphrase, ciphertext);
	}

	private String decrypt(Cipher cipher, String salt, String iv, String passphrase, String ciphertext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] decrypted = doFinal(cipher, Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));
			return new String(decrypted, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}

	private static byte[] doFinal(Cipher cipher, int encryptMode, SecretKey key, String iv, byte[] bytes) {
		try {
			cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			throw fail(e);
		}
	}

	private static SecretKey generateKey(String salt, String passphrase) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterationCount, keySize);
			SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			return key;
		} catch (Exception e) {
			throw fail(e);
		}
	}

	public static String random(int length) {
		byte[] salt = new byte[length];
		new SecureRandom().nextBytes(salt);
		return hex(salt);
	}

	public synchronized static String base64(byte[] bytes) {
		try {
			return new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] base64(String str) {
		try {
			return Base64.decodeBase64(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String hex(byte[] bytes) {
		return new String(Hex.encodeHex(bytes));
	}

	public static byte[] hex(String str) {
		try {
			return Hex.decodeHex(str.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException(e);
		}
	}

	private static IllegalStateException fail(Exception e) {
		return new IllegalStateException(e);
	}

	public static void main(String[] args) {
		AesAuthUtil au = new AesAuthUtil();

		String ct1 = au.encrypt("test");
		System.out.println(ct1);
		String ct = "w8Dlpnrw8jiNAnnCk8Xfcw==";

		System.out.println(au.decrypt(ct));

	}

}
