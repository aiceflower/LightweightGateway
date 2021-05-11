package com.scnu.lwg.jni;

import java.io.File;
import java.net.URL;

/**
 * @author Kin
 * @description jni interface
 * @email kinsanities@sina.com
 * @time 2021/5/9 12:03 下午
 */

public class JniInterface {

	static {
		URL url = Thread.currentThread().getContextClassLoader().getResource("native");
		String path = url.getPath();
		/**
		 * 加载加解密库
		 */
		System.load(path + File.separator + "libwbcrypto.dylib");
		/**
		 * 加载jni库
		 */
		System.load(path + File.separator + "main.so");
	}

	/**
	 * gen table
	 * @param key
	 * @param type 1-enc table, 0-dec table
	 * @return
	 */
	public static native byte[] genTable(byte[] key, int type);
	public static native int initWithBox(String alias, byte[] enc_box, int type);
	public static native byte[] wbSm4Enc(String alias, byte[] in);
	public static native byte[] wbSm4Dec(String alias, byte[] in);
	public native static int clear(String alias);
	public native static byte[] sm3(String msg);
}
