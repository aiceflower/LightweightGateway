package com.scnu.lwg.util;

/**
 * @author Kin
 * @description byte utils
 * @email kinsanities@sina.com
 * @time 2021/5/10 7:33 上午
 */

public class ByteUtils {
	private static final char[] HEXES = {
			'0', '1', '2', '3',
			'4', '5', '6', '7',
			'8', '9', 'a', 'b',
			'c', 'd', 'e', 'f'
	};

	/**
	 * byte数组 转换成 16进制小写字符串
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		StringBuilder hex = new StringBuilder();

		for (byte b : bytes) {
			hex.append(HEXES[(b >> 4) & 0x0F]);
			hex.append(HEXES[b & 0x0F]);
		}

		return hex.toString();
	}

	/**
	 * 16进制字符串 转换为对应的 byte数组
	 * @param hex
	 * @return
	 */
	public static byte[] hex2Bytes(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		char[] hexChars = hex.toCharArray();
		// 如果 hex 中的字符不是偶数个, 则忽略最后一个
		byte[] bytes = new byte[hexChars.length / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
		}

		return bytes;
	}

	/**
	 * print hex
	 * @param res
	 * @param len
	 */
	public static void printHex(byte[] res, int len){
		for(int i = 0; i < len; i++) {
			System.out.print(Integer.toHexString(res[i]) + "  ");
			if((i+1)%16==0) {
				System.out.println();
			}
		}
		System.out.println();
	}
}
