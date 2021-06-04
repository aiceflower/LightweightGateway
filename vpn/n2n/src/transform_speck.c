/**
 * (C) 2007-20 - ntop.org and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not see see <http://www.gnu.org/licenses/>
 *
 */

#include "n2n.h"

#define N2N_SPECK_TRANSFORM_VERSION       1  /* version of the transform encoding */
#define N2N_SPECK_IVEC_SIZE               16

#define SPECK_KEY_BYTES (256/8)

/* Speck plaintext preamble */
#define TRANSOP_SPECK_VER_SIZE     1       /* Support minor variants in encoding in one module. */
#define TRANSOP_SPECK_PREAMBLE_SIZE (TRANSOP_SPECK_VER_SIZE + N2N_SPECK_IVEC_SIZE)

typedef unsigned char n2n_speck_ivec_t[N2N_SPECK_IVEC_SIZE];

typedef struct transop_wbsm4 {
  WBCRYPTO_sm4_context *sm4_ctx;
  WBCRYPTO_wbsm4_context *enc_ctx;
  WBCRYPTO_wbsm4_context *dec_ctx;		      /* the round keys for payload encryption & decryption */
} transop_wbsm4_t;

/* ****************************************************** */

static int transop_deinit_speck(n2n_trans_op_t *arg) {
  transop_wbsm4_t *priv = (transop_wbsm4_t *)arg->priv;
  if(priv){
    if(priv->enc_ctx != NULL){
      free(priv->enc_ctx);
    }
    if(priv->dec_ctx != NULL){
      free(priv->dec_ctx);  
    }
    free (priv);
  }
  return 0;
}

/* ****************************************************** */

static void set_speck_iv(transop_wbsm4_t *priv, n2n_speck_ivec_t ivec) {
  // keep in mind the following condition: N2N_SPECK_IVEC_SIZE % sizeof(rand_value) == 0 !
  uint64_t rand_value;
  uint8_t i;

  for (i = 0; i < N2N_SPECK_IVEC_SIZE; i += sizeof(rand_value)) {
    rand_value = n2n_rand();
    memcpy(ivec + i, &rand_value, sizeof(rand_value));
  }
}

static void copyAdd(uint8_t * outbuf, const uint8_t * inbuf, size_t in_len){
  int k = 3;
  int i;
  for(i = 0; i < in_len; i++){
    outbuf[i] = inbuf[i] ^ k;
  }
}
/** The Speck packet format consists of:
 *
 *  - a 8-bit speck encoding version in clear text
 *  - a 128-bit random IV
 *  - encrypted payload.
 *
 *  [V|IIII|DDDDDDDDDDDDDDDDDDDDD]
 *         |<---- encrypted ---->|
 */
static int transop_encode_speck(n2n_trans_op_t * arg,
			       uint8_t * outbuf,
			       size_t out_len,
			       const uint8_t * inbuf,
			       size_t in_len,
			       const uint8_t * peer_mac) {
  int len=0;
  transop_wbsm4_t* priv = (transop_wbsm4_t *)arg->priv;
  if ( out_len >= in_len )
  {
      unsigned char iv_enc[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
      //WBCRYPTO_wbsm4_cbc_encrypt(inbuf, in_len, outbuf, in_len, priv->enc_ctx, iv_enc);
      //memcpy( outbuf, inbuf, in_len );
      uint8_t sm4_key[16] = {0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef,
      
                                    0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10};
      printf("wbsm4 encode. in_len=%d, out_len=%d\n", in_len, out_len);
      
      uint8_t PKT_CONTENT[]={
  0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
  0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
      
      /*
      WBCRYPTO_sm4_context *sm4_ctx;
      sm4_ctx=WBCRYPTO_sm4_context_init();
      
      
      WBCRYPTO_sm4_init_key(sm4_ctx, sm4_key, sizeof(sm4_key));
      
      
      WBCRYPTO_sm4_cbc_encrypt(PKT_CONTENT, sizeof(PKT_CONTENT), sm4_out, sizeof(PKT_CONTENT), sm4_ctx, iv_enc);
      */
      uint8_t sm4_out[2000];
      n2n_print(inbuf, in_len);
      WBCRYPTO_sm4_cbc_encrypt(inbuf, in_len, outbuf, in_len, priv->sm4_ctx, iv_enc);
      printf(".................sm4 enc:\n");
      n2n_print(outbuf, in_len);
      
      //copyAdd(outbuf, inbuf, in_len);
      len = in_len;
      fflush(stdout);
  }
  else
  {
      traceEvent( TRACE_DEBUG, "encode_null %lu too big for packet buffer", in_len );        
  }
  /*
  transop_wbsm4_t * priv = (transop_wbsm4_t *)arg->priv;

  if(in_len <= N2N_PKT_BUF_SIZE) {
    if((in_len + TRANSOP_SPECK_PREAMBLE_SIZE) <= out_len) {
      size_t idx=0;
      
      n2n_speck_ivec_t enc_ivec = {0};

      traceEvent(TRACE_DEBUG, "encode_speck %lu bytes", in_len);

      encode_uint8(outbuf, &idx, N2N_SPECK_TRANSFORM_VERSION);

    
      set_speck_iv(priv, enc_ivec);
      encode_buf(outbuf, &idx, &enc_ivec, N2N_SPECK_IVEC_SIZE);

      len = in_len;

      WBCRYPTO_wbsm4_cbc_encrypt(inbuf, in_len, outbuf + TRANSOP_SPECK_PREAMBLE_SIZE, out_len, priv->enc_ctx, enc_ivec);
      
      traceEvent(TRACE_DEBUG, "encode_speck: encrypted %u bytes.\n", in_len);

      len += TRANSOP_SPECK_PREAMBLE_SIZE;
    } else
      traceEvent(TRACE_ERROR, "encode_speck outbuf too small.");
  } else
    traceEvent(TRACE_ERROR, "encode_speck inbuf too big to encrypt.");
  */
  return len;
}

/* ****************************************************** */

/* See transop_encode_speck for packet format */
static int transop_decode_speck(n2n_trans_op_t * arg,
			       uint8_t * outbuf,
			       size_t out_len,
			       const uint8_t * inbuf,
			       size_t in_len,
			       const uint8_t * peer_mac) {
  int len=0;
  transop_wbsm4_t* priv = (transop_wbsm4_t *)arg->priv;
  if ( out_len >= in_len )
  {
      unsigned char iv_dec[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
      //WBCRYPTO_wbsm4_cbc_decrypt(inbuf, in_len, outbuf, in_len, priv->dec_ctx, iv_dec);
      //memcpy( outbuf, inbuf, in_len);
      printf("wbsm4 decode. in_len=%d, out_len=%d\n", in_len, out_len);
      
      uint8_t sm4_key[16] = {0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef,
                                    0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10};
      
      
      uint8_t pk_con[] = {0x26, 0x77, 0xF4, 0x6B, 0x09, 0xC1, 0x22, 0xCC, 0x97, 0x55, 0x33, 0x10, 0x5B, 0xD4, 0xA2, 0x2A, 
0xB2, 0x9A, 0x3C, 0x8C, 0x3F, 0xC0, 0x06, 0x50, 0xB3, 0xDD, 0xF3, 0x99, 0x6B, 0xA3, 0xC5, 0x83, 
0x5D, 0x27, 0xE0, 0xC0, 0x7C, 0x81, 0x5E, 0x82, 0xE0, 0x48, 0xEE, 0x8E, 0x6E, 0x17, 0x28, 0x46, 
0x6A, 0x68, 0xA3, 0xDA, 0xB5, 0x9D, 0xCB, 0x5A, 0x16, 0x40, 0x23, 0x48, 0xCC, 0x5C, 0x03, 0xCA};
      /*
      WBCRYPTO_sm4_context *sm4_ctx;
      sm4_ctx=WBCRYPTO_sm4_context_init();
      uint8_t sm4_out[2000];
      WBCRYPTO_sm4_init_key(sm4_ctx, sm4_key, sizeof(sm4_key));
      
      
      WBCRYPTO_sm4_cbc_decrypt(pk_con, sizeof(pk_con), sm4_out, sizeof(pk_con), sm4_ctx, iv_dec);
      
      */
      uint8_t sm4_out[2000];
      
      printf("sm4 dec..................:\n");
      n2n_print(inbuf, in_len);
      WBCRYPTO_sm4_cbc_decrypt(inbuf, in_len, outbuf, in_len, priv->dec_ctx, iv_dec);
      n2n_print(outbuf, in_len);

      //copyAdd(outbuf, inbuf, in_len);
      len = in_len;
      fflush(stdout);
  }
  else
  {
      traceEvent( TRACE_NORMAL, "decode_wbsm4 %lu too big for packet buffer", in_len );        
  }
  /*
   transop_wbsm4_t* priv = (transop_wbsm4_t *)arg->priv;

  if(((in_len - TRANSOP_SPECK_PREAMBLE_SIZE) <= N2N_PKT_BUF_SIZE) 
     && (in_len >= TRANSOP_SPECK_PREAMBLE_SIZE) 
  )
  {
    size_t rem=in_len;
    size_t idx=0;
    uint8_t speck_enc_ver=0;
    
    n2n_speck_ivec_t dec_ivec = {0};

    decode_uint8(&speck_enc_ver, inbuf, &rem, &idx );

    if(N2N_SPECK_TRANSFORM_VERSION == speck_enc_ver) {
      traceEvent(TRACE_DEBUG, "decode_speck %lu bytes", in_len);
      len = (in_len - TRANSOP_SPECK_PREAMBLE_SIZE);

      decode_buf((uint8_t *)&dec_ivec, N2N_SPECK_IVEC_SIZE, inbuf, &rem, &idx);

      WBCRYPTO_wbsm4_cbc_decrypt(inbuf + TRANSOP_SPECK_PREAMBLE_SIZE, len, outbuf, out_len, priv->dec_ctx, dec_ivec);
      
      traceEvent(TRACE_DEBUG, "decode_speck: decrypted %u bytes.\n", len);

    } else
      traceEvent(TRACE_ERROR, "decode_speck unsupported Speck version %u.", speck_enc_ver);
  } else
  traceEvent(TRACE_ERROR, "decode_speck inbuf wrong size (%ul) to decrypt.", in_len);
  */
  return in_len;
}

/* ****************************************************** */

static int setup_speck_key(transop_wbsm4_t *priv, const uint8_t *key, ssize_t key_size) {

  uint8_t key_pad[16] = {0};

  int len = key_size > 16 ? 16 : key_size;
  int i;
  for(i = 0; i < len; i++){
    key_pad[i] = key[i];
  }
  uint8_t sm4_key[16] = {0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef,
                                    0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10};

  priv->enc_ctx = WBCRYPTO_wbsm4_context_init();
  priv->dec_ctx = WBCRYPTO_wbsm4_context_init();
  WBCRYPTO_wbsm4_gen_table_with_dummyrounds(priv->enc_ctx, sm4_key, sizeof(sm4_key), WBCRYPTO_ENCREYT_MODE, 1);
  WBCRYPTO_wbsm4_gen_table_with_dummyrounds(priv->dec_ctx, sm4_key, sizeof(sm4_key), WBCRYPTO_DECREYT_MODE, 1);
  
  priv->sm4_ctx = WBCRYPTO_sm4_context_init();
  WBCRYPTO_sm4_init_key(priv->sm4_ctx, sm4_key, sizeof(sm4_key));

  traceEvent(TRACE_DEBUG, "speck key setup completed\n");

  return(0);
}

/* ****************************************************** */

static void transop_tick_speck(n2n_trans_op_t * arg, time_t now) { ; }

/* ****************************************************** */
/* Speck initialization function */
int n2n_transop_speck_init(const n2n_edge_conf_t *conf, n2n_trans_op_t *ttt) {
  
  transop_wbsm4_t *priv;
  const u_char *encrypt_key = (const u_char *)conf->encrypt_key;
  size_t encrypt_key_len = strlen(conf->encrypt_key);

  memset(ttt, 0, sizeof(*ttt));
  ttt->transform_id = N2N_TRANSFORM_ID_SPECK;

  ttt->tick = transop_tick_speck;
  ttt->deinit = transop_deinit_speck;
  ttt->fwd = transop_encode_speck;
  ttt->rev = transop_decode_speck;

  priv = (transop_wbsm4_t*) calloc (1, sizeof(transop_wbsm4_t));
  if(!priv) {
    traceEvent(TRACE_ERROR, "cannot allocate transop_speck_t memory");
    return(-1);
  }
  ttt->priv = priv;

  /* Setup the cipher and key */
  return(setup_speck_key(priv, encrypt_key, encrypt_key_len));
}

