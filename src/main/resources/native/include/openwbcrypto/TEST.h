#ifndef OPEN_WBCRYPTO_TEST_H
#define OPEN_WBCRYPTO_TEST_H

#include "openwbcrypto/error.h"
#include "openwbcrypto/conf.h"

#ifdef  __cplusplus
extern "C" { 
#endif
    /**********************************************************************
     * gen table
     *********************************************************************/
    typedef int (*WBCRYPTO_TEST_gen_tbox) (void *ctx, unsigned char *key);

    int TEST_gen_table(char *algname, void *key,
                       WBCRYPTO_TEST_gen_tbox gentable);
    /************************************************************************
     * basic enc/dec mode = ecb mode
     ***********************************************************************/
    typedef int (*WBCRYPTO_TEST_basic_encrypt) (unsigned char *input, unsigned char *output, void *ctx);

    typedef int (*WBCRYPTO_TEST_basic_decrypt) (unsigned char *input, unsigned char *output, void *ctx);

    int TEST_basic_mode_unified_key(char *algname, void *key,
                        WBCRYPTO_TEST_basic_encrypt encrypt,
                        WBCRYPTO_TEST_basic_decrypt decrypt);

    int TEST_basic_mode_separated_key(char *algname, void *enc_key, void *dec_key,
                        WBCRYPTO_TEST_basic_encrypt encrypt,
                        WBCRYPTO_TEST_basic_decrypt decrypt);

    /************************************************************************
     * cbc mode
     ***********************************************************************/
    typedef int (*WBCRYPTO_TEST_cbc_encrypt) (unsigned char *input, unsigned char *output, int inlen,
                                            void *ctx, unsigned char *ivec);

    typedef int (*WBCRYPTO_TEST_cbc_decrypt) (unsigned char *input, unsigned char *output, int inlen,
                                              void *ctx, unsigned char *ivec);

    int TEST_cbc_mode_unified_key(char *algname, void *key,
                                  WBCRYPTO_TEST_cbc_encrypt encrypt,
                                  WBCRYPTO_TEST_cbc_decrypt decrypt);

    int TEST_cbc_mode_separated_key(char *algname, void *enc_key, void *dec_key,
                                    WBCRYPTO_TEST_cbc_encrypt encrypt,
                                    WBCRYPTO_TEST_cbc_decrypt decrypt);

    /************************************************************************
     * gcm mode
     ***********************************************************************/
    typedef int (*WBCRYPTO_TEST_gcm128_setiv_and_add) (void *ctx,
                                                    unsigned char *iv, size_t ivlen,
                                                    unsigned char *aad, size_t addlen);

    typedef int (*WBCRYPTO_TEST_gcm128_encrypt) (void *ctx,
                                                 const unsigned char *in, unsigned char *out,
                                                 size_t len);

    typedef int (*WBCRYPTO_TEST_gcm128_decrypt) (void *ctx,
                                                 const unsigned char *in, unsigned char *out,
                                                 size_t len);

    typedef int (*WBCRYPTO_TEST_gcm128_finish) (void *ctx,
                                                unsigned char *tag, size_t len);

    int TEST_gcm_mode_unified_key(char *algname, void *key,
                                  WBCRYPTO_TEST_basic_encrypt basic_enc,
                                  WBCRYPTO_TEST_gcm128_setiv_and_add setivaad,
                                  WBCRYPTO_TEST_gcm128_encrypt encrypt,
                                  WBCRYPTO_TEST_gcm128_decrypt decrypt);
    /************************************************************************
     * gcm file mode
     ***********************************************************************/
    typedef int (*WBCRYPTO_TEST_gcmfile_setiv_and_add) (void *gcm,
                                                unsigned char *iv, size_t ivlen,
                                                unsigned char *aad, size_t addlen);

    typedef int (*WBCRYPTO_TEST_gcmfile_encrypt) (void *ctx,
                                  char *infpath, char *outfpath);

    typedef int (*WBCRYPTO_TEST_gcmfile_decrypt) (void *ctx,
                                  char *infpath, char *outfpath);

    int TEST_gcm_file_mode(char *algname, void *key,
                        char *infpath, char *encfpath, char *decfpath,
                        WBCRYPTO_TEST_basic_encrypt basic_enc,
                        WBCRYPTO_TEST_gcmfile_setiv_and_add setivaad,
                        WBCRYPTO_TEST_gcmfile_encrypt encfile,
                        WBCRYPTO_TEST_gcmfile_decrypt decfile);
#ifdef  __cplusplus
}
#endif

#endif //OPEN_WBCRYPTO_TEST_H
