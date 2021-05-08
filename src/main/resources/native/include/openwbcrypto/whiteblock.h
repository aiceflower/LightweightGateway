#ifndef OPEN_WBCRYPTO_WHITEBLOCK_H
#define OPEN_WBCRYPTO_WHITEBLOCK_H

#include "openwbcrypto/conf.h"
#include "openwbcrypto/aes.h"

#define WBCRYPTO_WHITEBLOCK_AES_128_16 16
#define WBCRYPTO_WHITEBLOCK_AES_128_20 20
#define WBCRYPTO_WHITEBLOCK_AES_128_24 24
#define WBCRYPTO_WHITEBLOCK_AES_128_28 28
#define WBCRYPTO_WHITEBLOCK_AES_128_32 32

#ifdef __cplusplus
extern "C" {
#endif

    struct whiteblock_context{
        int rounds;
        int block_size;
        uint8_t ***Tbox;
        uint8_t *proc_key;
    };

    typedef struct whiteblock_context WBCRYPTO_whiteblock_context;

    /**
     * init WHITEBLOCK_context
     * @param block_size t-box input size, such as:16，20，24，28，32
     *        using the params：WHITEBLOCK_AES_128_16，WHITEBLOCK_AES_128_20，WHITEBLOCK_AES_128_24
     * @return newly create ctx
     */
    WBCRYPTO_whiteblock_context *WBCRYPTO_whiteblock_context_init(int block_size, unsigned char *proc_key, size_t keylen);

    /**
    * gen WHITEBLOCK t-boxes with key
    * @param ctx whiteblock_context, might be NULL
    * @param key the key used to generate T-BOX
    * @return 0 means false, 1 means true
    */
    int WBCRYPTO_whiteblock_gen_table_with_key(WBCRYPTO_whiteblock_context *ctx, unsigned char *tbox_key, size_t keylen);

    /**
     * free context
     * @param ctx
     */
    void WBCRYPTO_whiteblock_context_free(WBCRYPTO_whiteblock_context *ctx);

    /**
     * WhiteBlock-encrypt
     * @param ctx the whiteblock context
     * @param input plaintext
     * @param output ciphertext
     * @return 0 means false, 1 means true
     */
    int WBCRYPTO_whiteblock_encrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_whiteblock_context *ctx);

    /**
     * WhiteBlock-Decrypt
     * @param ctx ctx the whiteblock context
     * @param input ciphertext
     * @param output plaintext
     * @return 0 means false, 1 means true
     */
    int WBCRYPTO_whiteblock_decrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_whiteblock_context *ctx);

    /*****************************************************************************
    * key-store
    ****************************************************************************/
    int WBCRYPTO_whiteblock_file_to_key(WBCRYPTO_whiteblock_context *ctx, char *fpath);

    int WBCRYPTO_whiteblock_key_to_file(const WBCRYPTO_whiteblock_context *ctx, char *fpath);

    /*****************************************************************************
     * cbc mode
     ****************************************************************************/
    int WBCRYPTO_whiteblock_cbc_encrypt(const unsigned char *in, size_t inlen,
                                        unsigned char *out, size_t outlen,
                                        const WBCRYPTO_whiteblock_context *ctx,
                                        unsigned char ivec[16]);
    int WBCRYPTO_whiteblock_cbc_decrypt(const unsigned char *in, size_t inlen,
                                        unsigned char *out, size_t outlen,
                                        const WBCRYPTO_whiteblock_context *ctx,
                                        unsigned char ivec[16]);

    /*****************************************************************************
    * gcm mode
    ****************************************************************************/
    WBCRYPTO_gcm_context *WBCRYPTO_whiteblock_gcm_init(WBCRYPTO_whiteblock_context *key);

    /*****************************************************************************
    * gcmfile mode
    ****************************************************************************/
    WBCRYPTO_gcmfile_context *WBCRYPTO_whiteblock_gcmfile_init(WBCRYPTO_whiteblock_context *key);

#ifdef __cplusplus
}
#endif

#endif //OPEN_WBCRYPTO_WHITEBLOCK_H
