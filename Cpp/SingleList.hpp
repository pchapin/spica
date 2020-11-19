
#ifndef SINGLELIST_HPP
#define SINGLELIST_HPP

#include <cstddef>
#include <initializer_list>
#include <iterator>
#include <utility>

template<typename T>
class SingleList {
public:
    // The usual typedef names.
    typedef       T  value_type;
    typedef       T *pointer;
    typedef const T *const_pointer;
    typedef       T  &reference;
    typedef const T  &const_reference;
    typedef std::size_t    size_type;
    typedef std::ptrdiff_t difference_type;

private:
    // Each node in the list is an instance of structure Node.
    struct Node {
        T     data;  // The data item in this node.
        Node *next;  // Pointer to the next node in the list or nullptr if there are no others.

        // Convenience constructor. Initializes the fields of Node by copy construction.
        Node( const T &item, Node *p ) : data(item), next(p) { }
    };

    Node *head_node;  // Points at first element in list (or nullptr if list is empty).
    Node *last_node;  // Points at the last element in the list (or nullptr if list is empty).
    size_type count;  // The number of items in the list.

public:
    // Default constructor and destructor.
    SingleList( );
   ~SingleList( ) noexcept;

    // Initializer list constructor.
    SingleList( const std::initializer_list<T> &initializers );

    // Copy operations.
    SingleList( const SingleList &other );
    SingleList &operator=( const SingleList &other );

    // Move operations.
    SingleList( SingleList &&other ) noexcept;
    SingleList &operator=( SingleList &&other ) noexcept;

     // Iterators for SingleList are ForwardIterators.
    class iterator : public std::iterator<std::forward_iterator_tag, T> {
        friend class SingleList;
    private:
        SingleList *object;  // The object into which this iterator points.
        Node *previous; // The node just before the current node.
        Node *current;  // The current node. If nullptr this iterator is "just off the end."

        //! Private constructor can only be used by SingleList's methods.
        iterator( SingleList *o, Node *p, Node *c ) noexcept :
            object(o), previous(p), current(c) { }
    public:
        //! Default constructor creates a NULL iterator.
        iterator( ) noexcept :
            object(nullptr), previous(nullptr), current(nullptr) { }

        //! Preincrement: Advances iterator to next item and returns a reference to itself.
        iterator &operator++( ) noexcept;

        //! Postincrement: Advances iterator to next item and returns original iterator.
        iterator operator++( int ) noexcept;

        //! Returns true if *this and other point at the same object.
        bool operator==( const iterator &other ) const noexcept;

        //! Returns true if *this and other point at different objects.
        bool operator!=( const iterator &other ) const noexcept;

        //! Returns a reference to the item the iterator is pointing at.
        reference operator*( ) const noexcept;

        //! Returns a pointer to the item the iterator is pointing at.
        pointer operator->( ) const noexcept;
    };  // End of iterator class.

    //! Returns the number of items in the list.
    size_type size( ) const noexcept;

    //! Adds item to the front of the list.
    void push_front( const T &item );

    //! Adds item to the end of the list.
    void push_back( const T &item );

    //! Inserts item before the iterator p. Returns iterator to the inserted item.
    // Note that insert requires a reference to an iterator (why?). Is that a problem?
    iterator insert( iterator &p, const T &item );

    //! Inserts sequence from [first, last) before the iterator p.
    //  Returns iterator to last inserted item.
    template<typename InputIterator>
    iterator insert( iterator &p, InputIterator first, InputIterator last );

    //! Returns an iterator to the first item in the list.
    iterator begin( ) noexcept;

    //! Returns an iterator just past the last time in the list.
    iterator end( ) noexcept;
};

// ===========================
// IMPLEMENTATION BEGINS HERE!
// ===========================

template<typename T>
SingleList<T>::SingleList( )
{
    head_node = nullptr;
    last_node = nullptr;
    count = 0;
}


template<typename T>
SingleList<T>::~SingleList( ) noexcept
{
    Node *current = head_node;
    while( current != nullptr ) {
        Node *temp = current->next;
        delete current;
        current = temp;
    }
}


template<typename T>
SingleList<T>::SingleList( const std::initializer_list<T> &initializers )
{
    // Default construct the object.
    head_node = nullptr;
    last_node = nullptr;
    count = 0;

    // Insert the individual items.
    iterator it = end( );
    for( const T &item : initializers ) {
        insert( it, item );
    }
}


template<typename T>
SingleList<T>::SingleList( const SingleList &other )
{
    // First, default construct myself.
    head_node = nullptr;
    last_node = nullptr;
    count = 0;

    // Next, loop over the other list and push_back its items onto myself.
    Node *current = other.head_node;
    while( current != nullptr ) {
        push_back( current->data );
        current = current->next;
    }
}


template<typename T>
SingleList<T> &SingleList<T>::operator=( const SingleList &other )
{
    if( this != &other ) {
        // Copy the other value into a temporary list (for exception safety).
        SingleList<T> temp_list( other );

        // It worked! Move the value from the temporary list into myself.
        *this = std::move( temp_list );
    }
    return *this;
}


template<typename T>
SingleList<T>::SingleList( SingleList &&other ) noexcept :
    head_node( other.head_node ),
    last_node( other.last_node ),
    count( other.count )
{
    // Leave the other object destructable.
    other.head_node = nullptr;
    other.last_node = nullptr;
    other.count = 0;
}


template<typename T>
SingleList<T> &SingleList<T>::operator=( SingleList &&other ) noexcept
{
    if( this != &other ) {
        // Remove the value of the target object.
        Node *current = head_node;
        while( current != nullptr ) {
            Node *temp = current->next;
            delete current;
            current = temp;
        }

        // Transfer the other value.
        head_node = other.head_node;
        last_node = other.last_node;
        count = other.count;

        // Leave the other object destructable.
        other.head_node = nullptr;
        other.last_node = nullptr;
        other.count = 0;
    }
    return *this;
}


template<typename T>
typename SingleList<T>::iterator &SingleList<T>::iterator::operator++( ) noexcept
{
    previous = current;
    current = current->next;
    return *this;
}


template<typename T>
typename SingleList<T>::iterator SingleList<T>::iterator::operator++( int ) noexcept
{
    iterator copy = { object, previous, current };
    previous = current;
    current = current->next;
    return copy;
}


template<typename T>
inline bool SingleList<T>::iterator::operator==( const iterator &other ) const noexcept
{
    return (object == other.object && previous == other.previous && current == other.current);
}


template<typename T>
inline bool SingleList<T>::iterator::operator!=( const iterator &other ) const noexcept
{
    return !(*this == other);
}


template<typename T>
inline typename SingleList<T>::reference SingleList<T>::iterator::operator*( ) const noexcept
{
    return current->data;
}


template<typename T>
inline typename SingleList<T>::pointer SingleList<T>::iterator::operator->( ) const noexcept
{
    return &current->data;
}


template<typename T>
inline typename SingleList<T>::size_type SingleList<T>::size( ) const noexcept
{
    return count;
}


template<typename T>
void SingleList<T>::push_front( const T &item )
{
    Node *new_node = new Node( item, head_node );
    if( head_node == nullptr ) {
        head_node = new_node;
        last_node = new_node;
        count = 1;
    }
    else {
        head_node = new_node;
        count++;
    }
}


template<typename T>
void SingleList<T>::push_back( const T &item )
{
    Node *new_node = new Node( item, nullptr );
    if( last_node == nullptr ) {
        head_node = new_node;
        last_node = new_node;
        count = 1;
    }
    else {
        last_node->next = new_node;
        last_node = new_node;
        count++;
    }
}


template<typename T>
typename SingleList<T>::iterator SingleList<T>::insert( iterator &p, const T &item )
{
    Node *new_node = new Node( item, p.current );
    p.object->count++;

    if( p.previous == nullptr && p.current == nullptr ) {
        // The list is empty.
        p.object->head_node = new_node;
        p.object->last_node = new_node;
        p.previous = new_node;
        return iterator( p.object, nullptr, new_node );
    }
    else if( p.previous == nullptr && p.current != nullptr ) {
        // p points at the first item of a non-empty list.
        p.object->head_node = new_node;
        p.previous = new_node;
        return iterator( p.object, nullptr, new_node );
    }
    else if( p.previous != nullptr && p.current == nullptr ) {
        // p points just past the last item of a non-empty list.
        p.object->last_node = new_node;
        p.previous->next = new_node;
        Node *old_previous = p.previous;
        p.previous = new_node;
        return iterator( p.object, old_previous, new_node );
    }
    else {
        // p points at an item with a well defined previous item.
        p.previous->next = new_node;
        Node *old_previous = p.previous;
        p.previous = new_node;
        return iterator( p.object, old_previous, new_node );
    }
}


template<typename T>
template<typename InputIterator>
typename SingleList<T>::iterator SingleList<T>::insert(
    iterator &p, InputIterator first, InputIterator last )
{
    iterator result( p );

    while( first != last ) {
        result = insert( p, *first );
        ++first;
    }
    return result;
}


template<typename T>
typename SingleList<T>::iterator SingleList<T>::begin( ) noexcept
{
    return iterator( this, nullptr, head_node );
}


template<typename T>
typename SingleList<T>::iterator SingleList<T>::end( ) noexcept
{
    return iterator( this, last_node, nullptr );
}

#endif
