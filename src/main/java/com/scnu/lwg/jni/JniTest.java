package com.scnu.lwg.jni;

import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * @author Kin
 * @description jni test
 * @email kinsanities@sina.com
 * @time 2021/4/24 10:54 下午
 */

@Component
public class JniTest {

	static {
		URL url = Thread.currentThread().getContextClassLoader().getResource("native");
		String path = url.getPath();
		System.load(path+"/libwbcrypto.dylib");
		System.load(path+"/test.so");
	}

	public native int add(int x, int y);
	public native int sub(int x, int y);
	public native int plus(int x, int y);


}
