/* *************************************************************************
FILE          : Heap.java
PROGRAMMER    : Peter C. Chapin
LAST REVISION : November 11, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a Java implementation of the heap sort and other heap operations. This class
implements all the heap operations as static methods.
************************************************************************* */
package edu.vtc.spica;

import java.util.List;

public class Heap {

    private static int parent(int index)
    {
        return (index - 1)/2;
    }

    private static int left(int index)
    {
        return 2*index + 1;
    }

    private static int right(int index)
    {
        return 2*index + 2;
    }

    private static void heapify(List theSequence, int index)
    {
        int lft     = left(index);
        int rht     = right(index);
        int largest = index;

        // Is the left child bigger?
        if (lft < theSequence.size() &&
              ((Comparable)theSequence.get(lft)).compareTo(
               (Comparable)theSequence.get(index)) > 0) {
            largest = lft;
        }

        // Is the right child bigger than the biggest so far?
        if (rht < theSequence.size() &&
              ((Comparable)theSequence.get(rht)).compareTo(
               (Comparable)theSequence.get(largest)) > 0) {
            largest = rht;
        }

        // If I found a bigger one, swap it into place and continue.
        if (largest != index) {
            Object temp = theSequence.get(index);
            theSequence.set(index, theSequence.get(largest));
            theSequence.set(largest, temp);
            heapify(theSequence, largest);
        }
    }

    public static void build(List theSequence)
    {
        for (int index = theSequence.size()/2 - 1; index >= 0; index--) {
            heapify(theSequence, index);
        }
    }

    public static void sort(List theSequence)
    {
        build(theSequence);
        for (int index = theSequence.size() - 1; index > 0; index--) {
            Object temp = theSequence.get(0);
            theSequence.set(0, theSequence.get(index));
            theSequence.set(index, temp);
            heapify(theSequence.subList(0, index), 0);
        }
    }

    public static Object max(List theSequence)
    {
        return theSequence.get(0);
    }

    public static Object extractMax(List theSequence)
    {
        Object maximum = theSequence.get(0);
        theSequence.set(0, theSequence.get(theSequence.size() - 1));
        theSequence.remove(theSequence.size() - 1);
        heapify(theSequence, 0);
        return maximum;
    }

    public static void insert(List theSequence, Object item)
    {
        theSequence.add(item);

        int index = theSequence.size() - 1;
        while (index > 0 &&
                 ((Comparable)theSequence.get(parent(index))).compareTo(
                  (Comparable)theSequence.get(index)) < 0 ) {
            Object temp = theSequence.get(parent(index));
            theSequence.set(parent(index), theSequence.get(index));
            theSequence.set(index, temp);
            index = parent(index);
        }
    }

}
