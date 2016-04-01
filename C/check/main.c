
#include <CUnit/CUnit.h>
#include <CUnit/CUCurses.h>
#include <stdio.h>
#include <stdlib.h>

#include "string_tests.h"
#include "thread_pool_tests.h"
#include "tree_tests.h"

int main( void )
{
    int exit_status = EXIT_SUCCESS;

    CU_pSuite str_suite;
    CU_pTest  str_tests[3];

    CU_pSuite tree_suite;
    CU_pTest  tree_tests[1];

    CU_pSuite pool_suite;
    CU_pTest  pool_tests[1];

    if( CU_initialize_registry( ) != CUE_SUCCESS ) {
        printf("Error initializing the CUnit registry!\n");
        exit_status = EXIT_FAILURE;
    }
    else {
        str_suite     = CU_add_suite( "String Tests", NULL, NULL );
        str_tests[0]  = CU_add_test( str_suite, "Construction Test", str_construction );
        str_tests[1]  = CU_add_test( str_suite, "Prepend Test", str_prepend );
        str_tests[2]  = CU_add_test( str_suite, "At Test", str_at );

        tree_suite    = CU_add_suite( "Tree Tests", NULL, NULL );
        tree_tests[0] = CU_add_test( tree_suite, "Construction Test", tree_construction );

        pool_suite    = CU_add_suite( "Thread Pool Tests", NULL, NULL );
        pool_tests[0] = CU_add_test( pool_suite, "Torture Test", thread_pool_torture );

        CU_curses_run_tests( );
        CU_cleanup_registry( );
    }
    return exit_status;
}
