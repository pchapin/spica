/*!
 * \file    WorkQueue.c
 * \brief   Implementation of a work queue for use in multi-threaded programs.
 * \author  Peter C. Chapin <PChapin@vtc.vsc.edu>
 */

#include <stdlib.h>
#include "WorkQueue.h"


void WorkQueue_initialize( WorkQueue *self, int queue_capacity )
{
    self->queue          = (void **)malloc( queue_capacity * sizeof( void * ) );
    self->queue_size     = 0;
    self->queue_capacity = queue_capacity;
    self->next_in        = 0;
    self->next_out       = 0;

    pthread_mutex_init( &self->lock, NULL );
    sem_init( &self->used, 0, 0 );
    sem_init( &self->free, 0, queue_capacity );
}


void WorkQueue_destroy( WorkQueue *self )
{
    free( self->queue );
    pthread_mutex_destroy( &self->lock );
    sem_destroy( &self->used );
    sem_destroy( &self->free );
}


int WorkQueue_size( WorkQueue *self )
{
    return self->queue_size;
}


void WorkQueue_push( WorkQueue *self, void *item )
{
    sem_wait( &self->free );
    pthread_mutex_lock( &self->lock );
    self->queue[self->next_in] = item;
    self->queue_size++;
    self->next_in++;
    if( self->next_in >= self->queue_capacity ) self->next_in = 0;
    pthread_mutex_unlock( &self->lock );
    sem_post( &self->used );
}


void *WorkQueue_pop( WorkQueue *self )
{
    void *item;

    sem_wait( &self->used );
    pthread_mutex_lock( &self->lock );
    item = self->queue[self->next_out];
    self->queue_size--;
    self->next_out++;
    if( self->next_out >= self->queue_capacity ) self->next_out = 0;
    pthread_mutex_unlock( &self->lock );
    sem_post( &self->free );

    return item;
}
