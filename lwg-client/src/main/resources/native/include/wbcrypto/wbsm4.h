#ifndef WBCRYPTO_WBSM4_H
#define WBCRYPTO_WBSM4_H

#include <wbcrypto/conf.h>
#include <WBMatrix/WBMatrix.h>
#include <wbcrypto/sm4.h>

#ifdef __cplusplus
extern "C" {
#endif

    struct wbsm4_context {
        uint32_t rounds;

        uint32_t ssbox_enc[100][4][256];
        Aff32 M[100][3];
        Aff32 C[100];
        Aff32 D[100];

        // start encoding
        Aff32 SE[4];
        Aff32 FE[4];
    };

    typedef struct wbsm4_context WBCRYPTO_wbsm4_context;

    /******************************************basic function**********************************************/
    /**
    * the function initializes the wbsm4 context
    * @param ctx Context to initialize, MUST be NULL
    * @return NULL is fault, otherwise successful
    */
    WBCRYPTO_wbsm4_context *WBCRYPTO_wbsm4_context_init();

    /**
    * free context
    * @param ctx
    */
    void WBCRYPTO_wbsm4_context_free(WBCRYPTO_wbsm4_context *ctx);

    /**
    * generate wbsm4 instance with dummy round
    * @param ctx a pointer to an instance of WBCRYPTO_wbsm4_context
    * @param key encrypt key
    * @param keylen key length
    * @param encmode encrypto mode. {WBCRYPTO_ENCREYT_MODE: encrypto mode; WBCRYPTO_DECREYT_MODE: decrypto mode}
    * @param dummyrounds add extra dummyrounds, 1 dummyround will be expanded to 4 rounds in the runtime
    * @return int 0 is successful, otherwise fault
    */
    int WBCRYPTO_wbsm4_gen_table_with_dummyrounds(WBCRYPTO_wbsm4_context *ctx, const uint8_t *key, size_t keylen, int encmode, int dummyrounds);

    /**
    * the function generate key-tables by the key in the context(the default dummyaround param is 1)
    * @param key key used to generate the key-tables, which need to hide
    * @param keylen key length
    * @param encmode encrypto mode. {WBCRYPTO_ENCREYT_MODE: encrypto mode; WBCRYPTO_DECREYT_MODE: decrypto mode}
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_gen_table(WBCRYPTO_wbsm4_context *ctx, const uint8_t *key, size_t keylen, int encmode);

    /**
    * the function is used to encrypt(**generally not used directly**)
    * @param ctx wbsm4-ctx must be init
    * @param input plaintext
    * @param output ciphertext
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_encrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_wbsm4_context *ctx);

    /**
    * the function is used to decrypt(**generally not used directly**)
    * @param ctx wbsm4-ctx must be init
    * @param input ciphertext
    * @param output plaintext
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_decrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_wbsm4_context *ctx);

    /******************************************key exchange aux-fun*********************************************/
    /**
    * convert T-box to file for storage
    * @param ctx the context with t-box which will convert T-box to file
    * @param fpath filepath for t-box storage
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_key2file(const WBCRYPTO_wbsm4_context *ctx, char *fpath);

    /**
    * generate T-box by reading keystore-file
    * @param ctx the context which is gen t-box by file, must bu NULL
    * @param fpath filepath for t-box storage
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_file2key(WBCRYPTO_wbsm4_context *ctx, char *fpath);

    /**
    * convert T-box to string for exchanging
    * @param ctx the context with t-box which will convert
    * @param kstream key stream
    * @param ks_len key stream length
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_key2bytes(const WBCRYPTO_wbsm4_context *ctx, void **kstream);

    /**
    * convert string to T-box
    * @param ctx the context which is gen t-box by string, must bu NULL
    * @param kstream key stream
    * @param ks_len key stream length
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_bytes2key(WBCRYPTO_wbsm4_context *ctx, const void *kstream);

    /********************************************CBC mode************************************************/
    /**
    * wbsm4 encryption of cbc mode
    * @param in plaintext
    * @param inlen the length of input
    * @param out ciphertext
    * @param outlen the length of output
    * @param ctx wbsm4-ctx must be init
    * @param ivec initialization-vectors, the length must be 16
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_cbc_encrypt(const unsigned char *in, size_t inlen,
                                 unsigned char *out, size_t outlen,
                                 const WBCRYPTO_wbsm4_context *ctx,
                                 unsigned char ivec[16]);

    /**
    * wbsm4 decryption of cbc mode
    * @param in ciphertext
    * @param inlen the length of input
    * @param out plaintext
    * @param outlen the length of output
    * @param ctx wbsm4-ctx must be init
    * @param ivec initialization-vectors, the length must be 16
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_wbsm4_cbc_decrypt(const unsigned char *in, size_t inlen,
                                 unsigned char *out, size_t outlen,
                                 const WBCRYPTO_wbsm4_context *ctx,
                                 unsigned char ivec[16]);

    /********************************************GCM mode************************************************/
    /**
    * init the gcm128 context
    * @param key the context of wbsm4-algorithm, must be init
    * @return NULL is fault, otherwise successful
    */
    WBCRYPTO_gcm_context *WBCRYPTO_wbsm4_gcm_init(WBCRYPTO_wbsm4_context *key);

    /******************************************gcmfile mode*********************************************/
    /**
    * init the gcm-file context
    * @param key the context of wbsm4-algorithm, must be init
    * @return NULL is fault, otherwise successful
    */
    WBCRYPTO_gcmfile_context *WBCRYPTO_wbsm4_gcmfile_init(WBCRYPTO_wbsm4_context *key);


#ifdef __cplusplus
}
#endif

#endif //WBCRYPTO_WBSM4_H
