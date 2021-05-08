#ifndef OPEN_WBCRYPTO_DEMO_H
#define OPEN_WBCRYPTO_DEMO_H

#include "openwbcrypto/conf.h"

#ifdef __cplusplus
extern "C" {
#endif

    /**********************************************************************
     *                               demo                                 *
     * please imitate the following specifications to write the interface *
     **********************************************************************/

    struct demo_context{
        uint8_t **Tbox;   //(not required param)example: suppose that the demo contains a parameter of T-Box
    };

    typedef struct demo_context WBCRYPTO_demo_context;

    /**
    * the function initializes the demo context
    * @param ctx Context to initialize, MUST NOT be NULL
    * @return NULL is fault, otherwise successful
    */
    WBCRYPTO_demo_context *WBCRYPTO_demo_context_init();

    /**
    * This function frees the demo context.
    * @param ctx The context to free, which must point to an initialized context.
    */
    void WBCRYPTO_demo_context_free(WBCRYPTO_demo_context *ctx);

    /**
    * the function generate T-Box by the key in the context
    * @param key key used to generate the T table, which need to hide
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_demo_gen_table_with_key(WBCRYPTO_demo_context *ctx, unsigned char *key, size_t keylen);

    /**
    * generate T-box by reading file
    * @param ctx the context which is gen t-box by file
    * @param fpath filepath for t-box storage
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_demo_file_to_key(WBCRYPTO_demo_context *ctx, char *fpath);

    /**
     * convert T-box to file for storage
     * @param ctx the context with t-box which will convert T-box to file
     * @param fpath filepath for t-box storage
     * @return 1 if success, 0 if error
     */
    int WBCRYPTO_demo_key_to_file(const WBCRYPTO_demo_context *ctx, char *fpath);

    /**
     * the function is used to encrypt(**generally not used directly**)
     * @param ctx Demo Context must gen tbox
     * @param input plaintext
     * @param output ciphertext
     * @return 1 if success, 0 if error
     */
    int WBCRYPTO_demo_encrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_demo_context *ctx);

    /**
    * the function is used to decrypt(**generally not used directly**)
    * @param ctx Demo Context must gen tbox
    * @param input ciphertext
    * @param output plaintext
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_demo_decrypt(const unsigned char *input, unsigned char *output, WBCRYPTO_demo_context *ctx);

    /********************************************CBC mode************************************************/
    /**
    * demo encryption of cbc mode
    * @param in plaintext
    * @param inlen the length of input
    * @param out ciphertext
    * @param outlen the length of output
    * @param ctx Demo Context must gen tbox
    * @param ivec initialization-vectors, the length must be 16
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_demo_cbc_encrypt(const unsigned char *in, size_t inlen,
                                  unsigned char *out, size_t outlen,
                                  const WBCRYPTO_demo_context *ctx,
                                  unsigned char ivec[16]);

    /**
    * demo decryption of cbc mode
    * @param in ciphertext
    * @param inlen the length of input
    * @param out plaintext
    * @param outlen the length of output
    * @param ctx Demo Context must gen tbox
    * @param ivec initialization-vectors, the length must be 16
    * @return 1 if success, 0 if error
    */
    int WBCRYPTO_demo_cbc_decrypt(const unsigned char *in, size_t inlen,
                                  unsigned char *out, size_t outlen,
                                  const WBCRYPTO_demo_context *ctx,
                                  unsigned char ivec[16]);

    /********************************************GCM mode************************************************/
    /**
     * init the gcm128 context
     * @param key the context of demo-algorithm
     * @return NULL is fault, otherwise successful
     */
    WBCRYPTO_gcm_context *WBCRYPTO_demo_gcm_init(WBCRYPTO_demo_context *key);

    /***************************Encrypt/Decrypt files in GCM mode**********************************/
    /**
     * init the gcm-file context
     * @param key the context of demo-algorithm
     * @return NULL is fault, otherwise successful
     */
    WBCRYPTO_gcmfile_context *WBCRYPTO_demo_gcmfile_init(WBCRYPTO_demo_context *key);

#ifdef __cplusplus
}
#endif

#endif //OPEN_WBCRYPTO_DEMO_H
