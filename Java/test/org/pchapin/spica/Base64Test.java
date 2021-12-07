/* ****************************************************************************
FILE          : Base64Test.java
PROGRAMMER    : Peter Chapin
LAST REVISION : March 14, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a simple program to pseudo-test the Base64OutputStream
class. It takes two file names on the command line and "copies" one file
to the other doing a base64 encoding of the file in the process. This
program also illustrates how one might use the Base64OutputStream class in
a program.
**************************************************************************** */
package org.pchapin.spica;

import java.io.*;

class Base64Test {

    public static void main(String[] argv)
        throws java.io.FileNotFoundException, java.io.IOException
    {
        int ch;

        // Open the input file.
        FileInputStream myInFile = new FileInputStream(argv[0]);
        BufferedInputStream myBufferedInput =
            new BufferedInputStream(myInFile);

        // Open the output file.
        FileOutputStream myOutFile = new FileOutputStream(argv[1]);
        Base64OutputStream myEncoder = new Base64OutputStream(myOutFile);
        BufferedOutputStream myBufferedOutput =
            new BufferedOutputStream(myEncoder);

        // Read all the input a byte at a time and copy to the output.
        while ((ch = myBufferedInput.read()) != -1) {
            myBufferedOutput.write(ch);
        }

        // Close the streams.
        myBufferedInput.close();
        myBufferedOutput.close();
    }

}
