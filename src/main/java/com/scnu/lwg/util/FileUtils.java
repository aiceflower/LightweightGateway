package com.scnu.lwg.util;

import java.io.*;

/**
 * @author Kin
 * @description file utils
 * @email kinsanities@sina.com
 * @time 2021/5/10 8:54 下午
 */

public class FileUtils {
	private static String classPath = Thread.currentThread().getContextClassLoader().getResource("native").getPath();
	private static String fileName = "wb_sm4.key";
	private static String filepath = classPath + File.separator + fileName;

	/**
	 * 将字节流转换成文件
	 * @param data
	 * @throws Exception
	 */
	public static void saveFile(byte [] data){
		try {
			if(data != null){
				File file  = new File(filepath);
				if(file.exists()){
					file.delete();
				}
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data,0,data.length);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把一个文件转化为byte字节数组。
	 *
	 * @return
	 */
	public static byte[] fileConvertToByteArray() {
		byte[] data = null;
		try {
			FileInputStream fis = new FileInputStream(filepath);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			int len;
			byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) != -1) {
				bao.write(buffer, 0, len);
			}
			data = bao.toByteArray();
			fis.close();
			bao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
