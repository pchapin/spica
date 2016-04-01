/* *************************************************************************
FILE          : GraphCreate.java
SUBJECT       : A program to create a simple graph.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains a program that creates a simple graph by randomly connecting a large number
of vertices.
************************************************************************* */
package edu.vtc.spica;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.text.NumberFormat;
import java.util.Random;

/**
 * This class contains a main method that generates a graph by randomly connecting a large number of vertices.
 */
public class GraphCreate {

    /**
     * This method computes a random graph. It interacts with the user to obtain the size of the graph to create and the
     * name of the file where the graph description should be written.
     */
    public static void main(String[] argv)
    {
        BufferedReader input = new
            BufferedReader(new InputStreamReader(System.in));

        try {
            // Get the goods.
            System.out.print("Output file name? ");
            String fileName = input.readLine();

            System.out.print("Number of vertices? ");
            String response = input.readLine();
            int vertexCount = Integer.parseInt(response);

            System.out.print("Number of edges? ");
            response = input.readLine();
            int edgeCount = Integer.parseInt(response);

            System.out.print("Lower bound on weight? ");
            response = input.readLine();
            double lowerBound = Double.parseDouble(response);

            System.out.print("Upper bound on weight? ");
            response = input.readLine();
            double upperBound = Double.parseDouble(response);

            // Basic sanity checks.
            if (vertexCount < 0 || edgeCount > (vertexCount * vertexCount) ||
                 upperBound < lowerBound) {
                System.out.println("I don't like those numbers!");
                return;
            }

            // To support negative weight edges a different method needs to be used to mark undefined connections in the
            // edge matrix.
            //
            if (lowerBound < 0.0) {
                System.out.println("Negative weight edges not supported!");
                return;
            }

            System.out.println("Computing edge matrix...");

            // Allocate the necessary array and initialize it.
            double[][] edgeMatrix = new double[vertexCount][vertexCount];
            for (int i = 0; i < vertexCount; ++i) {
                for (int j = 0; j < vertexCount; ++j) {
                    edgeMatrix[i][j] = -1.0;
                }
            }

            // Now generate edgeCount distinct edges.
            Random myGenerator = new Random();
            while (edgeCount > 0) {
                int u = myGenerator.nextInt(vertexCount);
                int v = myGenerator.nextInt(vertexCount);
                if (edgeMatrix[u][v] < 0.0) {
                    double w = myGenerator.nextDouble();
                    w = w*(upperBound - lowerBound) + lowerBound;
                    edgeMatrix[u][v] = w;
                    edgeCount--;
                }
            }

            System.out.println("Writing file...");

            // Spew forth the results.
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(3);
            nf.setMinimumFractionDigits(3);
            FileWriter output = new FileWriter(fileName);
            for (int i = 0; i < vertexCount; i++) {
                String x = i + ": ";
                output.write(x, 0, x.length());
                for (int j = 0; j < vertexCount; j++) {
                    if (edgeMatrix[i][j] < 0.0) continue;
                    x = "(" + j + ", " + nf.format(edgeMatrix[i][j]) + "), ";
                    output.write(x, 0, x.length());
                }
                output.write("\n", 0, 1);
            }

            output.close();
            System.out.println("Done!");
        }
        catch (java.io.IOException e) {
            System.out.println("I/O Exception: " + e);
        }
    }

}
