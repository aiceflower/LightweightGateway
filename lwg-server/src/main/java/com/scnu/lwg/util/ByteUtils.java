package com.scnu.lwg.util;

import java.io.*;
import java.nio.ByteBuffer;

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

	private static ByteBuffer buffer = ByteBuffer.allocate(8);

	/**
	 * int转byte
	 */
	public static byte intToByte(int x) {
		return (byte) x;
	}

	/**
	 * byte转int
	 */
	public static int byteToInt(byte b) {
		return b & 0xFF;
	}

	/**
	 * byte[]转int
	 */
	public static int byteArrayToInt(byte[] b) {
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}
	public static int byteArrayToInt(byte[] b, int index){
		return   b[index+3] & 0xFF |
				(b[index+2] & 0xFF) << 8 |
				(b[index+1] & 0xFF) << 16 |
				(b[index+0] & 0xFF) << 24;
	}

	/**
	 * int转byte[]
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] {
				(byte) ((a >> 24) & 0xFF),
				(byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF)
		};
	}

	/**
	 * short转byte[]
	 */
	public static void byteArrToShort(byte b[], short s, int index) {
		b[index + 1] = (byte) (s >> 8);
		b[index + 0] = (byte) (s >> 0);
	}

	/**
	 * byte[]转short
	 */
	public static short byteArrToShort(byte[] b, int index) {
		return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
	}

	/**
	 * 16位short转byte[]
	 */
	public static byte[] shortToByteArr(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	/**
	 * byte[]转16位short
	 */
	public static short byteArrToShort(byte[] b){
		return byteArrToShort(b,0);
	}

	/**
	 * long转byte[]
	 */
	public static byte[] longToBytes(long x) {
		buffer.putLong(0, x);
		return buffer.array();
	}

	/**
	 * byte[]转Long
	 */
	public static long bytesToLong(byte[] bytes) {
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();//need flip
		return buffer.getLong();
	}

	/**
	 * 从byte[]中抽取新的byte[]
	 * @param data - 元数据
	 * @param start - 开始位置
	 * @param end - 结束位置
	 * @return 新byte[]
	 */
	public static byte[] getByteArr(byte[]data,int start ,int end){
		byte[] ret=new byte[end-start];
		for(int i=0;(start+i)<end;i++){
			ret[i]=data[start+i];
		}
		return ret;
	}

	/**
	 * 流转换为byte[]
	 */
	public static byte[] readInputStream(InputStream inStream) {
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			byte[] data = null;
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
			return data;
		}catch (IOException e) {
			return null;
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
	}

	/**
	 * byte[]转inputstream
	 */
	public static InputStream readByteArr(byte[] b){
		return new ByteArrayInputStream(b);
	}

	/**
	 * byte数组内数字是否相同
	 */
	public static boolean isEq(byte[] s1,byte[] s2){
		int slen=s1.length;
		if(slen==s2.length){
			for(int index=0;index<slen;index++){
				if(s1[index]!=s2[index]){
					return false;
				}
			}
			return true;
		}
		return  false;
	}

	/**
	 * byte数组转换为Stirng
	 * @param s1 -数组
	 * @param encode -字符集
	 * @param err -转换错误时返回该文字
	 * @return
	 */
	public static String getString(byte[] s1,String encode,String err){
		try {
			return new String(s1,encode);
		} catch (UnsupportedEncodingException e) {
			return err==null?null:err;
		}
	}

	/**
	 * byte数组转换为Stirng
	 * @param s1-数组
	 * @param encode-字符集
	 * @return
	 */
	public static String getString(byte[] s1,String encode){
		return getString(s1,encode,null);
	}

	/**
	 * 字节数组转16进制字符串
	 */
	public static String byteArrToHexString(byte[] b){
		String result="";
		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring(1);
		}
		return result;
	}

	/**
	 * 16进制字符创转int
	 */
	public static int hexStringToInt(String hexString){
		return Integer.parseInt(hexString,16);
	}

	/**
	 * 十进制转二进制
	 */
	public static String intToBinary(int i){
		return Integer.toBinaryString(i);
	}
}
