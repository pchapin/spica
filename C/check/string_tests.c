
#include <CUnit/CUnit.h>
#include <string.h>

#include "str.h"
#include "string_tests.h"

void str_construction( void )
{
    string s1;
    int    error_code;

    error_code = string_init( &s1 );
    CU_ASSERT( error_code == 0 );

    // The tests below need improvement. They should carefully exercise both the cases where a
    // reallocation is done and where it is not done by each of the various copy and append
    // functions. Also testing should verify that no memory leaks occur during these operations
    // (using valgrind?)

    string_copycharp( &s1, "Hello" );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "Hello" ) == 0 );

    string_appendcharp( &s1, "World" );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "HelloWorld" ) == 0 );

    string_appendchar( &s1, '!' );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "HelloWorld!" ) == 0 );

    string_destroy( &s1 );
}


void str_prepend( void )
{
    string s1;

    string_init( &s1 );
    string_copycharp( &s1, "Hello" );
    string_prependcharp( &s1, "World" );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "WorldHello" ) == 0 );

    // Similarly to str_construction this test needs to explore a lot more boundary conditions
    // in all the prepending functions.

    string_prependchar( &s1, '!' );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "!WorldHello" ) == 0 );

    string_destroy( &s1 );
}


void str_at( void )
{
    string s1;

    string_init( &s1 );
    string_copycharp( &s1, "Hello" );
    string_putcharat( &s1, 'x', 0 );
    CU_ASSERT( strcmp( string_getcharp( &s1 ), "xello" ) == 0 );
    CU_ASSERT( string_getcharat( &s1, 0 ) == 'x' );

    string_destroy( &s1 );
}
