/* ***************************************************************************
FILE          : ListTest.java
LAST REVISION : 2003-11-01
SUBJECT       : Program to test LinkedList
PROGRAMMER    : (C) Copyright 2003 by Peter C. Chapin

Please send comments or bug reports to
*************************************************************************** */
package edu.vtc.spica;

import java.util.LinkedList;
import java.util.ListIterator;

public class ListTest {

    private static void forwardTest(ListIterator it)
    {
        Integer temp;

        if (it.hasPrevious())
            System.out.println("ERROR: fwd-01");
        if (!it.hasNext())
            System.out.println("ERROR: fwd-02");
        if (it.previousIndex() != -1)
            System.out.println("ERROR: fwd-03");
        if (it.nextIndex() != 0)
            System.out.println("ERROR: fwd-04");

        temp = (Integer)it.next();
        if (temp.compareTo(0) != 0)
            System.out.println("ERROR: fwd-05");
        if (!it.hasPrevious())
            System.out.println("ERROR: fwd-06");
        if (!it.hasNext())
            System.out.println("ERROR: fwd-07");
        if (it.previousIndex() != 0)
            System.out.println("ERROR: fwd-08");
        if (it.nextIndex() != 1)
            System.out.println("ERROR: fwd-09");

        temp = (Integer)it.next();
        if (temp.compareTo(1) != 0)
            System.out.println("ERROR: fwd-10");
        if (!it.hasPrevious())
            System.out.println("ERROR: fwd-11");
        if (!it.hasNext())
            System.out.println("ERROR: fwd-12");
        if (it.previousIndex() != 1)
            System.out.println("ERROR: fwd-13");
        if (it.nextIndex() != 2)
            System.out.println("ERROR: fwd-14");

        temp = (Integer)it.next();
        if (temp.compareTo(2) != 0)
            System.out.println("ERROR: fwd-15");
        if (!it.hasPrevious())
            System.out.println("ERROR: fwd-16");
        if (it.hasNext())
            System.out.println("ERROR: fwd-17");
        if (it.previousIndex() != 2)
            System.out.println("ERROR: fwd-18");
        if (it.nextIndex() != 3)
            System.out.println("ERROR: fwd-19");
    }

    private static void reverseTest(ListIterator it)
    {
        Integer temp;

        if (!it.hasPrevious())
            System.out.println("ERROR: rev-01");
        if (it.hasNext())
            System.out.println("ERROR: rev-02");
        if (it.previousIndex() != 2)
            System.out.println("ERROR: rev-03");
        if (it.nextIndex() != 3)
            System.out.println("ERROR: rev-04");

        temp = (Integer)it.previous();
        if (temp.compareTo(2) != 0)
            System.out.println("ERROR: rev-05");
        if (!it.hasPrevious())
            System.out.println("ERROR: rev-06");
        if (!it.hasNext())
            System.out.println("ERROR: rev-07");
        if (it.previousIndex() != 1)
            System.out.println("ERROR: rev-08");
        if (it.nextIndex() != 2)
            System.out.println("ERROR: rev-09");

        temp = (Integer)it.previous();
        if (temp.compareTo(1) != 0)
            System.out.println("ERROR: rev-10");
        if (!it.hasPrevious())
            System.out.println("ERROR: rev-11");
        if (!it.hasNext())
            System.out.println("ERROR: rev-12");
        if (it.previousIndex() != 0)
            System.out.println("ERROR: rev-13");
        if (it.nextIndex() != 1)
            System.out.println("ERROR: rev-14");

        temp = (Integer)it.previous();
        if (temp.compareTo(0) != 0)
            System.out.println("ERROR: rev-15");
        if (it.hasPrevious())
            System.out.println("ERROR: rev-16");
        if (!it.hasNext())
            System.out.println("ERROR: rev-17");
        if (it.previousIndex() != -1)
            System.out.println("ERROR: rev-18");
        if (it.nextIndex() != 0)
            System.out.println("ERROR: rev-19");
    }

    public static void main(String[] args)
    {
        LinkedList   myList = new LinkedList();
        ListIterator it     = myList.listIterator(0);

        // Building a simple list.
        if (myList.size() != 0)
            System.out.println("ERROR: ini-01");
        System.out.println("Building a list of three elements");
        it.add(0);
        it.add(1);
        it.add(2);
        if (myList.size() != 3)
            System.out.println("ERROR: ini-02");

        System.out.println("Iterating over the list in the forward direction");
        it = myList.listIterator(0);
        forwardTest(it);

        System.out.println("Iterating over the list in the reverse direction");
        it = myList.listIterator(3);
        reverseTest(it);

        System.out.println("Testing complete!");
    }

}
