package com.sp.infra.svc.gov.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * @author: oo
 * @date: 2018/10/30 16:07
 */
public class PasswordBook {
	private static final Logger logger = LoggerFactory.getLogger(PasswordBook.class);
	
	private static final int compressLen = 3;
	private static final byte[] IV_BYTES = "0102030405060708".getBytes();
	private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final byte[] IA = new byte[256];

	public static String encrypt(String content, String key) throws Exception {
		String[] split = content.split("");
		StringBuilder md5 = new StringBuilder();
		for (String s : split) {
			md5.append(md5Compress(s, key, compressLen));
		}
		String ciphertext = aesEncrypt(key, content);
		return String.format("~%s~%s~", ciphertext, md5.toString());
	}

	public static String decrypt(String content, String decryptKey) throws Exception {
		if (!isCiphertext(content)) {
			throw new IllegalArgumentException("非合法密文!!!");
		}
		String[] split = content.split("~");
		String text = split[1];
		return PasswordBook.aesDecrypt(text, decryptKey);
	}

	public static boolean isCiphertext(String content) {
		String[] split = content.split("~");
		if (split.length != 3) {
			return false;
		}

		int textLen = split[2].length();
		if (textLen % 4 != 0) {
			return false;
		}
		return true;
	}

	public static String md5Compress(String content, String key) throws Exception {
		StringBuilder md5 = new StringBuilder();
		String[] split = content.split("");
		for (String s : split) {
			md5.append(md5Compress(s, key, compressLen));
		}
		return md5.toString();
	}

	private static String md5Compress(String text, String encrypt, int toLength) throws Exception {
		return base64Encode(compress(hmacMD5Encrypt(text, encrypt.getBytes()), toLength));
	}

	private static byte[] hmacMD5Encrypt(String encryptText, byte[] encryptKey) throws Exception {
		SecretKey secretKey = new SecretKeySpec(encryptKey, "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(secretKey);
		byte[] text = encryptText.getBytes("UTF-8");
		return mac.doFinal(text);
	}

	private static String aesEncrypt(String key, String content)
			throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
		return base64Encode(aesUtil(key, content.getBytes(), Cipher.ENCRYPT_MODE));
	}

	private static String aesDecrypt(String content, String decryptKey) throws Exception {
		byte[] bytes = aesUtil(decryptKey, decode(content.getBytes("UTF-8")), Cipher.DECRYPT_MODE);
		return new String(bytes, "UTF-8");
	}

	private static byte[] aesUtil(String key, byte[] content, int mode)
			throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
//        byte[] bytes = key.getBytes();
//        IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(mode, new SecretKeySpec(bytes, "AES"), iv);
//        return cipher.doFinal(content);

		byte[] bytes = key.getBytes();
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256, new SecureRandom(bytes));
		SecretKey secretKey = kgen.generateKey();
		IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, new SecretKeySpec(secretKey.getEncoded(), "AES"), iv);
		return cipher.doFinal(content);
	}

	private static byte[] decode(byte[] inbuf) {
		int size = inbuf.length / 4 * 3;
		if (size == 0) {
			return inbuf;
		} else {
			if (inbuf[inbuf.length - 1] == 61) {
				--size;
				if (inbuf[inbuf.length - 2] == 61) {
					--size;
				}
			}

			byte[] outbuf = new byte[size];
			int inpos = 0;
			int outpos = 0;

			for (size = inbuf.length; size > 0; size -= 4) {
				byte a = IA[inbuf[inpos++] & 255];
				byte b = IA[inbuf[inpos++] & 255];
				outbuf[outpos++] = (byte) (a << 2 & 252 | b >>> 4 & 3);
				if (inbuf[inpos] == 61) {
					return outbuf;
				}

				a = b;
				b = IA[inbuf[inpos++] & 255];
				outbuf[outpos++] = (byte) (a << 4 & 240 | b >>> 2 & 15);
				if (inbuf[inpos] == 61) {
					return outbuf;
				}

				a = b;
				b = IA[inbuf[inpos++] & 255];
				outbuf[outpos++] = (byte) (a << 6 & 192 | b & 63);
			}

			return outbuf;
		}
	}

	static {
		int i;
		for (i = 0; i < 255; ++i) {
			IA[i] = -1;
		}

		for (i = 0; i < CA.length; ++i) {
			IA[CA[i]] = (byte) i;
		}

	}

	private static byte[] encodeToByte(byte[] sArr, boolean lineSep) {
		int sLen = sArr != null ? sArr.length : 0;
		if (sLen == 0) {
			return new byte[0];
		} else {
			int eLen = sLen / 3 * 3;
			int cCnt = (sLen - 1) / 3 + 1 << 2;
			int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
			byte[] dArr = new byte[dLen];
			int left = 0;
			int d = 0;
			int cc = 0;

			while (left < eLen) {
				int i = (sArr[left++] & 255) << 16 | (sArr[left++] & 255) << 8 | sArr[left++] & 255;
				dArr[d++] = (byte) CA[i >>> 18 & 63];
				dArr[d++] = (byte) CA[i >>> 12 & 63];
				dArr[d++] = (byte) CA[i >>> 6 & 63];
				dArr[d++] = (byte) CA[i & 63];
				if (lineSep) {
					++cc;
					if (cc == 19 && d < dLen - 2) {
						dArr[d++] = 13;
						dArr[d++] = 10;
						cc = 0;
					}
				}
			}

			left = sLen - eLen;
			if (left > 0) {
				d = (sArr[eLen] & 255) << 10 | (left == 2 ? (sArr[sLen - 1] & 255) << 2 : 0);
				dArr[dLen - 4] = (byte) CA[d >> 12];
				dArr[dLen - 3] = (byte) CA[d >>> 6 & 63];
				dArr[dLen - 2] = left == 2 ? (byte) CA[d & 63] : 61;
				dArr[dLen - 1] = 61;
			}
			return dArr;
		}
	}

	private static byte[] compress(byte[] input, int toLength) {
		if (toLength < 0) {
			return null;
		} else {
			byte[] output = new byte[toLength];
			int i;
			for (i = 0; i < output.length; ++i) {
				output[i] = 0;
			}
			for (i = 0; i < input.length; ++i) {
				int index_output = i % toLength;
				output[index_output] ^= input[i];
			}
			return output;
		}
	}

	private static String base64Encode(byte[] src) {
		byte[] res = encodeToByte(src, false);
		return src != null ? new String(res, Charset.forName("UTF-8")) : null;

	}
}
