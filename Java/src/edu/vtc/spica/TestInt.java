/* *************************************************************************
FILE          : TestInt.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 8, 2001

(C) Copyright 2001 by Peter Chapin

This file contains the implementation of class TestInt. These objects act
like integers except that they also support the RangedInt interface. I
use TestInt objects to test Counting.countingSort().
************************************************************************ */
package edu.vtc.spica;

//
// The following class is used to test countingSort. The countingSort method in class Sorters expects to get a sequence
// of objects that implement the RangedInt interface. This class is modeled after Integer (I can't extend Integer
// because that class is final). It provides the necessary methods for RangedInt as well as the "usual" access and
// comparison methods.
//

public class TestInt implements RangedInt {

    private int myInt;
    private int lower = 0;
    private int upper = 0;

    // Constructor allows me to give this integer a value.
    public TestInt(int newValue, int newLower, int newUpper)
    {
        // FIX: Check sanity and throw an exception if insane.

        myInt = newValue;
        lower = newLower;
        upper = newUpper;
    }

    //
    // The methods of RangedInt must be implemented.
    //

    public int lowerBound()
    {
        return lower;
    }

    public int upperBound()
    {
        return upper;
    }

    public int currentValue()
    {
        return myInt;
    }

    //
    // A couple of other methods that I need to sort, etc.
    //

    public int compareTo(Object other)
    {
        TestInt otherInt = (TestInt)other;
        if (myInt < otherInt.myInt) return -1;
        if (myInt > otherInt.myInt) return +1;
        return 0;
    }

    public int intValue()
    {
        return myInt;
    }

    public String toString()
    {
        return Integer.toString(myInt);
    }
}
