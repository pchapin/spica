/* *************************************************************************
FILE          : MergeSpeed.java
PROGRAMMER    : Peter Chapin
LAST REVISION : September 14, 2000

(C) Copyright 2000 by Peter Chapin

This file contains a program that demonstrates the performance of merge sort.
************************************************************************* */
package edu.vtc.spica.benchmarks;

import java.util.Arrays;
import java.util.Random;
import edu.vtc.spica.Merge;
import edu.vtc.spica.Timer;

public class MergeSpeed {

    private static final int K = 1024;
    private static final int M = K*K;

    // The main method is the program.
    public static void main(String[] Argv)
    {
        int sequenceSize;

        // Create a random number generator and seed it with zero.
        Random generator = new Random(0);

        // Loop once for each test.
        for (sequenceSize = 256; sequenceSize <= 1*M; sequenceSize *= 2) {

            // Allocate an array of java.lang.Integer references and
            // populate it with random data.
            // 
            Integer[] p = new Integer[sequenceSize];
            for (int i = 0; i < sequenceSize; ++i)
                p[i] = new Integer(generator.nextInt());

            // Do the sort operation, timing it in the process.
            Timer stopwatch = new Timer();
            stopwatch.start();
            Merge.mergeSort(Arrays.asList(p));
            stopwatch.stop();

            // Display the results. How can I format this neatly?
            System.out.println(
              "Size = " + sequenceSize + "; Time = " + stopwatch.time()/1000.0
            );
        }
    }
}
