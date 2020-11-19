
#include <cassert>
#include <cstdlib>
#include <iostream>
#include <string>
#include <utility>
#include <vector>
#include "SingleList.hpp"

using namespace std;

static void print_list(
    const string &header, SingleList<int> &list, const string &expected_list, int expected_size )
{
    cout << header << ":\n\t     found: ";
    for( auto item : list ) {
        cout << item << " ";
    }
    cout << "\n\t[expecting: " << expected_list << "]\n";
    cout << "\tsize == " << list.size( ) << " [expecting: " << expected_size << "]\n\n";
}


static void test_constructions( )
{
    SingleList<int> list1;
    list1.push_back( 42 );

    SingleList<int> list2;
    list2.push_back( 42 );

    SingleList<int> list1mc( std::move( list1 ) );
    print_list( "list1mc", list1mc, "42", 1 );

    SingleList<int> list2ma;
    list2ma = std::move( list2 );
    print_list( "list2ma", list2ma, "42", 1 );

    SingleList<int> list3;
    list3.push_back( 42 );

    SingleList<int> list4;
    list4.push_back( 42 );

    SingleList<int> list3cc( list3 );
    assert( list3.size( ) == 1 );
    assert( *list3.begin( ) == 42 );
    print_list( "list3cc", list3cc, "42", 1 );

    SingleList<int> list4ca;
    list4ca = list4;
    assert( list4.size( ) == 1 );
    assert( *list4.begin( ) == 42 );
    print_list( "list4ca", list4ca, "42", 1 );
}


static void test_insertions( )
{
    SingleList<int> sample_list1;
    sample_list1.push_back( -99 );  // Onto an empty list.
    sample_list1.push_front( 99 );  // Onto a non-empty list.
    print_list( "sample_list1", sample_list1, "99 -99", 2 );

    SingleList<int> sample_list2;
    sample_list2.push_front( 99 );  // Onto an empty list.
    sample_list2.push_back( -99 );  // Onto a non-empty list.
    print_list( "sample_list2", sample_list2, "99 -99", 2 );

    // Exercises insertion into an empty list and on the back.
    SingleList<int> sample_list3 = { 1, 2, 3, 4, 5 };  // Initializer list constructor.
    sample_list3.push_back( -99 );
    sample_list3.push_front( 99 );
    print_list( "sample_list3", sample_list3, "99 1 2 3 4 5 -99", 7 );

    // Exercises insertion on the front.
    auto it = sample_list3.begin( );
    auto result_it = sample_list3.insert( it, 0 );
    assert( result_it == sample_list3.begin( ) );
    assert( *result_it == 0 );
    print_list( "sample_list3", sample_list3, "0 99 1 2 3 4 5 -99", 8 );

    // Exercises insertion in the middle (and postincrement of an iterator).
    it++;
    result_it = sample_list3.insert( it, 42 );
    assert( *result_it == 42 );
    print_list( "sample_list3", sample_list3, "0 99 42 1 2 3 4 5 -99", 9 );

    // Exercises preincrement of an iterator.
    ++it;
    result_it = sample_list3.insert( it, -42 );
    assert( *result_it == -42 );
    print_list( "sample_list3", sample_list3, "0 99 42 1 -42 2 3 4 5 -99", 10 );

    // Exercises insertion of a sequence via the template insert method.
    std::vector<int> v = { 50, 51 };
    result_it = sample_list3.insert( it, v.begin( ), v.end( ) );
    assert( *result_it == 51 );
    print_list( "sample_list3", sample_list3, "0 99 42 1 -42 50 51 2 3 4 5 -99", 12 );
}


int main( )
{
    test_constructions( );
    test_insertions( );
    return EXIT_SUCCESS;
}
