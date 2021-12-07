/* *************************************************************************
FILE          : RedBlackTest.java
PROGRAMMER    : Peter C. Chapin
LAST REVISION : 2003-09-26

(C) Copyright 2003 by Peter C. Chapin

This file contains a test program that exercises RedBlackTrees.
************************************************************************* */
package org.pchapin.spica;

import java.io.*;

public class RedBlackTest {

    public static void main(String[] args)
        throws FileNotFoundException, IOException
    {
        int i;

        // Check the command line for a file name.
        if (args.length != 1) {
            System.out.println("Expected a file name on the command line");
            System.exit(1);
        }

        // Create an empty tree and check it.
        RedBlackTree myTree = new RedBlackTree();
        if (myTree.size() != 0) {
            System.out.println(
              "FAILURE: Empty tree has size: " + myTree.size());
            System.exit(1);
        }

        // Open a file full of integers (one integer on a line).
        BufferedReader input = new BufferedReader(new FileReader(args[0]));

        // Read the file a line at a time and store the integers found there into the tree. By using different files,
        // different tests can be conducted. Note: to check add() properly, the sequence of input values should be
        // chosen so that each case in the pseudo code is exercised.
        //
        String line;
        System.out.println("Reading input file... ");
        java.util.ArrayList checkList = new java.util.ArrayList();
        while ((line = input.readLine()) != null) {
            Integer value = Integer.parseInt(line);
            if (myTree.add(value)) {
                checkList.add(value);
            }
            else {
                System.out.println("NOTE: Value " + line + " already in tree");
            }
        }
        input.close();
        System.out.println("done\n");

        System.out.println("NOTE: The size of the tree is " + myTree.size());

        // Now verify that all values in the checkList are in the tree according to contains(). If add() has problems,
        // items might be placed in the tree but then later orphaned as incorrect adjustments are made to add other
        // items. If the input is such that a sufficiently "interesting" tree is created, this can also test every case
        // where contains() is supposed to find an object.
        // 
        for (i = 0; i < checkList.size(); i++) {
            if (!myTree.contains(checkList.get(i))) {
                System.out.println(
                  "FAILURE: Missing an expected item: " + checkList.get(i));
                System.exit(1);
            }
        }
        System.out.println("NOTE: Tree contains all values that were added");

        // Have to search for an item not in the tree.
        Integer badNumber = -1;
        if (myTree.contains(badNumber)) {
            System.out.println(
              "FAILURE: Found unexpected value (-1) in the tree");
            System.exit(1);
        }
        System.out.println("NOTE: Value -1 not found (expected)");

        // Iterate the tree and compare the results with the sorted
        // checkList. Note that the tree and the checkList should have
        // the same number of values. If an exception is thrown during
        // this iteration it may mean that the iteration is returning
        // too many values (and hence this code goes off the end of the
        // checkList). That's a problem.
        // 
        java.util.Collections.sort(checkList);
        i = 0;
        java.util.Iterator it = myTree.iterator();
        while (it.hasNext()) {
            Integer treeElement = (Integer)it.next();
            if (treeElement.compareTo((Integer)checkList.get(i)) != 0) {
                System.out.println(
                  "FAILURE: Iteration returns values in the wrong order");
                System.exit(1);
            }
            i += 1;
        }
        System.out.println("NOTE: Iteration order correct");
        if (i != checkList.size()) {
            System.out.println(
              "FAILURE: Not all values were visited during the iteration.");
            System.exit(1);
        }
        System.out.println("NOTE: All values visited during iteration.");
    }
}
