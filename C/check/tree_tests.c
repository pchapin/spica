
#include <assert.h>
#include <stdlib.h>

#include "Tree.h"
#include "tree_tests.h"

struct Interval {
    int low, high;
};

static int compare_intervals( void *interval_1, void *interval_2 )
{
    struct Interval *i_1 = (struct Interval *)interval_1;
    struct Interval *i_2 = (struct Interval *)interval_2;

    return ( i_1->low < i_2->low );
}


static struct Interval *make_interval(int low, int high)
{
    struct Interval *new_interval = (struct Interval *)malloc( sizeof(struct Interval) );
    assert( new_interval != NULL );
    new_interval->low  = low;
    new_interval->high = high;
    return new_interval;
}


void tree_construction( void )
{
    Tree t1;

    Tree_init( &t1, compare_intervals );
    Tree_insert( &t1, make_interval( 1, 2 ) );

    // This will leak memory because the objects referenced by the tree are not released.
    Tree_destroy( &t1 );
}
