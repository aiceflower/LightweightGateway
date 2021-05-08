#ifndef OPEN_WBCRYPTO_CONF_H
#define OPEN_WBCRYPTO_CONF_H

#include <stddef.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include "openwbcrypto/error.h"
#include "openwbcrypto/modes.h"
#include "openwbcrypto/utils.h"

//encryption mdoes
#define WBCRYPTO_ENCREYT_MODE 1
#define WBCRYPTO_DECREYT_MODE 0

//number of test cycle
#define TEST_CYCLE_NUM 1

//Indicates the path to the file to encrypt and decrypt
// #define WBCRYPTO_TEST_FIN_PATH "/mnt/f/test/test1M.txt"
// #define WBCRYPTO_TEST_FENC_PATH "/mnt/f/test/test.enc.txt"
// #define WBCRYPTO_TEST_FDEC_PATH "/mnt/f/test/test.dec.txt"
#define WBCRYPTO_TEST_FIN_PATH "F:\\test\\test1M.txt"
#define WBCRYPTO_TEST_FENC_PATH "F:\\test\\test.enc.txt"
#define WBCRYPTO_TEST_FDEC_PATH "F:\\test\\test.dec.txt"

// #define WBCRYPTO_TEST_KEY_FPATH "/mnt/f/test/key.whibox"
#define WBCRYPTO_TEST_KEY_FPATH "F:\\test\\key.whibox"

#endif //OPEN_WBCRYPTO_CONF_H
