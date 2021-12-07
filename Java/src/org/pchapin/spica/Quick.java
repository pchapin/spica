/* *************************************************************************
FILE          : Quick.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 2, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a Java implementation of the quick sort algorithm.
************************************************************************* */
package org.pchapin.spica;

import java.util.List;

public class Quick {

/* The code below uses the Hoare partitioning method for Quick Sort...

    //
    // The following method is used by quickSort to partition the sequence into two
    // subsequences.
    // 
    private static int partition(List theSequence, int p, int r)
    {
        Object Pivot = theSequence.get(p);
        int    i     = p - 1;
        int    j     = r + 1;

        while (true) {
            do {
                j--;
            } while (((Comparable)theSequence.get(j)).compareTo(
                      (Comparable)Pivot) > 0);

            do {
                i++;
            } while (((Comparable)theSequence.get(i)).compareTo(
                      (Comparable)Pivot) < 0);

            if (i < j) {
                Object Temp = theSequence.get(i);
                theSequence.set(i, theSequence.get(j));
                theSequence.set(j, Temp);
            }
            else {
                return j;
            }
        }
    }


    //
    // The following method implements the guts of the quick sort algorithm.
    // 
    private static void quickSortGuts(List theSequence, int p, int r)
    {
        if (p < r) {
            int q = partition(theSequence, p, r);
            quickSortGuts(theSequence, p, q);
            quickSortGuts(theSequence, q + 1, r);
        }
    }
*/


    //
    // The following method is used by quickSort to partition the sequence into two
    // subsequences.
    // 
    private static int partition(List theSequence, int p, int r)
    {
        Object pivot = theSequence.get(r);
        int i = p - 1;
        for (int j = p; j <= r - 1; ++j) {
            if (((Comparable)theSequence.get(j)).compareTo((Comparable)pivot)
                    <= 0) {
                i++;
                Object temp = theSequence.get(i);
                theSequence.set(i, theSequence.get(j));
                theSequence.set(j, temp);
            }
        }

        Object temp = theSequence.get(i + 1);
        theSequence.set(i + 1, theSequence.get(r));
        theSequence.set(r, temp);

        return i + 1;
    }


    //
    // The following method implements the guts of the quick sort algorithm.
    //
    private static void quickSortGuts(List theSequence, int p, int r)
    {
        if (p < r) {
            int q = partition(theSequence, p, r);
            quickSortGuts(theSequence, p, q - 1);
            quickSortGuts(theSequence, q + 1, r);
        }
    }


    //
    // The following method implements quick sort.
    //
    public static void quickSort(List theSequence)
    {
        quickSortGuts(theSequence, 0, theSequence.size() - 1);
    }

}
