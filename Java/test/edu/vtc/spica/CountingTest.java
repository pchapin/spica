/* *************************************************************************
FILE          : CountingTest.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 2, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a program that exercises edu.vtc.Counting.countingSort().
************************************************************************* */
package edu.vtc.spica;

public class CountingTest {

    public static void main(String[] argv)
    {
        // The test cases. There are some others that probably should be checked. Also it might be nice to verify
        // correctness automatically by including the expected results here (make each test case a class) and then
        // comparing the sorted results to the expected answers, etc.
        // 
        Object[] Test = {
            new TestInt(3, 0, 9), new TestInt(1, 0, 9), new TestInt(7, 0, 9),
            new TestInt(1, 0, 9), new TestInt(5, 0, 9), new TestInt(7, 0, 9),
            new TestInt(8, 0, 9), new TestInt(0, 0, 9), new TestInt(1, 0, 9)
        };

        // Sort the array and print out the results.
        Counting.countingSort(java.util.Arrays.asList(Test));
        for (int i = 0; i < Test.length; ++i) {
            System.out.print(Test[i].toString() + " ");
        }
        System.out.print("\n");
    }

}
