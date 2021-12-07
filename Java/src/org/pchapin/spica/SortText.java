/* *************************************************************************
FILE          : SortText.java
PROGRAMMER    : Peter Chapin
LAST REVISION : November 11, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a small program that sorts the text it finds at its standard input.
************************************************************************* */
package org.pchapin.spica;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class SortText {

    public static void main(String[] argv)
        throws java.io.IOException
    {
        // Create a suitable input file object.
        InputStreamReader myReader =
            new InputStreamReader(System.in);
        BufferedReader myBufferedReader =
            new BufferedReader(myReader);

        // Create an ArrayList to hold the text.
        ArrayList<String> text = new ArrayList<>();

        // Read the text.
        String line;
        while ((line = myBufferedReader.readLine()) != null) {
            text.add(line);
        }

        // Sort it.
        Merge.mergeSort(text);

        // Display it.
        Iterator it = text.iterator();
        while (it.hasNext()) {
            line = (String) it.next();
            System.out.println(line);
        }
    }

}
