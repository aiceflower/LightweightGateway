package com.scnu.lwg.jni;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kin
 * @description jni interface
 * @email kinsanities@sina.com
 * @time 2021/5/9 12:03 下午
 */
@Slf4j
public class JniInterface {

	static volatile boolean loadStatus = false;

	public synchronized void loadLibs(String cryptoPath, String jniPath){
		if (loadStatus){
			return;
		}
		loadStatus = true;
		System.out.println("load crypto lib in path:" + cryptoPath);
		System.out.println("load jni lib in path:" + jniPath);
		log.info("load crypto lib in path:{}", cryptoPath);
		log.info("load jni lib in path:{}", jniPath);
		try {
			System.load(cryptoPath);
			System.load(jniPath);
		}catch (Exception e){
			log.error("load lib error.");
			e.printStackTrace();
		}
	}

	public static boolean isLoadStatus() {
		return loadStatus;
	}

	/**
	static {
		URL url = Thread.currentThread().getContextClassLoader().getResource("native");
		String path = url.getPath();

		///System.load(path + File.separator + "libwbcrypto.dylib");
		System.loadLibrary("wbcrypto");

		///System.load(path + File.separator + "main.so");
		System.loadLibrary("main");
	}
	*/

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
