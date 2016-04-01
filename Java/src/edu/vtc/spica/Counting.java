/* *************************************************************************
FILE          : Counting.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 2, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a Java implementation of the counting sort algorithm.
************************************************************************* */
package edu.vtc.spica;

import java.util.List;

public class Counting {

    //
    // The following method implements counting sort. It expects a sequence of objects that
    // implement the RangedInt interface. Furthermore every object in the sequence must support
    // the same range (it might be nice to lift this restriction at some point).
    //
    public static void countingSort(List theSequence)
    {
        if (theSequence.size() == 0) return;

        RangedInt x = (RangedInt)theSequence.get(0);
        Object[] output = new Object[theSequence.size()];
        int[] counters = new int[x.upperBound() - x.lowerBound() + 1];

        // Count the number of times each value appears.
        for (int i = 0; i < theSequence.size(); i++) {
            x = (RangedInt)theSequence.get(i);
            counters[x.currentValue() - x.lowerBound()]++;
        }

        // Compute cumulations.
        for (int k = 1; k < counters.length; k++) {
            counters[k] += counters[k-1];
        }

        // Position input into output array.
        for (int i = theSequence.size() - 1; i >= 0; i--) {
            x = (RangedInt)theSequence.get(i);
            output[counters[x.currentValue() - x.lowerBound()] - 1] =
                theSequence.get(i);
            counters[x.currentValue() - x.lowerBound()]--;
        }

        // Copy the output array back to the input.
        for (int i = 0; i < theSequence.size(); i++) {
            theSequence.set(i, output[i]);
        }
    }

}
