/* *************************************************************************
FILE          : SimpleGraph.java
SUBJECT       : Sample implementation of the Graph interface.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains an example of how one could build an application specific graph class using
the interface in Graph.java (that is implemented abstractly in AbstractGraph.java).
************************************************************************* */

package edu.vtc.spica;

import java.io.FileReader;
import java.io.BufferedReader;

/**
 * This class reads a graph from a simple text file. It is useful for testing. The graph description file is organized
 * in adjacency list format due to its generally more economical use of space. Each line is numbered with a vertex
 * number followed by a colon. After the vertex number comes a space delimited list of (Vertex, Weight) pairs. Here is
 * an example
 *
 * <pre>1: (2, 0.344) (4, 1.38) (3, 2.13)</pre>
 */
public class SimpleGraph extends AbstractGraph<Double> {

    /**
     * The constructor reads and parses the file.
     *
     * @param fileName The name of the file containing the graph description.
     */
    public SimpleGraph(String fileName)
        throws java.io.FileNotFoundException, java.io.IOException
    {
        String line = null;
        BufferedReader input = new BufferedReader(new FileReader(fileName));

        // Read the input file a line at a time.
        while ((line = input.readLine()) != null) {

            // Does this look like a valid line? If not ignore it. This allows me to skip blank lines and treat any line
            // without a colon as a comment.
            // 
            int vertexDelim = line.indexOf(':');
            if (vertexDelim == -1) continue;

            // Extract the "primary" vertex number. Note that the number must start at the beginning of the line and run
            // up to the colon.
            // 
            String vertexNumber = line.substring(0, vertexDelim);
            int u = Integer.parseInt(vertexNumber);

            // Now look for remote vertex information.
            while ((vertexDelim = line.indexOf('(', vertexDelim)) != -1) {

                // Locate the vertex number and weight.
                int vertexEnd = line.indexOf(')', vertexDelim);
                if (vertexEnd == -1) break;
                String vertexInfo = line.substring(vertexDelim + 1, vertexEnd);

                // Break the (vertex, weight) pair into components.
                int comma = vertexInfo.indexOf(',');
                int v = Integer.parseInt(vertexInfo.substring(0, comma));
                Double w = Double.parseDouble(vertexInfo.substring(comma + 1));

                // Install this information in the graph.
                // System.out.println(
                //   "Edge: (" + u + ", " + v + ") has weight: " + w
                // );
                newEdge(u, v, w);

                // Skip the search for the next vertex past this one.
                vertexDelim = vertexEnd + 1;
            }
        }

        input.close();
    }

    // Simple test program.
    public static void main(String[] Args)
    {
        try {
            SimpleGraph myGraph = new SimpleGraph("testgraph.txt");
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("Can't find graph file: testgraph.txt!");
        }
        catch (java.io.IOException e) {
            System.out.println("Error reading graph file: " + e);
        }
    }

}
