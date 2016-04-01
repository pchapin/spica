/****************************************************************************
FILE       : tree.d
SUBJECT    : Simple binary tree template.
PROGRAMMER : (C) Copyright 2007 by Peter C. Chapin

Please send comments or bug reports to

     Peter C. Chapin
     Computer Information Systems Department
     Vermont Technical College
     Williston, VT 05495
     PChapin@vtc.vsc.edu
****************************************************************************/

class Tree(T) {

    // Private Section
    //****************
    private {
        class Node {
            public:
                Node left;
                Node right;
                Node parent;
                T    data;
        };

        Node  root;
        uint  count;

        // Returns the number of nodes in the tree.
        uint node_count( const Node p ) const
        {
            if( p is null ) return 0;
            return( node_count( p.left ) + 1 + node_count( p.right ) );
        }

        // Verifies parent pointers.
        void check_parents( const Node p ) const
        {
            if( p is null ) return;
            if( p.left !is null ) {
                assert( p is p.left.parent );
            }
            if( p.right !is null ) {
                assert( p is p.right.parent );
            }
            check_parents( p.left );
            check_parents( p.right );
        }

        // Verifies that the elements are in proper order.
        void check_order( ) const
        {
            if( count < 2 ) return;

            T[] sequential = dump( );
            for( int i = 1; i < sequential.length; ++i ) {
                assert( sequential[i-1] < sequential[i] );
            }
        }
    }

    // Public Section
    //***************

    this( )
    {
        root  = null;
        count = 0;
    }

    // Requires O(n) time and thus not suitable for an invariant section.
    void check_sanity( ) const
    {
        if( root !is null ) assert( root.parent is null );
        check_parents( root );
        check_order( );
        assert( count == node_count( root ) );
    }

    // Dump the tree's contents to an array.
    T[] dump( ) const
    {
        T[] dump_data = new T[count];
        int dump_index = 0;

        // Recursive function that does the dirty work.
        void do_dump( const Node p )
        {
            if( p is null ) return;
            do_dump( p.left );
            dump_data[dump_index] = p.data;
            dump_index++;
            do_dump( p.right );
        }

        do_dump( root );
        return( dump_data );
    }

    // Returns the number of items in the tree.
    uint size( )
    {
        return( count );
    }

    // Adds a copy of the new_item to the tree.
    void insert( in T new_item )
    {
        Node  fresh = new Node;
        fresh.data  = new_item;
        fresh.left  = null;
        fresh.right = null;

        if( count == 0 ) {
            root = fresh;
            fresh.parent = null;
            count = 1;
        }
        else {
            Node current = root;
            Node previous;
            while( current !is null ) {
                previous = current;
                if( new_item < current.data ) current = current.left;
                else if( current.data < new_item ) current = current.right;
                else return;
            }
            fresh.parent = previous;
            if( new_item < previous.data ) previous.left = fresh;
            else if( previous.data < new_item ) previous.right = fresh;
            ++count;
        }
    }

    // Return pointer to item in the tree or null if not present.
    T *find( in T key )
    {
        Node current = root;
        while( current !is null ) {
            if( key < current.data ) current = current.left;
            else if( current.data < key ) current = current.right;
            else return( &current.data );
        }
        return( null );
    }

    // Erases the node with the given key. Does nothing if key not present.
    void erase( in T key )
    {
        Node minimum_node( Node p )
        {
            while( p.left !is null ) p = p.left;
            return( p );
        }

        // Search for the node.
        Node current = root;
        while( current !is null ) {
            if( key < current.data ) current = current.left;
            else if( current.data < key ) current = current.right;
            else break;
        }
        if( current is null ) return;

        // Now erase it.
        Node splice;
        Node rest;
        if( current.left is null || current.right is null ) splice = current;
        else splice = minimum_node( current.right );

        if( splice.left !is null ) rest = splice.left;
        else rest = splice.right;

        if( rest !is null ) rest.parent = splice.parent;
        if( splice.parent is null ) root = rest;
        else if( splice is splice.parent.left ) splice.parent.left = rest;
        else splice.parent.right = rest;

        if( splice !is current ) current.data = splice.data;
        --count;
    }
}


