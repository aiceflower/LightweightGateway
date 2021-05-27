/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <string.h>
#include <wbcrypto/sm3.h>
#include <wbcrypto/wbsm4.h>
#include <string>
#include <map>

/* Header for class com_scnu_lwg_jni_JniInterface */

#ifndef _Included_com_scnu_lwg_jni_JniInterface
#define _Included_com_scnu_lwg_jni_JniInterface
#ifdef __cplusplus
extern "C" {
#endif
using namespace std;

std::map<std::string, WBCRYPTO_wbsm4_context*> lwg_enc_entities;
std::map<std::string, WBCRYPTO_wbsm4_context*> lwg_dec_entities;

void printchar(unsigned char * in, size_t len){
    int i;
    for(i = 0; i < len; i++) {
        printf("%.2X ", in[i]);
        if((i+1)%32==0) {
            printf("\n");
        }
    }
    printf("\n");
}

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    genTable
 * Signature: ([C[B)I
 */
JNIEXPORT jbyteArray JNICALL Java_com_scnu_lwg_jni_JniInterface_genTable
  (JNIEnv *env, jclass j, jbyteArray key_, jint type_){

    jbyte *keyb = env->GetByteArrayElements(key_, nullptr);
    int type = (int)type_;
    printf("type1=%d\n", type);

    //将jbyte转换为char*
    int key_len = env->GetArrayLength(key_);
    unsigned char key[key_len + 1];
    memset(key,0,key_len + 1);
    memcpy(key, keyb, key_len);
    key[key_len] = 0;

    //声明wbsm4指针对象
    WBCRYPTO_wbsm4_context *wbsm4_ctx_enc;
    //初始化wbsm4指针对象，此时对象未计算轮密钥
    wbsm4_ctx_enc=WBCRYPTO_wbsm4_context_init();
    //加密预生成操作
    //其中key是密钥，sizeofkey是密钥长度，WBCRYPTO_ENCREYT_MODE指定预生成为加密密钥（即为1）,1为dummyround轮数(选择1-4均可)
    WBCRYPTO_wbsm4_gen_table_with_dummyrounds(wbsm4_ctx_enc, key, sizeof(key), type, 4);
    void *bytes_enc;
    //进行密钥导入前需对结构体进行初始化操作，并进行预生成表操作，这里省略
    //转换为字符流
    WBCRYPTO_wbsm4_key2bytes(wbsm4_ctx_enc, &bytes_enc);

    int box_len = sizeof(struct wbsm4_context);
    box_len += sizeof(uint32_t);

    unsigned char *d;
    d = (unsigned char*)bytes_enc;

    //printchar(d, box_len);

    jbyteArray res_data = env->NewByteArray(box_len);
    jbyte *buf = (jbyte*)bytes_enc;
    env->SetByteArrayRegion(res_data, 0, box_len,buf);

    fflush(stdout);
    return res_data;
  }

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    initWithBox
 * Signature: (Ljava/lang/String;[BI)I
 */
JNIEXPORT jint JNICALL Java_com_scnu_lwg_jni_JniInterface_initWithBox
  (JNIEnv *env, jclass j, jstring alias_, jbyteArray bytes_enc_, jint type_){
    int type = (int)type_;
    const char *alias = env->GetStringUTFChars(alias_, nullptr);
    jbyte *bytes_enc = env->GetByteArrayElements(bytes_enc_, nullptr);

    WBCRYPTO_wbsm4_context *wbsm4_ctx;
    wbsm4_ctx = WBCRYPTO_wbsm4_context_init();
    WBCRYPTO_wbsm4_bytes2key(wbsm4_ctx, bytes_enc);

    std::string aliasStr = alias;
    std::map<std::string, WBCRYPTO_wbsm4_context *>::iterator it;

    //1-enc, 0-dec
    if (type == 1){
        it = lwg_enc_entities.find(aliasStr);
        if (it != lwg_enc_entities.end()) {
            return -1;
        }
        lwg_enc_entities.insert(std::pair<std::string, WBCRYPTO_wbsm4_context *>(aliasStr, wbsm4_ctx));
        printf("type 1 \n");
    }else {
        it = lwg_dec_entities.find(aliasStr);
        if (it != lwg_dec_entities.end()) {
            return -1;
        }
        lwg_dec_entities.insert(std::pair<std::string, WBCRYPTO_wbsm4_context *>(aliasStr, wbsm4_ctx));
        printf("type 0 \n");
    }
    return 0;
  }

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    wbSm4Enc
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jbyteArray JNICALL Java_com_scnu_lwg_jni_JniInterface_wbSm4Enc
  (JNIEnv *env, jclass j, jstring alias_, jbyteArray in_){
    const char *alias = env->GetStringUTFChars(alias_, nullptr);
    std::string aliasStr = alias;
    jbyte *in = env->GetByteArrayElements(in_, nullptr);
    std::map<std::string, WBCRYPTO_wbsm4_context *>::iterator it = lwg_enc_entities.find(aliasStr);
    // 安全键盘实例不存在
    if (it == lwg_enc_entities.end()) {
        return nullptr;
    }
    WBCRYPTO_wbsm4_context *wbsm4_ctx_enc;
    wbsm4_ctx_enc = it->second;

    int len = (int)env->GetArrayLength(in_);
    //以下是假设的数据
    unsigned char iv_gcm[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};   //iv值：初始化向量
    unsigned char aad_gcm[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};   //aad：附加消息

    unsigned char tag_gcm[16] = {0};        //tag：身份验证标签
    unsigned char *cipher_gcm = (unsigned char *)malloc(len);

    unsigned char *msg_gcm = (unsigned char *)malloc(len);
    msg_gcm = (unsigned char *)in;

    //由于gcm模式下加密和解密的gcm结构体不同，需要各自定义
    WBCRYPTO_gcm_context *gcm_enc;

    //加密
    //使用wbsm4结构体初始化gcm结构体
    //**注意**：gcm模式类似于流密码，gcm加解密时候使用同一密钥结构体即可，一遍选择加密密钥结构体
    gcm_enc=WBCRYPTO_wbsm4_gcm_init(wbsm4_ctx_enc);
    //导入初始化向量iv值
    WBCRYPTO_gcm_setiv(gcm_enc,iv_gcm,sizeof(iv_gcm));
    //导入附加信息aad（长度不限）
    WBCRYPTO_gcm_aad(gcm_enc,aad_gcm,sizeof(aad_gcm));
    //gcm模式加密
    WBCRYPTO_gcm_encrypt(gcm_enc,msg_gcm,len,cipher_gcm,len);

    jbyteArray res_data = env->NewByteArray(len);
    jbyte *buf = (jbyte*)cipher_gcm;
    env->SetByteArrayRegion(res_data, 0, len,
                                    buf);
    return res_data;
  }

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    wbSm4Dec
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jbyteArray JNICALL Java_com_scnu_lwg_jni_JniInterface_wbSm4Dec
  (JNIEnv *env, jclass j, jstring alias_, jbyteArray in_){
      const char *alias = env->GetStringUTFChars(alias_, nullptr);
      std::string aliasStr = alias;
      jbyte *in = env->GetByteArrayElements(in_, nullptr);
      std::map<std::string, WBCRYPTO_wbsm4_context *>::iterator it = lwg_enc_entities.find(aliasStr);
      // 安全键盘实例不存在
      if (it == lwg_enc_entities.end()) {
          return nullptr;
      }
      WBCRYPTO_wbsm4_context *wbsm4_ctx_dec;
      wbsm4_ctx_dec = it->second;

      int len = (int)env->GetArrayLength(in_);
      //以下是假设的数据
      unsigned char iv_gcm[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};   //iv值：初始化向量
      unsigned char aad_gcm[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};   //aad：附加消息

      unsigned char tag_gcm[16] = {0};  //tag：身份验证标签
      unsigned char *cipher_gcm = (unsigned char *)malloc(len);

      unsigned char *msg_gcm = (unsigned char *)malloc(len);
      msg_gcm = (unsigned char *)in;

      WBCRYPTO_gcm_context *gcm_dec;
      //解密（和加密类似，注意要重新声明该结构体）
      //**注意**：此时声明的结构体也为加密密钥结构体，即wbsm4_ctx_enc!!!!
      gcm_dec = WBCRYPTO_wbsm4_gcm_init(wbsm4_ctx_dec);
      WBCRYPTO_gcm_setiv(gcm_dec,iv_gcm,sizeof(iv_gcm));
      WBCRYPTO_gcm_aad(gcm_dec,aad_gcm,sizeof(aad_gcm));
      WBCRYPTO_gcm_decrypt(gcm_dec,msg_gcm, len,cipher_gcm, len);

      jbyteArray res_data = env->NewByteArray(len);
      jbyte *buf = (jbyte*)cipher_gcm;
      env->SetByteArrayRegion(res_data, 0, len, buf);
      return res_data;
    }

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    clear
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_scnu_lwg_jni_JniInterface_clear
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_scnu_lwg_jni_JniInterface
 * Method:    sm3
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jbyteArray JNICALL Java_com_scnu_lwg_jni_JniInterface_sm3
  (JNIEnv *env, jclass j, jstring msg_){

    const char *msg = env->GetStringUTFChars(msg_, nullptr);
    jsize len = env->GetStringUTFLength(msg_);
    //digest用于存储计算出的哈希值，请保证数组长度为32
    unsigned char digest[32]={0};

    //声明sm3结构体
    WBCRYPTO_sm3_context ctx;
    //初始化sm3结构体
    WBCRYPTO_sm3_init(&ctx);
    //进行hash操作
    WBCRYPTO_sm3_update(&ctx, msg, len);
    //得到hash后的哈希值
    WBCRYPTO_sm3_final(&ctx, digest);
    jbyteArray res_data = env->NewByteArray(32);
    jbyte *buf = (jbyte*)digest;
    env->SetByteArrayRegion(res_data, 0, 32,
                                buf);
    return res_data;
  }

#ifdef __cplusplus
}
#endif
#endif