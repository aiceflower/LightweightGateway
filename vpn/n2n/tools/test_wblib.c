#include <stdio.h>
#include "n2n.h"

//test util
//print string in hexadecimal
void TEST_print_state(unsigned char * in, size_t len);

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
    uint8_t pktbuf[N2N_PKT_BUF_SIZE];
    n2n_trans_op_t transop_speck;

    n2n_edge_conf_t conf;

    // Init configuration
    edge_init_conf_defaults(&conf);
    strncpy((char*)conf.community_name, "abc123def456", sizeof(conf.community_name));
    conf.encrypt_key = "SoMEVer!S$cUREPassWORD";
    
    //init
    n2n_transop_speck_init(&conf, &transop_speck);

    //run_transop_benchmark("transop_speck", &transop_speck, &conf, pktbuf);

    transop_speck.deinit(&transop_speck);

    return 0;
}