/*!
 * \file    WorkQueue.h
 * \brief   Interface to a work queue for use in multi-threaded programs.
 * \author  Peter C. Chapin <PChapin@vtc.vsc.edu>
 *
 */

#ifndef WORKQUEUE_H
#define WORKQUEUE_H

#include <pthread.h>
#include <semaphore.h>

typedef struct {
    void **queue;            //!< Pointer to dynamically allocated array of void* work items.
    int    queue_size;       //!< Number of items currently in the queue.
    int    queue_capacity;   //!< Total number of items that could fit in the queue.
    int    next_in;          //!< Index of the slot into which a new item will be pushed.
    int    next_out;         //!< Index of the slot from which an item will be popped.

    // Synchronization primitives.
    pthread_mutex_t lock;
    sem_t used;
    sem_t free;
} WorkQueue;

#ifdef __cplusplus
extern "C" {
#endif

//! Initializes a WorkQueue.
/*!
 * This function dynamically allocates an array to hold the WorkQueue using the specified capacity.
 * There is currently no error return should the allocation fail. However in that case any use of
 * the WorkQueue will result in undefined behavior. (TODO: Fix this!)
 *
 * Every WorkQueue object that has been successfully initialized should be destroyed before program
 * exit. Note also that any attempt to copy a WorkQueue object results in undefined behavior.
 *
 * \param self A pointer to the WorkQueue object to intialize.
 * \param queue_capacity The space allocated for the queue in terms of number of allowed items.
 */
void WorkQueue_initialize( WorkQueue *self, int queue_capacity );


//! Destroys a WorkQueue.
/*!
 * This function recovers the resources of a WorkQueue. Every WorkQueue object that is successfully
 * initialized should be destroyed before program exit. Do not destroy a WorkQueue that is actively
 * being used. Note also that any objects currently in the queue are not deallocated (only the
 * pointers to the objects are recovered).
 *
 * \param self A pointer to the WorkQueue to destroy.
 */
void WorkQueue_destroy( WorkQueue *self );


//! Returns the number of items in the WorkQueue.
int WorkQueue_size( WorkQueue *self );


//! Adds an item to the WorkQueue.
/*!
 * This function adds 'item' to the WorkQueue 'self.' The significance of the item is up to the
 * caller. In particular it need not be dynamically allocated, but it could be. The addition to
 * the WorkQueue is thread safe. If the WorkQueue is full this function blocks (potentially
 * indefinitely) waiting for available space.
 *
 * \param self A pointer to the WorkQueue to which the item will be added.
 * \param item The item to add.
 */
void WorkQueue_push( WorkQueue *self, void *item );


//! Removes an item from the WorkQueue.
/*!
 * This function removes an item from the WorkQueue 'self' and returns it. The removal is
 * thread safe. If the WorkQueue is empty this function blocks (potentially indefinitely) until
 * an item is added. Items are removed from the WorkQueue in FIFO order.
 *
 * \param self A pointer to the WorkQueue from which an item will be removed.
 * \returns The removed item.
 */
void *WorkQueue_pop( WorkQueue *self );

#ifdef __cplusplus
}
#endif

#endif
