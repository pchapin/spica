/* ***************************************************************************
FILE          : LinkedList.java
LAST REVISION : 2003-10-27
SUBJECT       : Implementation of a linked list.
PROGRAMMER    : (C) Copyright 2003 by Peter C. Chapin

*************************************************************************** */
package org.pchapin.spica;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList extends AbstractSequentialList
{
    // This class describes the individual nodes.
    private static class ListNode
    {
        public Object   data;      // Reference to data in this node.
        public ListNode next;      // Pointer to next node (or null).
        public ListNode previous;  // Pointer to previous node (or null).
    }

    private int count = 0;
    private ListNode head = null;

    // Return the number of items in this list.
    public int size()
    {
        return count;
    }

    // Return an iterator starting at the specified node.
    public ListIterator listIterator(int index)
    {
        if (index < 0 || index > count)
            throw new IndexOutOfBoundsException();

        // Deal with the just-off-the-end iterator as a special case.
        if (index == count)
            return new LLIterator(this, null);

        // Otherwise step down the list looking for the right node.
        ListNode current = head;
        while (index != 0) {
            current = current.next;
            --index;
        }
        return new LLIterator(this, current);
    }

    // Nested iterator class. This is where all the work is located.
    private static class LLIterator implements ListIterator
    {
        private LinkedList theList;
        private ListNode theNode;
        private ListNode bookmark = null;
        private boolean donePrevious = false;

        public LLIterator(LinkedList myList, ListNode myNode)
        {
            theList = myList;
            theNode = myNode;
        }

        public void add(Object newItem)
        {
            // If the list was initially empty...
            if (theList.count == 0) {
                theList.head = new ListNode();
                theList.head.data = newItem;
                theList.head.next = null;
                theList.head.previous = null;
            }

            // Otherwise the list wasn't empty.
            else {

                // If the current node is the head of the list...
                if (theList.head == theNode) {
                    ListNode newNode = new ListNode();
                    newNode.data = newItem;
                    newNode.next = theList.head;
                    newNode.previous = null;
                    theList.head.previous = newNode;
                    theList.head = newNode;
                }

                // Otherwise the current node isn't the head of the list.
                else {
                    ListNode newNode = new ListNode();
                    newNode.data = newItem;
                    newNode.next = theNode;
                    newNode.previous = theNode.previous; // theNode == null?
                    theNode.previous.next = newNode;
                    theNode.previous = newNode;
                }
            }

            // Take care of common housekeeping.
            bookmark = null;
            ++theList.count;
        }

        public boolean hasNext()
        {
            return (theNode != null);
        }

        public boolean hasPrevious()
        {
            if (theNode == null) {
                return theList.count > 0;
            }
            return (theNode.previous != null);
        }

        public Object next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            Object temp = theNode.data;
            bookmark = theNode;
            donePrevious = false;
            theNode = theNode.next;
            return temp;
        }

        public int nextIndex()
        {
            return 0;
        }

        public Object previous()
        {
            if (!hasPrevious())
                throw new NoSuchElementException();

            // Must handle theNode == null (just off end of list).
            theNode = theNode.previous;
            bookmark = theNode;
            donePrevious = true;
            return theNode.data;
        }

        public int previousIndex()
        {
            return -1;
        }

        public void remove()
        {
            if (bookmark == null)
                throw new IllegalStateException();

            // Bump the current node pointer forward if we are pointing at it.
            if (donePrevious)
                theNode = theNode.next;

            // Unlike the 
            bookmark.previous.next = theNode;
            if (theNode != null) theNode.previous = bookmark.previous;
            bookmark = null;
            --theList.count;
        }

        public void set(Object newItem)
        {
            bookmark.data = newItem;
        }
    }
}
