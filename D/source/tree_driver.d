/****************************************************************************
FILE       : tree_driver.d
SUBJECT    : Test program for binary tree template.
PROGRAMMER : (C) Copyright 2007 by Peter C. Chapin

Please send comments or bug reports to

     Peter C. Chapin
     Computer Information Systems Department
     Vermont Technical College
     Williston, VT 05495
     PChapin@vtc.vsc.edu
****************************************************************************/

import std.stdio;
import tree;

void compare( int[] dump_result, int[] expected )
{
    assert( dump_result.length == expected.length );
    foreach( size_t i, int value; dump_result ) {
        assert( dump_result[i] == expected[i] );
    }
}

void test_find( Tree!(int) my_tree, int[] contents )
{
    int *find_result;
    foreach( int value; contents ) {
        find_result = my_tree.find( value );
        assert( find_result != null && *find_result == value );
    }
    find_result = my_tree.find( 0 );
    assert( find_result == null );
    find_result = my_tree.find( 2 );
    assert( find_result == null );
    find_result = my_tree.find( 9 );
    assert( find_result == null );
}

void check_erase( Tree!(int) my_tree, int[] erase_order, int[]* expected )
{
    foreach( size_t i, int value; erase_order ) {
        my_tree.erase( value );
        my_tree.check_sanity( );
        compare( my_tree.dump( ), expected[i] );
    }
}

int main( )
{
    auto my_tree = new Tree!(int);
    assert( my_tree !is null );

    my_tree.insert( 5 );
    my_tree.insert( 3 );
    my_tree.insert( 7 );
    my_tree.insert( 1 );
    my_tree.insert( 4 );
    my_tree.insert( 6 );
    my_tree.check_sanity( );
    compare( my_tree.dump( ), [ 1, 3, 4, 5, 6, 7 ].dup );
    test_find( my_tree, [ 1, 3, 4, 5, 6, 7 ].dup );
    
    int[][6] expected = [ [ 1, 3, 4, 6, 7 ],
                          [ 1, 3, 4, 7 ],
                          [ 1, 4, 7 ],
                          [ 1, 7 ],
                          [ 7 ],
                          [ ] ];
    check_erase( my_tree, [ 5, 6, 3, 4, 1, 7 ].dup, expected.ptr );
    return( 0 );
}

