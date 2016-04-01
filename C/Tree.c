/*! \file Tree.c
 *  \brief Implementation of the binary search trees and their operations.
 *  \author Peter C. Chapin <PChapin@vtc.vsc.edu>
 *  \date May 20, 2012
 */

#include <stdlib.h>
#include "Tree.h"

static void subtree_destroy( /*@null@*/ /*@only@*/ struct TreeNode *subtree )
{
    if( subtree == NULL ) return;
    subtree_destroy( subtree->left );
    subtree_destroy( subtree->right );
    free( subtree );
}


static void subtree_insert(
    /*@dependent@*/ struct TreeNode *current,
    /*@only@*/      struct TreeNode *new_node,
                    int ( *compare )( void *x, void *y ) )
{
    int comparison_result = compare( new_node->data, current->data );
    
    // Insert into the left subtree...
    if( comparison_result < 0 ) {
        if( current->left == NULL ) {
            current->left    = new_node;
            new_node->parent = current;
        }
        else
            subtree_insert( current->left, new_node, compare );
    }
    
    // ... or insert into the right subtree.
    else {
        if( current->right == NULL ) {
            current->right   = new_node;
            new_node->parent = current;
        }
        else
            subtree_insert( current->right, new_node, compare );
    }
}


void Tree_init( Tree *tree, int ( *compare )( void *x, void *y ) )
{
    // Provide default initial values.
    tree->root    = NULL;
    tree->compare = compare;
    tree->count   = 0;   
}


int Tree_insert( Tree *tree, void *value )
{
    struct TreeNode *new_node;
    
    // Set up the new node.
    new_node = (struct TreeNode *)malloc( sizeof( struct TreeNode ) );
    if( new_node == NULL ) return -1;
    new_node->parent = NULL;
    new_node->left   = NULL;
    new_node->right  = NULL;
    new_node->color  = BLACK_NODE;
    new_node->data   = value;
    
    // Add it to the tree.
    if( tree->root == NULL ) {
        tree->root  = new_node;
        tree->count = 1;
    }
    else {
        subtree_insert( tree->root, new_node, tree->compare );
        tree->count++;
    }
    
    return 0;
}


void Tree_destroy( Tree *tree )
{
    // Deallocate all the tree nodes.
    subtree_destroy( tree->root );
    
    // Put the left over tree object into a well defined state.
    tree->root  = NULL;
    tree->count = 0;
}
