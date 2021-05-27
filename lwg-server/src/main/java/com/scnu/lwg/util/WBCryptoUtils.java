package com.scnu.lwg.util;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

/**
 * @author Kin
 * @description wb crypto utils
 * @email kinsanities@sina.com
 * @time 2021/5/23 5:10 下午
 */

public class WBCryptoUtils {
	private String alias = "abc";
	private String key = "000102030405060708090a0b0c0d0e0f";

	private WBCryptolib.WBCRYPTO_wbsm4_context wbSm4EncContext;
	private WBCryptolib.WBCRYPTO_gcm_context gcmEncContext;
	private WBCryptolib.WBCRYPTO_wbsm4_context wbSm4DecContext;
	private WBCryptolib.WBCRYPTO_gcm_context gcmDecContext;

	private Boolean inited = Boolean.FALSE;

	@Value("${mqtt.wb.filepath}")
	private String filePath;

	public void initWithFile(){
		if (!this.inited){
			wbSm4EncContext = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_context_init();
			WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_file2key(wbSm4EncContext, filePath);
			//白盒sm4 gcm模式加解密白盒表一样
			wbSm4DecContext = wbSm4EncContext;

			byte[] iv_enc = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			byte[] iv_dec = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			byte[] aad = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

			//init gcm enc context
			gcmEncContext = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gcm_init(wbSm4EncContext);
			WBCryptolib.INSTANCE.WBCRYPTO_gcm_setiv(gcmEncContext, iv_enc, iv_enc.length);
			WBCryptolib.INSTANCE.WBCRYPTO_gcm_aad(gcmEncContext, aad, aad.length);

			//init gcm dec context
			gcmDecContext = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gcm_init(wbSm4DecContext);
			WBCryptolib.INSTANCE.WBCRYPTO_gcm_setiv(gcmDecContext, iv_dec, iv_dec.length);
			WBCryptolib.INSTANCE.WBCRYPTO_gcm_aad(gcmDecContext, aad, aad.length);

			//update init status
			this.inited = Boolean.TRUE;
		}
	}

	public void genTable(String filePath){
		byte[] keyBytes = ByteUtils.hex2Bytes(this.key);
		//init enc context
		WBCryptolib.WBCRYPTO_wbsm4_context wbsm4_enc_ctx = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_context_init();

		//gen enc table
		WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gen_table_with_dummyrounds(wbsm4_enc_ctx, keyBytes, keyBytes.length, 1, 1);

		//key2file
		WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_key2file(wbsm4_enc_ctx, filePath);
	}

	/**
	 * gcm enc
	 * @param msg
	 * @return
	 */
	public String wbSm4Enc(String msg){
		initWithFile();
		byte[] in = msg.getBytes();
		byte[] out = new byte[in.length];
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_encrypt(gcmEncContext, in, in.length, out, out.length);
		String res = ByteUtils.bytes2Hex(out);
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
		byte[] out = new byte[in.length];
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_decrypt(gcmDecContext, in, in.length, out, out.length);
		return new String(out);
	}

	/**
	 * sm3 hash algorithm
	 * @param msg
	 * @return
	 */
	public static String sm3(String msg){
		byte[] byteMsg = msg.getBytes(StandardCharsets.UTF_8);
		byte[] digest = new byte[32];
		WBCryptolib.INSTANCE.WBCRYPTO_sm3(byteMsg, byteMsg.length, digest);
		return ByteUtils.byteArrToHexString(digest);
	}
}
