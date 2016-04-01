/*! \file BitFile.c
 *  \brief Declarations of the bit_file methods.
 *  \author Peter C. Chapin <Peter.Chapin@vtc.vsc.edu>
 *  \warning This code has not been extensively tested!
 */

#include <stdio.h>
#include "BitFile.h"

//! Opens a bit file.
/*!
 * It is an error to open a bit file that is already opened without first closing it.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \param the_name Pointer to the file name.
 * \param the_mode Either Bit_In for input files or Bit_Out for output files.
 *
 * \return Zero if the file failed to open; non-zero otherwise.
 */
int open_bit( BitFile *bf, const char *the_name, BitFileMode the_mode )
{
    bf->mode             = the_mode;
    bf->bit_number       = 0;
    bf->workspace        = 0;
    bf->workspace_active = 0;

    if( the_mode == Bit_In )
        bf->the_file = fopen( the_name, "rb" );
    else
        bf->the_file = fopen( the_name, "wb" );
    
    return ( bf->the_file == NULL ) ? 0 : 1;
}


//! Read a header of raw bytes out of a bit file.
/*!
 * This function reads a specified number of bytes out of a bit file and puts them into the
 * specified buffer. It returns the actual number of bytes read. This function is intended to be
 * used to read a header out of a bit file before processing the bits themselves. It should not
 * be used on a bit_file opened as Bit_Out. It should not be used once get_bit( ) has been
 * called.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \param buffer Pointer to buffer to receive the header.
 * \param number_of_bytes The number of bytes to read.
 *
 * \return The number of bytes actually read. If this is less than the number of bytes requested
 * then either an error occurred or the end of the file was encountered.
 */
size_t read_bitheader( BitFile *bf, void *buffer, size_t number_of_bytes )
{
    if( bf->the_file == NULL || bf->mode == Bit_Out ) return 0;
    return fread( buffer, 1, number_of_bytes, bf->the_file );
}


//! Read a single bit from a bit_file.
/*!
 * This function returns the next bit from a bit file.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \return The next bit or EOF on end-of-file or error.
 */
int get_bit( BitFile *bf )
{
    int result;

    if( bf->the_file == NULL || bf->mode == Bit_Out ) return EOF;

    if( bf->workspace_active == 0 ) {
        bf->workspace        = getc( bf->the_file );
        bf->workspace_active = 1;
        bf->bit_number       = 0;
    }
    if( bf->workspace == EOF ) return EOF;
    result = bf->workspace  &  ( 1 << bf->bit_number );
    if( ++bf->bit_number > 7 )
        bf->workspace_active = 0;

    return result != 0;
}


//! Write a header into a bit file.
/*!
 * This function writes a specified number of bytes out of a buffer and puts them into the
 * specified bit file. It returns the actual number of bytes written. This function is intended
 * to be used to write a header into a bit file before processing the bits themselves. It should
 * not be used on BitFile objects opened as Bit_In. It should not be used once put_bit( ) has
 * been called.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \param buffer Pointer to the buffer containing the material to write.
 * \param number_of_bytes The number of bytes to write.
 *
 * \return The number of bytes actually written. If this is less than the number of bytes
 * requested then an error occurred.
 */
size_t write_bitheader( BitFile *bf, const void *buffer, size_t number_of_bytes )
{
    if( bf->the_file == NULL || bf->mode == Bit_In ) return 0;
    return fwrite( buffer, 1, number_of_bytes, bf->the_file );
}


//! Write a single bit to a bit file.
/*!
 * This function writes a bit into a bit file.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \param bit_value The bit to write.
 *
 * \return Zero if the write failed, non-zero otherwise.
 */
int put_bit( BitFile *bf, int bit_value )
{
    int worked = 1;

    if( bf->the_file == NULL || bf->mode == Bit_In ) return 0;
        
    if( bit_value == 0 )
        bf->workspace &= ~( 1 << bf->bit_number );
    else
        bf->workspace |=  ( 1 << bf->bit_number );

    if( ++bf->bit_number > 7 ) {
        if( putc( bf->workspace, bf->the_file ) == EOF ) worked = 0;
        bf->workspace  = 0;
        bf->bit_number = 0;
    }

    return worked;
}


//! Close a bit file.
/*!
 * This function flushes the last partially filled byte to the bit file (for files open as
 * Bit_Out) and closes the file. Bits in the last byte that were not written via put_bit( ) are
 * given the value zero.
 *
 * \param bf Pointer to the BitFile object being operated on.
 * \param object The bit file to close.
 *
 * \return Zero if the close failed, non-zero otherwise.
 */
int close_bit( BitFile *bf )
{
    int worked = 1;

    if( bf->mode == Bit_In )
        return ( fclose( bf->the_file ) == 0 );

    if( bf->bit_number > 0 ) {
        if( putc( bf->workspace, bf->the_file ) == EOF ) worked = 0;
    }
    if( fclose( bf->the_file ) != 0 ) worked = 0;

    return worked;
}
