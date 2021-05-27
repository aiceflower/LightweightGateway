package com.scnu.lwg.util;

import com.scnu.lwg.jni.JniInterface;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;


/**
 * @author Kin
 * @description crypto utils
 * @email kinsanities@sina.com
 * @time 2021/5/10 7:07 上午
 */
public class CryptoUtils {

	@Value("${mqtt.lib.crypto}")
	private String libCryptoPath;

	@Value("${mqtt.lib.jni}")
	private String libJniPath;

	@Value("${mqtt.wb.filepath}")
	private String filePath;

	/**
	 * alias
	 */
	private String alias;
	/**
	 * key
	 */
	private String key;

	/**
	 * init status
	 */
	private Boolean inited = false;

	/**
	 * type: 1-enc, 0-dec
	 */
	private int type;

	public CryptoUtils(){
		this("lwg_crypto", "000102030405060708090a0b0c0d0e0f", 1);
	}

	public CryptoUtils(String alias, String key, int type){
		this.alias = alias;
		this.key = key;
		this.type = type;
	}

	/**
	 * gen table
	 * @return
	 */
	public byte[] genTable(){
		byte[] bytes = ByteUtils.hex2Bytes(this.key);
		byte[] table = JniInterface.genTable(bytes, this.type);
		return table;
	}

	/**
	 *
	 * @return
	 */
	public int initWithFile(){
		if (!this.inited){
			byte[] bytes = FileUtils.fileConvertToByteArray(filePath);
			return initWithBox(bytes);
		}
		return 0;
	}

	/**
	 * init with box
	 * @param enc_box
	 * @return
	 */
	public int initWithBox(byte[] enc_box){
		this.inited = true;
		return JniInterface.initWithBox(this.alias, enc_box, this.type);
	}

	/**
	 * gcm enc
	 * @param msg
	 * @return
	 */
	public String wbSm4Enc(String msg){
		initWithFile();
		byte[] in = msg.getBytes();
		byte[] bytes = JniInterface.wbSm4Enc(this.alias, in);
		String res = ByteUtils.bytes2Hex(bytes);
		return res;
	}

	/**
	 * gcm dec
	 * @param msg
	 * @return
	 */
	public String wbSm4Dec(String msg){
		initWithFile();
		byte[] in = ByteUtils.hex2Bytes(msg);
		byte[] bytes = JniInterface.wbSm4Dec(this.alias, in);
		return new String(bytes);
	}

	/**
	 * is inited
	 * @return
	 */
	public Boolean getInited() {
		return inited;
	}

	/**
	 * sm3 hash algorithm
	 * @param msg
	 * @return
	 */
	public static String sm3(String msg){
		byte[] hash = JniInterface.sm3(msg);
		return ByteUtils.bytes2Hex(hash);
	}

	@PostConstruct
	public void loadJniLib(){
		new JniInterface().loadLibs(libCryptoPath, libJniPath);
	}
}
