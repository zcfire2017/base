package com.base.tools.encry;


import com.base.tools.log.LogHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密帮助类
 */
public class EncryptionHelper {

	/**
	 * 用SHA256加密字符串
	 *
	 * @param str 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String toSHA256String(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (Exception e) {
			LogHelper.error(e);
		}
		return encodeStr;
	}

	/**
	 * 将byte转为16进制
	 *
	 * @param bytes byte数组
	 * @return 字符串
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuilder stringBuffer = new StringBuilder();
		String temp;
		for (byte aByte : bytes) {
			temp = Integer.toHexString(aByte & 0xFF);
			if (temp.length() == 1) {
				//1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

	/**
	 * SHA1 加密
	 *
	 * @param inStr 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String shaEncode(String inStr) {
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuilder hexValue = new StringBuilder();
		for (byte md5Byte : md5Bytes) {
			int val = ((int) md5Byte) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}