/****************************************************************************
FILE   : openssl.h
SUBJECT: Splint annotations for the OpenSSL library.
AUTHOR : Peter C. Chapin

This file contains splint annotations for OpenSSL types and functions. Using a separate file
like this allows annotations to be applied without actually editing the OpenSSL headers.

See splintlib.h for information on how this file is used.
****************************************************************************/

#include <stddef.h>

/* Abstract types. */
typedef /*@abstract@*/ struct { } RSA;

/* OpenSSL functions. */
RSA *RSA_generate_key(
  int, unsigned long, /*@null@*/ void (*)(int, int, void *), /*@null@*/ void * );
void RSA_free( /*@only@*/ /*@out@*/ RSA * );
