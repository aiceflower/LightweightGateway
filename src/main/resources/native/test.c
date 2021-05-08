#include "openwbcrypto/demo.h"
#include "openwbcrypto/aes.h"
#include "openwbcrypto/space.h"
#include "openwbcrypto/whiteblock.h"
#include "openwbcrypto/TEST.h"

void printchar(unsigned char * in, size_t len){
    int i;
    for(i = 0; i < len; i++) {
        printf("%.2X ", in[i]);
        if((i+1)%16==0) {
            printf("\n");
        }
    }
    printf("\n");
}

int test(){
    int i;
    unsigned char key[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    unsigned char iv_enc[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    unsigned char iv_dec[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    unsigned char aad[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    unsigned char msg[1024] = {0};
    unsigned char cipher[1024] = {0};
    unsigned char plain[1024] = {0};
    for(i=0;i<1024;i++){
        msg[i]=i & 0xff;
    }

    //aes
//    WBCRYPTO_aes_context *aes_ctx2=WBCRYPTO_aes_context_init();
//    WBCRYPTO_aes_init_key(aes_ctx2, key, sizeof(key));
//    WBCRYPTO_aes_key_to_file(aes_ctx2, WBCRYPTO_TEST_KEY_FPATH);
//    WBCRYPTO_aes_context *aes_ctx=WBCRYPTO_aes_context_init();
//    WBCRYPTO_aes_file_to_key(aes_ctx, WBCRYPTO_TEST_KEY_FPATH);
//    WBCRYPTO_aes_cbc_encrypt(msg, sizeof(msg), cipher, sizeof(cipher), aes_ctx, iv_enc);
//    WBCRYPTO_aes_cbc_decrypt(cipher,sizeof(cipher),plain,sizeof(plain),aes_ctx,iv_dec);
//
//    WBCRYPTO_gcm_context *gcm_enc,*gcm_dec;
//    gcm_enc=WBCRYPTO_aes_gcm_init(aes_ctx);
//    WBCRYPTO_gcm_setiv(gcm_enc,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcm_aad(gcm_enc,aad,sizeof(aad));
//    WBCRYPTO_gcm_encrypt(gcm_enc,msg,sizeof(msg),cipher,sizeof(cipher));
//
//    gcm_dec=WBCRYPTO_aes_gcm_init(aes_ctx);
//    WBCRYPTO_gcm_setiv(gcm_dec,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcm_aad(gcm_dec,aad,sizeof(aad));
//    WBCRYPTO_gcm_decrypt(gcm_dec,cipher,sizeof(cipher),plain,sizeof(plain));
//
//    WBCRYPTO_gcmfile_context *enc_gcmfile,*dec_gcmfile;
//    enc_gcmfile=WBCRYPTO_aes_gcmfile_init(aes_ctx);
//    WBCRYPTO_gcmfile_setiv(enc_gcmfile,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcmfile_aad(enc_gcmfile,aad,sizeof(aad));
//    WBCRYPTO_gcmfile_encrypt(enc_gcmfile,WBCRYPTO_TEST_FIN_PATH,WBCRYPTO_TEST_FENC_PATH);
//
//    dec_gcmfile=WBCRYPTO_aes_gcmfile_init(aes_ctx);
//    WBCRYPTO_gcmfile_setiv(dec_gcmfile,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcmfile_aad(dec_gcmfile,aad,sizeof(aad));
//    WBCRYPTO_gcmfile_decrypt(dec_gcmfile,WBCRYPTO_TEST_FENC_PATH,WBCRYPTO_TEST_FDEC_PATH);

    //space
    WBCRYPTO_space_context *space_ctx2=WBCRYPTO_space_context_init(WBCRYPTO_SPACE_AES_128_16_8);
    WBCRYPTO_space_gen_table_with_key(space_ctx2, key, sizeof(key));
    WBCRYPTO_space_key_to_file(space_ctx2, WBCRYPTO_TEST_KEY_FPATH);
    WBCRYPTO_space_context *space_ctx=WBCRYPTO_space_context_init(WBCRYPTO_SPACE_AES_128_16_8);
    WBCRYPTO_space_file_to_key(space_ctx, WBCRYPTO_TEST_KEY_FPATH);
	printchar(msg,16);
    WBCRYPTO_space_cbc_encrypt(msg, sizeof(msg), cipher, sizeof(cipher), space_ctx, iv_enc);
	printchar(cipher,16);
    WBCRYPTO_space_cbc_decrypt(cipher,sizeof(cipher),plain,sizeof(plain),space_ctx,iv_dec);
	printchar(plain,16);

    WBCRYPTO_gcm_context *gcm_enc,*gcm_dec;
    gcm_enc=WBCRYPTO_space_gcm_init(space_ctx);
    WBCRYPTO_gcm_setiv(gcm_enc,iv_enc,sizeof(iv_enc));
    WBCRYPTO_gcm_aad(gcm_enc,aad,sizeof(aad));
    WBCRYPTO_gcm_encrypt(gcm_enc,msg,sizeof(msg),cipher,sizeof(cipher));

    //gcm_dec=WBCRYPTO_space_gcm_init(space_ctx);
    //WBCRYPTO_gcm_setiv(gcm_dec,iv_enc,sizeof(iv_enc));
    //WBCRYPTO_gcm_aad(gcm_dec,aad,sizeof(aad));
    //WBCRYPTO_gcm_decrypt(gcm_dec,cipher,sizeof(cipher),plain,sizeof(plain));

    //WBCRYPTO_gcmfile_context *enc_gcmfile,*dec_gcmfile;
    //enc_gcmfile=WBCRYPTO_space_gcmfile_init(space_ctx);
    //WBCRYPTO_gcmfile_setiv(enc_gcmfile,iv_enc,sizeof(iv_enc));
    //WBCRYPTO_gcmfile_aad(enc_gcmfile,aad,sizeof(aad));
    //WBCRYPTO_gcmfile_encrypt(enc_gcmfile,WBCRYPTO_TEST_FIN_PATH,WBCRYPTO_TEST_FENC_PATH);

    //dec_gcmfile=WBCRYPTO_space_gcmfile_init(space_ctx);
    //WBCRYPTO_gcmfile_setiv(dec_gcmfile,iv_enc,sizeof(iv_enc));
    //WBCRYPTO_gcmfile_aad(dec_gcmfile,aad,sizeof(aad));
    //WBCRYPTO_gcmfile_decrypt(dec_gcmfile,WBCRYPTO_TEST_FENC_PATH,WBCRYPTO_TEST_FDEC_PATH);

    //whiteblock
//    WBCRYPTO_whiteblock_context *whiteblock_ctx2=WBCRYPTO_whiteblock_context_init(WBCRYPTO_WHITEBLOCK_AES_128_16, key, sizeof(key));
//    WBCRYPTO_whiteblock_gen_table_with_key(whiteblock_ctx2, key, sizeof(key));
//    WBCRYPTO_whiteblock_key_to_file(whiteblock_ctx2, WBCRYPTO_TEST_KEY_FPATH);
//    WBCRYPTO_whiteblock_context *whiteblock_ctx=WBCRYPTO_whiteblock_context_init(WBCRYPTO_WHITEBLOCK_AES_128_16, key, sizeof(key));
//    WBCRYPTO_whiteblock_file_to_key(whiteblock_ctx, WBCRYPTO_TEST_KEY_FPATH);
//    WBCRYPTO_whiteblock_cbc_encrypt(msg, sizeof(msg), cipher, sizeof(cipher), whiteblock_ctx, iv_enc);
//    WBCRYPTO_whiteblock_cbc_decrypt(cipher,sizeof(cipher),plain,sizeof(plain),whiteblock_ctx,iv_dec);
//
//    WBCRYPTO_gcm_context *gcm_enc,*gcm_dec;
//    gcm_enc=WBCRYPTO_whiteblock_gcm_init(whiteblock_ctx);
//    WBCRYPTO_gcm_setiv(gcm_enc,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcm_aad(gcm_enc,aad,sizeof(aad));
//    WBCRYPTO_gcm_encrypt(gcm_enc,msg,sizeof(msg),cipher,sizeof(cipher));
//
//    gcm_dec=WBCRYPTO_whiteblock_gcm_init(whiteblock_ctx);
//    WBCRYPTO_gcm_setiv(gcm_dec,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcm_aad(gcm_dec,aad,sizeof(aad));
//    WBCRYPTO_gcm_decrypt(gcm_dec,cipher,sizeof(cipher),plain,sizeof(plain));
//
//    WBCRYPTO_gcmfile_context *enc_gcmfile,*dec_gcmfile;
//    enc_gcmfile=WBCRYPTO_whiteblock_gcmfile_init(whiteblock_ctx);
//    WBCRYPTO_gcmfile_setiv(enc_gcmfile,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcmfile_aad(enc_gcmfile,aad,sizeof(aad));
//    WBCRYPTO_gcmfile_encrypt(enc_gcmfile,WBCRYPTO_TEST_FIN_PATH,WBCRYPTO_TEST_FENC_PATH);
//
//    dec_gcmfile=WBCRYPTO_whiteblock_gcmfile_init(whiteblock_ctx);
//    WBCRYPTO_gcmfile_setiv(dec_gcmfile,iv_enc,sizeof(iv_enc));
//    WBCRYPTO_gcmfile_aad(dec_gcmfile,aad,sizeof(aad));
//    WBCRYPTO_gcmfile_decrypt(dec_gcmfile,WBCRYPTO_TEST_FENC_PATH,WBCRYPTO_TEST_FDEC_PATH);

cleanup:
    return 0;
}
