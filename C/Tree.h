/*! \file Tree.h
 *  \brief Declarations of the binary search trees and their operations.
 *  \author Peter C. Chapin <PChapin@vtc.vsc.edu>
 *  \date May 20, 2012
 */

#ifndef TREE_H
#define TREE_H

#include <stddef.h>

enum TreeColor { RED_NODE, BLACK_NODE };

struct TreeNode {
    /*@null@*/ /*@dependent@*/ struct TreeNode *parent;
    /*@null@*/ /*@owned@*/     struct TreeNode *left;
    /*@null@*/ /*@owned@*/     struct TreeNode *right;
               /*@shared@*/    void            *data;
    enum  TreeColor color;
};

typedef /*@abstract@*/ struct {
    /*@null@*/ /*@owned@*/ struct TreeNode *root;
    int ( *compare )( void *x, void *y );
    size_t count;
} Tree;

void Tree_init( /*@out@*/ Tree *tree, int ( *compare )( void *x, void *y ) );
int  Tree_insert( Tree *tree, /*@shared@*/ void *value );
void Tree_destroy( Tree *tree );


#endif
