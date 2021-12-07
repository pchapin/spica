/* *************************************************************************
FILE          : RangedInt.java
PROGRAMMER    : Peter Chapin
LAST REVISION : October 8, 2001

(C) Copyright 2001 by Peter Chapin

This file contains a Java interface for ranged integers.
************************************************************************ */

package org.pchapin.spica;


/**
 * Classes that implement this interface can be treated as a ranged integer. The lower and upper
 * bound of the range are returned by the methods declared below. These methods are read-only.
 * The facilities for setting a ranged integer's limits and value are not part of this
 * interface.
 */
interface RangedInt {

    // The smallest possible value.
    int lowerBound();

    // The largest possible value. upperBound >= lowerBound().
    int upperBound();

    // The current value.
    int currentValue();
}
