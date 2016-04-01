/*! \file    BitFile.h
 *  \brief   An abstract type that allows I/O of a file on a bit-by-bit basis.
 *  \author  Peter C. Chapin <PChapin@vtc.vsc.edu>
 */

#ifndef BITFILE_H
#define BITFILE_H

#include <stdio.h>

typedef enum { Bit_In, Bit_Out } BitFileMode;

//! Type for doing I/O of a file on a bit-by-bit basis.
/*!
 *  Certain applications need to read or write files on a bit by bit basis. This type
 *  facilitates such activities. The precise order in which the bits are stored into each byte
 *  of the bit file (ie from bit 0 toward bit 7 or the other way around) is not specified in
 *  this interface. However, files written with put_bit( ) will produce the bits in the same
 *  order when read with get_bit( ).
 */
typedef struct {
    FILE *the_file;          //!< The actual file.
    int   bit_number;        //!< The current position in workspace.
    int   workspace;         //!< One partially filled byte.
    int   workspace_active;  //!< =1 if there are interesting bits in workspace.
    BitFileMode mode;        //!< Is the file an input or an output file?
} BitFile;

#ifdef __cplusplus
extern "C" {
#endif

int    open_bit( BitFile *bf, const char *the_name, BitFileMode the_mode );
size_t read_bitheader( BitFile *bf, void *buffer, size_t number_of_bytes );
int    get_bit( BitFile *bf );
size_t write_bitheader( BitFile *bf, const void *buffer, size_t number_of_bytes );
int    put_bit( BitFile *bf, int bit_value );
int    close_bit( BitFile *bf );

#ifdef __cplusplus
}
#endif

#endif

