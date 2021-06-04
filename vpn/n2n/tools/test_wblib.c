#include <stdio.h>
#include <wbcrypto/wbsm4.h>
#include <wbcrypto/sm3.h>
#include <wbcrypto/conf.h>

//test util
//print string in hexadecimal
void TEST_print_state(unsigned char * in, size_t len);
//compare two values, and return 1 if correct and 0 if error
int TEST_cmp_values(char *value1, char *value2);

int test_sm3();
int test_sm4();
int test_wbsm4();
int test_sm4_and_wbsm4();
int test_wbsm4_exkey();

//msg convert to ascii is “abc”
static const unsigned char msg1[3]={0x61, 0x62, 0x63};

static const unsigned char expect_hash1[32]={0x66, 0xc7, 0xf0, 0xf4, 0x62, 0xee, 0xed, 0xd9,
                                            0xd1, 0xf2, 0xd4, 0x6b, 0xdc, 0x10, 0xe4, 0xe2,
                                            0x41, 0x67, 0xc4, 0x87, 0x5c, 0xf2, 0xf7, 0xa2,
                                            0x29, 0x7d, 0xa0, 0x2b, 0x8f, 0x4b, 0xa8, 0xe0};

static const unsigned char msg2[64]={0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,
                                    0x61, 0x62, 0x63, 0x64, 0x61, 0x62, 0x63, 0x64,};

static const unsigned char expect_hash2[32]={0xde, 0xbe, 0x9f, 0xf9, 0x22, 0x75, 0xb8, 0xa1,
                                             0x38, 0x60, 0x48, 0x89, 0xc1, 0x8e, 0x5a, 0x4d,
                                             0x6f, 0xdb, 0x70, 0xe5, 0x38, 0x7e, 0x57, 0x65,
                                             0x29, 0x3d, 0xcb, 0xa3, 0x9c, 0x0c, 0x57, 0x32};

void TEST_print_state(unsigned char * in, size_t len){
    int i;
    for(i = 0; i < len; i++) {
        printf("%.2X ", in[i]);
        if((i+1)%16==0) {
            printf("\n");
        }
    }
    printf("\n");
}

int main()
{
    unsigned char hash[32]={0};
    WBCRYPTO_sm3_context ctx;
    WBCRYPTO_sm3_init(&ctx);
    WBCRYPTO_sm3_update(&ctx, msg1, sizeof(msg1));
    WBCRYPTO_sm3_final(&ctx, hash);
    TEST_print_state(hash, sizeof(hash));

    WBCRYPTO_sm3_init(&ctx);
    WBCRYPTO_sm3_update(&ctx, msg2, sizeof(msg2));
    WBCRYPTO_sm3_final(&ctx, hash);
    TEST_print_state(hash, sizeof(hash));

    WBCRYPTO_sm3(msg1, sizeof(msg1), hash);
    TEST_print_state(hash, sizeof(hash));
    printf("test wblib success.\n");
    return 0;
}