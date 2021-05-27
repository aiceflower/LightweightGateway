package com.scnu.lwg.controller;

import com.scnu.lwg.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Kin
 * @description jni test controller
 * @email kinsanities@sina.com
 * @time 2021/4/25 12:57 上午
 */

@RequestMapping(value = "/test")
public class TestController {

	@Resource
	OkHttpCli okHttpCli;

	@Resource
	CryptoUtils cryptoUtils;

	@Resource
	WBCryptoUtils wbCryptoUtils;

	/**
	 * test okhttp3
	 * @return
	 */
	@GetMapping(value = "/http")
	public String testFeign() {
		String url = "https://api.github.com/users/aiceflower";
		String message = okHttpCli.doGet(url);
		return message;
	}

	/**
	 * test sm3
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/sm3")
	public String sm3(String str) {
		test();
		/*
		String s = cryptoUtils.wbSm4Enc(str);
		String plan = cryptoUtils.wbSm4Dec(s);

		wbCryptoUtils.genTable("/Users/alonglamp/Desktop/wbsm4.key");
		String s1 = wbCryptoUtils.wbSm4Enc(str);
		String plan1 = cryptoUtils.wbSm4Dec(s1);

		System.out.println(plan.equals(plan1));

		String h1 = CryptoUtils.sm3(str);
		String h2 = WBCryptoUtils.sm3(str);
		System.out.println("h1:" + h1);
		System.out.println("h2:" + h2);
		System.out.println();

		 */
		return "0";
	}

	private void test() {
		String key = "0123456789abcdef";
		byte[] msg1 = key.getBytes();
		System.out.println(msg1.length);
		System.out.println(ByteUtils.byteArrToHexString(msg1));
		byte [] msg = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef,
				(byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};
		byte[] iv_enc = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		byte[] iv_dec = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		byte[] aad = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		byte[] msg1024 = new byte[1024];
		for(int i=0;i<1024;i++){
			msg1024[i] = (byte) (i & 0xff);
		}
		byte[] cipher1024 = new byte[1024];
		byte[] plain1024 = new byte[1024];

		//init ctx
		WBCryptolib.WBCRYPTO_sm4_context sm4_context = WBCryptolib.INSTANCE.WBCRYPTO_sm4_context_init();
		WBCryptolib.INSTANCE.WBCRYPTO_sm4_init_key(sm4_context, msg, msg.length);
		WBCryptolib.WBCRYPTO_wbsm4_context wbsm4_enc_ctx = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_context_init();
		WBCryptolib.WBCRYPTO_wbsm4_context wbsm4_dec_ctx = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_context_init();
		WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gen_table_with_dummyrounds(wbsm4_enc_ctx, msg, msg.length, 1, 1);
		WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gen_table_with_dummyrounds(wbsm4_dec_ctx, msg, msg.length, 0, 1);

		//gcm test
		WBCryptolib.WBCRYPTO_gcm_context gcm_enc_ctx = WBCryptolib.INSTANCE.WBCRYPTO_wbsm4_gcm_init(wbsm4_enc_ctx);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_setiv(gcm_enc_ctx, iv_enc, iv_enc.length);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_aad(gcm_enc_ctx, aad, aad.length);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_encrypt(gcm_enc_ctx, msg1024, msg1024.length, cipher1024, cipher1024.length);
		WBCryptolib.WBCRYPTO_gcm_context gcm_dec_ctx = WBCryptolib.INSTANCE.WBCRYPTO_sm4_gcm_init(sm4_context);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_setiv(gcm_dec_ctx, iv_enc, iv_enc.length);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_aad(gcm_dec_ctx, aad, aad.length);
		WBCryptolib.INSTANCE.WBCRYPTO_gcm_decrypt(gcm_dec_ctx, cipher1024, cipher1024.length, plain1024, plain1024.length);

		String ans= ByteUtils.byteArrToHexString(plain1024);
		System.out.println(ans);
	}

	/**
	 * test encrypt
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/enc")
	public String enc(String str) {
		return cryptoUtils.wbSm4Enc(str);
	}

	/**
	 * test decrypt
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/dec")
	public String dec(String str) {
		return cryptoUtils.wbSm4Dec(str);
	}
}
