/* *************************************************************************
FILE          : GraphDemo.java
SUBJECT       : Program that demonstrates various graph algorithms.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains a program to exercise the graph algorithms.
************************************************************************* */
package org.pchapin.spica;

import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * This class demonstrates some graph algorithms in GraphAlgorithms.
 */
public class GraphDemo {

    /**
     * This method reads a SimpleGraph from the file testgraph.txt and then executes a few graph
     * algorithms on it. It interacts with the user to allow the user to try different things.
     */
    public static void main(String[] argv)
    {
        BufferedReader input = new
            BufferedReader(new InputStreamReader(System.in));

        try {
            // Get the graph.
            SimpleGraph myGraph = new SimpleGraph("testgraph.txt");
            System.out.println(
              "I have a graph with " + myGraph.numVertices() + " vertices."
            );

            // Does it have cycles?
            if (GraphAlgorithms.isAcyclic(myGraph)) {
                System.out.println("It is acyclic!\n");
            }
            else {
                System.out.println("It has at least one cycle.\n");
            }

            // Ask the user for the start vertex.
            System.out.println("What should I use for a start vertex?");
            String response = input.readLine();
            int u = Integer.parseInt(response);

            // Compute the single-source-shortest-path tree.
            System.out.println("Computing the single-source tree...");
            GraphAlgorithms.GraphTree myResult = 
                GraphAlgorithms.singleSourceShortestPath(myGraph, u);
            System.out.println("Done!\n");

            // Compute the BFS tree.
            // System.out.println("Computing the BFS tree...");
            // GraphAlgorithms.GraphTree myResult =
            //     GraphAlgorithms.BFS(myGraph, u);
            // System.out.println("Done!\n");

            // Let the user ask about many destination vertices.
            while (true) {

                // Get a destination vertex and quit if desired.
                System.out.println("What destination vertex? (-1 to quit)");
                response = input.readLine();
                int v = Integer.parseInt(response);
                if (v == -1) break;

                // Display distance and path information.
                System.out.println(
                  "Overall distance: " + myResult.getDistance(v)
                );
                System.out.print("Reverse path: ");
                System.out.print(v + " ");
                while (myResult.getPredecessor(v) != -1) {
                    System.out.print(myResult.getPredecessor(v) + " ");
                    v = myResult.getPredecessor(v);
                }
                System.out.print("\n\n");
            }
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("Can't find graph file: testgraph.txt!");
        }
        catch (java.io.IOException e) {
            System.out.println("Error reading graph file: " + e);
        }
    }

}
