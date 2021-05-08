#ifndef OPEN_WBCRYPTO_SPACE_H
#define OPEN_WBCRYPTO_SPACE_H

#include "openwbcrypto/conf.h"
#include "openwbcrypto/aes.h"

#define WBCRYPTO_SPACE_AES_128_16_8 8
#define WBCRYPTO_SPACE_AES_128_8_16 16
#define WBCRYPTO_SPACE_AES_128_16_24 24
#define WBCRYPTO_SPACE_AES_128_4_32 32

#ifdef __cplusplus
extern "C" {
#endif

    struct space_context{
        int rounds;
        int block_size;
        uint8_t **Tbox;
    };

    typedef struct space_context WBCRYPTO_space_context;

    /**
     * init SPACE_context
     * @param block_size divide SPACE_INPUT_SIZE and every block size，which is also t-box input size, such as:8,16,24,32
     *        using the params：SPACE_space_128_16_8, SPACE_space_128_8_16
     * @param partition_num divide SPACE_INPUT_SIZE into this num
     * @param rounds Encryption/Decryption iteration rounds
     * @return newly create ctx
     */
    WBCRYPTO_space_context *WBCRYPTO_space_context_init(int block_size);

    /**
    * init SPACE_context with key
    * @param ctx space_context, might be NULL
    * @param key the key used to generate T-BOX
    * @param keylen the length of key
    * @return 0 means false, 1 means true
    */
    int WBCRYPTO_space_gen_table_with_key(WBCRYPTO_space_context *ctx, unsigned char *key, size_t keylen);

    /**
     * free context
     * @param ctx
     */
    void WBCRYPTO_space_context_free(WBCRYPTO_space_context *ctx);

    /**
     * Space-encrypt
     * @param ctx the space context
     * @param input plaintext
     * @param output ciphertext
     * @return 0 means false, 1 means true
     */
    int WBCRYPTO_space_encrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_space_context *ctx);

    /**
     * Space-Decrypt
     * @param ctx ctx the space context
     * @param input ciphertext
     * @param output plaintext
     * @return 0 means false, 1 means true
     */
    int WBCRYPTO_space_decrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_space_context *ctx);

    /*****************************************************************************
    * key-store
    ****************************************************************************/
    int WBCRYPTO_space_file_to_key(WBCRYPTO_space_context *ctx, char *fpath);

    int WBCRYPTO_space_key_to_file(const WBCRYPTO_space_context *ctx, char *fpath);

    /*****************************************************************************
     * cbc mode
     ****************************************************************************/
    int WBCRYPTO_space_cbc_encrypt(const unsigned char *in, size_t inlen,
                                   unsigned char *out, size_t outlen,
                                   const WBCRYPTO_space_context *ctx,
                                   unsigned char ivec[16]);
    int WBCRYPTO_space_cbc_decrypt(const unsigned char *in, size_t inlen,
                                   unsigned char *out, size_t outlen,
                                   const WBCRYPTO_space_context *ctx,
                                   unsigned char ivec[16]);

    /*****************************************************************************
    * gcm mode
    ****************************************************************************/
    WBCRYPTO_gcm_context *WBCRYPTO_space_gcm_init(WBCRYPTO_space_context *key);

    /*****************************************************************************
    * gcmfile mode
    ****************************************************************************/
    WBCRYPTO_gcmfile_context *WBCRYPTO_space_gcmfile_init(WBCRYPTO_space_context *key);
    
#ifdef __cplusplus
}
#endif

#endif //OPEN_WBCRYPTO_SPACE_H
