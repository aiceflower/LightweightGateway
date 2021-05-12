package com.scnu.lwg.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Kin
 * @description file utils
 * @email kinsanities@sina.com
 * @time 2021/5/10 8:54 下午
 */
@Slf4j
public class FileUtils {

	/**
	 * 将字节流转换成文件
	 * @param data
	 * @throws Exception
	 */
	public static void saveFile(String filepath, byte [] data){
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
	public static byte[] fileConvertToByteArray(String filepath) {
		byte[] data = null;
		try {
			File f = new File(filepath);
			System.out.println("wb file path:" + filepath);
			if (!f.exists()){
				log.error("file not exists.");
				return null;
			}
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
