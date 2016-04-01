/****************************************************************************
FILE   : splintlib.h
SUBJECT: Splint annotations library for the Spica project.
AUTHOR : Peter C. Chapin

This file contains splint annotations for various third party libraries used in the Spica
project. Note that the header standard.h is provided as part of the splint distribution and
contains annotations for C standard library functions. The header openssl.h was created as part
of this project and contains annotations for OpenSSL types and functions.

Use this file to build splintlib.lcd that can be loaded into splint with a command line option.
Build the library file using a command such as

  splint -nolib -nof splintlib.h -dump splintlib
  
Note that neither standard.h nor openssl.h (nor this file) are included into any C source files.
They exist entire for splint's information.
****************************************************************************/

#include "standard.h"
#include "openssl.h"
