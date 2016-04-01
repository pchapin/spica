/* *************************************************************************
FILE          : GenerateRandom.java
PROGRAMMER    : Peter C. Chapin
LAST REVISION : 2003-09-26

(C) Copyright 2003 by Peter C. Chapin

This file contains a program that outputs a file of random integers.
************************************************************************* */
package edu.vtc.spica;

import java.util.Random;

public class GenerateRandom {

    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("Expected a size on the command line");
            System.exit(1);
        }
        int size = Integer.parseInt(args[0]);

        Random generator = new Random();
        for (int i = 0; i < size; ++i) {
            System.out.println(generator.nextInt(10000));
        }
    }

}
