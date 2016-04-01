/* *************************************************************************
FILE          : Timer.java
PROGRAMMER    : Peter Chapin
LAST REVISION : July 5, 2000

(C) Copyright 2000 by Peter Chapin

This file contains a Java implementation of a simple Timer class.
************************************************************************* */
package edu.vtc.spica;

import java.util.Date;

/**
 * A simple stopwatch-like class. Objects instantiated from Timer are intended act like stopwatches. They accumulate
 * time (in milliseconds) between when they are started and when they are stopped. They can be reset and restarted just
 * like a normal stopwatch.
 *
 * This class depends on the system clock to measure its time intervals. Although it claims to have millisecond
 * resolution, the actual resolution depends on the system clock. For many systems, the actual resolution will be
 * considerably worse than millisecond granularity. Also if the system clock is changed while a Timer is running, the
 * Timer will become confused.
 *
 * @author Peter Chapin
 * */
public class Timer {

    /** A Timer's internal state after it has been constructed or reset. */ 
    public static final int RESET   = 0;

    /** A Timer's internal state when it is running. */
    public static final int RUNNING = 1;

    /** A Timer's internal state when it has been stopped after running. */
    public static final int STOPPED = 2;

    private long startTime;        // Time, in ms, when timer was started.
    private long accumulatedTime;  // Time, in ms, accumulated so far.
    private int  internalState;    // One of the special values above.

    /**
     * This constructor creates a Timer with no accumulated time and in the RESET state.
     * */
     public Timer()
    {
        startTime = 0;
        accumulatedTime = 0;
        internalState = RESET;
    }

    /**
     * This method resets the Timer. It forces the accumulated time to zero and causes the Timer to stop if it is
     * already running.
     */
    public void reset()
    {
        startTime = 0;
        accumulatedTime = 0;
        internalState = RESET;
    }

    /**
     * This method returns the internal state of the Timer.
     *
     * @return The internal state of the Timer as an integer.
     */
    public int state()
    {
        return internalState;
    }

    /**
     * This method starts the Timer by noting the current system time and storing it. Note that time intervals that
     * cross a date boundary are fully supported. If the Timer is already running when this method is called, it is
     * restarted. The accumulated time is retained, but any "open" timing interval is lost.
     */
    public void start()
    {
        internalState = RUNNING;
        Date now = new Date();
        startTime = now.getTime();
    }

    /**
     * This method stops the Timer and updates the internal accumulated time to add in the results of the latest time
     * interval. If the Timer is not running when this method is called, there is no effect.
     */
    public void stop()
    {
        if (internalState == RUNNING) {
            Date now = new Date();
            internalState = STOPPED;
            accumulatedTime += now.getTime() - startTime;
        }
    }

    /**
     * This method returns the current time on the timer (accumulated plus the current interval, if any) in
     * milliseconds. It is permissible to ask for the time on a Timer that is running.
     *
     * @return The total number of milliseconds timed.
     */
    public long time()
    {
        if (internalState != RUNNING) {
            return accumulatedTime;
        }
        else {
            Date now = new Date();
            return accumulatedTime + (now.getTime() - startTime);
        }
    }

}
