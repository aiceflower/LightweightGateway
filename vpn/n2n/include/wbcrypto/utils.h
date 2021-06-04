#ifndef WBCRYPTO_UTILS_H
#define WBCRYPTO_UTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <wbcrypto/error.h>

#define WBCRYPTO_PADDING_NUM 16

/**
 * expand or reduce the key with #pkcs7 standard
 * expand: using PKCS#7 for key expansion
 * reduce: delete bits larger than 16byte
 * @param src initial key
 * @param srclen initial key length
 * @param dst padding key, must be init
 * @return padding key length
 */
size_t WBCRYPTO_key_padding_pkcs7(const unsigned char *src, size_t srclen, unsigned char *dst);

/**
 * expand or reduce the key
 * expand: using zero for expansion
 * reduce: delete bits larger than 16byte
 * @param src initial key
 * @param srclen initial key length
 * @param dst padding key, must be init
 * @return padding key length
 */
size_t WBCRYPTO_key_padding_zeros(const unsigned char *src, size_t srclen, unsigned char *dst);

/**
 * Convert key-file to context
 */
int WBCRYPTO_keystore_file_to_key(void *ctx, size_t ctx_len, char *fpath);

/**
 * Convert T-box to file for storage
 */
int WBCRYPTO_keystore_key_to_file(const void *ctx, size_t ctx_len, char *fpath);

int wRandomShuffleU8(uint8_t *list, int len);

#endif //WBCRYPTO_UTILS_H
