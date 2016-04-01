/* *************************************************************************
FILE          : AbstractGraph.java
SUBJECT       : Contains a base implementation of the Graph interface.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains a base class from which other graph classes can be defined. It provides
a rudimentary graph implementation from which more elaborate graphs can be built.
************************************************************************* */
package edu.vtc.spica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class implementing the Graph interface in the most basic way possible. A "real" graph class
 * would subclass this implementation and provide application specific material. This
 * implementation stores the graph in adjacency list format, and is not synchronized.
 *
 * @param <W> The type used for edge weights in the graph.
 */
public class AbstractGraph<W> implements Graph<W> {

    /** Internal object to hold the adjacency list. */
    protected ArrayList<ArrayList<EdgeDescriptor<W>>> vertexList =
        new ArrayList<ArrayList<EdgeDescriptor<W>>>();

    
    /**
     * Adds a new vertex. If the vertex already exists, this method has no effect. It will
     * create any intermediate vertices as necessary.
     *
     * @param u The vertex number to add.
     */
    public void newVertex(int u)
    {
        // If this vertex already exists, we are done.
        if (u < vertexList.size()) return;

        // Otherwise create the new vertex and all intermediate vertices (if necessary). This
        // assumes that the vertices of a graph are numbered contiguously.
        // 
        for (int i = u - vertexList.size() + 1; i > 0; i--) {
            vertexList.add(new ArrayList<EdgeDescriptor<W>>());
        }
    }

    
    /**
     * Adds a new, directed edge. This method makes sure that both vertices at either end exist
     * ahead of time. If the edge is intended to be undirected, you must explicitly add two
     * edges with one in each direction. There is no prohibition against adding multiple edges
     * connecting the same two vertices (for example, perhaps using different weights).
     *
     * @param u The source vertex for the edge. It need not exist in the graph ahead of time.
     * @param v The destination vertex for the edge. It need not exist in the graph ahead of time.
     * @param w The weight for the edge.
     */
    public void newEdge(int u, int v, W w)
    {
        // Create the vertices if they don't already exist.
        newVertex(u);
        newVertex(v);

        // Add v to the adjacency list of u if it isn't already.
        ArrayList<EdgeDescriptor<W>> adjacency = vertexList.get(u);
        Graph.EdgeDescriptor<W> theEdge = new Graph.EdgeDescriptor<W>(v, w);
        int edgeIndex = adjacency.indexOf(theEdge);
        if (edgeIndex == -1) adjacency.add(theEdge);
    }


    // ----------------------------------------------------------
    // The following methods are required for the Graph interface
    // ----------------------------------------------------------

    public int numVertices()
    {
        return vertexList.size();
    }

    
    public Iterator<EdgeDescriptor<W>> getAdjacency(int u)
    {
        ArrayList<EdgeDescriptor<W>> adjacencyList = vertexList.get(u);
        return adjacencyList.iterator();
    }

}
