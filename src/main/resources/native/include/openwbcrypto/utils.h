#ifndef OPEN_WBCRYPTO_UTILS_H
#define OPEN_WBCRYPTO_UTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "openwbcrypto/error.h"

/**
 * Expand or reduce the key
 * expand: using PKCS#7 for key expansion
 * reduce: delete bits larger than 16byte
 */
unsigned char *WBCRYPTO_key_padding_pkcs7(const unsigned char *key, size_t keylen);

/**
 * Convert key-file to context
 */
int WBCRYPTO_keystore_file_to_key(void *ctx, size_t ctx_len, char *fpath);

/**
 * Convert T-box to file for storage
 */
int WBCRYPTO_keystore_key_to_file(const void *ctx, size_t ctx_len, char *fpath);

#endif //OPEN_WBCRYPTO_UTILS_H
