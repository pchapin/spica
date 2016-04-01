/* *************************************************************************
FILE          : Merge.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 2, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a Java implementation of the merge sort algorithm.
************************************************************************* */
package edu.vtc.spica;

import java.util.List;

public class Merge {

    //
    // The following method merges two sorted subsequences that are stored in theSequence. The
    // first subsequence runs from indices [L, M) while the second subsequence runs from indices
    // [M, U). The resulting merged sequence is stored back in theSequence. Note that this
    // version of merge retains the order of equivalent items and is thus stable.
    // 
    private static void merge(List theSequence, int L, int M, int U)
    {
        // Create a workspace of an appropriate size. I know that this method will not be called
        // unless U - L is at least 2.
        // 
        Object[] workspace = new Object[U - L];

        // Initialize some "pointers" (really array indices).
        int p1 = L;   // Access first subsequence of theSequence.
        int p2 = M;   // Access second subsequence of theSequence.
        int p3 = 0;   // Access the workspace.

        // Keep looping until both subsequences are exhausted.
        while (!(p1 == M && p2 == U)) {

            // If the first subsequence is exhausted...
            if (p1 == M) {
                workspace[p3++] = theSequence.get(p2++);
            }

            // If the second subsequence is exhausted...
            else if (p2 == U) {
                workspace[p3++] = theSequence.get(p1++);
            }

            // If neither are exhausted, compare candidate elements.
            else if ( ((Comparable)theSequence.get(p1)).compareTo(
                      ((Comparable)theSequence.get(p2))) <= 0) {
                workspace[p3++] = theSequence.get(p1++);
            }
            else {
                workspace[p3++] = theSequence.get(p2++);
            }
        }

        // Reset p1 and p3 to point at the beginning of their sequences.
        p1 = L;
        p3 = 0;

        // Copy the sorted object references back into theSequence.
        while (p1 != U) {
            theSequence.set(p1++, workspace[p3++]);
        }
    }


    //
    // The following recursive method implements the merge sort algorithm. It works by breaking
    // the sequence into two sub-sequences of about equal size, sorting each and then using the
    // method above to merge them together. Because the merge method above is stable, this
    // version of merge sort is also stable.
    // 
    private static void mergeSortGuts(List theSequence, int L, int U)
    {
        // A sequence of one or fewer elements is already sorted.
        if (U - L <= 1) return;

        // Compute an approximate midpoint.
        int midIndex = (U + L)/2;

        // Sort the two subsequences.
        mergeSortGuts(theSequence, L, midIndex);
        mergeSortGuts(theSequence, midIndex, U);

        // Combine the result.
        if ( ((Comparable)theSequence.get(midIndex - 1)).compareTo(
             ((Comparable)theSequence.get(midIndex    ))) > 0) {
            merge(theSequence, L, midIndex, U);
        }
    }


    //
    // The following method is the "front end" method for merge sort that clients can call. It
    // simply calls the recursive function specifying the full size of the sequence.
    // 
    public static void mergeSort(List theSequence)
    {
        mergeSortGuts(theSequence, 0, theSequence.size());
    }

}
