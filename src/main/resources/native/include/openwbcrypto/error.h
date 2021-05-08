#ifndef OPEN_WBCRYPTO_ERROR_H
#define OPEN_WBCRYPTO_ERROR_H

#define OPEN_WBCRYPTO_ERR_FILE_IO "An error occurred while reading from or writing to a file."
#define OPEN_WBCRYPTO_ERR_BAD_INPUT_DATA "Bad input parameters to function."
#define OPEN_WBCRYPTO_ERR_ALLOC_FAILED  "Memory allocation failed."
#define OPEN_WBCRYPTO_ERR_NULL_PARAM "Input parameter is empty."
#define OPEN_WBCRYPTO_INLEN_THAN_OUTLEN "The output capacity is not large enough."
#define OPEN_WBCRYPTO_ERR_CALCULATE_ERROR "An error happened in the calculation."
#define OPEN_WBCRYPTO_ERR_INBLOCK_SIZE_WARN "Block size selected is not supported."
#define OPEN_WBCRYPTO_ERR_FILE_SIZE_WRONG "The read length of the file stream is not correct."
#define OPEN_WBCRYPTO_ERR_ENCFILE_MODEIFIED "The encrypted file has been modified, resulting in a different tag."

/**
 * Print the function where the error occurred
 * and the detailed cause of the error
 * and jump to the cleanup breakpoint
 */
#define OPEN_WBCRYPTO_THROW_REASON(func_msg,res_msg)   \
    do{                                                      \
        printf("A error occurred in the \"%s\" function, the reason is: %s\n",func_msg, res_msg); \
        goto cleanup;                                    \
    }while( 0 );

/**
 * Simple error judgment processing:
 * judge whether the return function is correct, 1 is correct, 0 is wrong
 * and jump to the cleanup breakpoint if wrong
 */
#define OPEN_WBCRYPTO_MPI_CHK(func)   \
    do{                             \
        if( func == 0 ) \
            goto cleanup;           \
    }while( 0 );

#endif //OPEN_WBCRYPTO_ERROR_H
