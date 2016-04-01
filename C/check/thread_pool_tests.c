/*! \file    thread_pool_tests.c
    \brief   Simple test/demonstration program for ThreadPools
    \author  Peter C. Chapin <PChapin@vtc.vsc.edu>

*/

#include <stdlib.h>
#include <CUnit/CUnit.h>
#include "ThreadPool.h"

#define ITERATION_COUNT 10000

int shared = 0;
pthread_mutex_t lock;

static void *thread_function( void *arg )
{
    int *holder = (int *)malloc( sizeof(int) );
    pthread_mutex_lock( &lock );
    *holder = ++shared;
    pthread_mutex_unlock( &lock );
    return holder;
}

void thread_pool_torture( void )
{
    int         i, j;
    ThreadPool  pool;
    threadid_t *IDs;
    int         thread_count;

    shared = 0;
    pthread_mutex_init( &lock, NULL );
    ThreadPool_initialize( &pool );

    thread_count = ThreadPool_count( &pool );
    IDs = (threadid_t *)malloc( thread_count * sizeof(threadid_t) );
    for( i = 0; i < ITERATION_COUNT; ++i ) {
        for( j = 0; j < thread_count; ++j ) {
            IDs[j] = ThreadPool_start( &pool, thread_function, NULL );
        }

        for( j = 0; j < thread_count; ++j ) {
            free( ThreadPool_result( &pool, IDs[j] ) );
        }
    }
    free( IDs );

    ThreadPool_destroy( &pool );
    pthread_mutex_destroy( &lock );

    CU_ASSERT( shared == thread_count * ITERATION_COUNT );
}
