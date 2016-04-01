/* *************************************************************************
FILE          : GraphAlgorithms.java
SUBJECT       : Contains a number of generic graph algorithm implementations.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains several static methods for manipulating graphs. These methods can process any
object that implements the Graph interface.
************************************************************************ */
package edu.vtc.spica;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is a wrapper around a number of static methods that provide basic graph
 * algorithms.
 */
public class GraphAlgorithms {

    /**
     * This class embodies the definition of a tree inside a graph.
     */
    public static class GraphTree {
        private double[] distance;
        private int[] predecessor;

        /**
         * Provide the GraphTree with the necessary information. Some other agent is responsible
         * for computing this information.
         *
         * @param d Array of distances.
         * @param p Array of predecessors.
         */
        public GraphTree(double[] d, int[] p)
        {
            distance = d;
            predecessor = p;
        }

        /** Accessor method for getting the distance to a vertex. */
        public double getDistance(int v)
        {
            return distance[v];
        }

        /** Accessor method for getting the predecessor of a vertex. */
        public int getPredecessor(int v)
        {
            return predecessor[v];
        }
    }

    // Some of the algorithms needs these "colors".
    private static final int WHITE = 0;
    private static final int GRAY  = 1;
    private static final int BLACK = 2;


    /**
     * Breadth first search.
     *
     * @param theGraph An object storing a graph.
     * @param start The start vertex.
     * @return The breadth first tree rooted at the specified start vertex.
     */
    public static <W>
    GraphTree BFS(Graph<W> theGraph, int start)
    {
        int size = theGraph.numVertices();

        // Verify that the given start vertex is in bounds.
        if (start < 0 || start >= size)
            throw new IndexOutOfBoundsException();

        // Allocate the necessary arrays.
        double[] distance = new double[size]; // Distance from start.
        int[] predecessor = new int[size];    // Previous vertex toward start.
        int[] color       = new int[size];    // "Color" of vertex.

        // I will use a doubly linked list to implement the necessary Q.
        LinkedList<Integer> Queue  = new LinkedList<Integer>();

        // Initialize the algorithm.
        for (int i = 0; i < size; i++) {
            distance[i]    = Double.POSITIVE_INFINITY;
            predecessor[i] = -1;
            color[i]       = WHITE;
        }
        distance[start] = 0.0;
        color[start]    = GRAY;
        Queue.addLast(start);

        // Keep looping as long as there is a (GRAY) vertex in the queue.
        while (Queue.size() != 0) {
            int u = Queue.removeFirst();

            // Scan down the adjacency list for vertex u.
            Iterator<Graph.EdgeDescriptor<W>> p = theGraph.getAdjacency(u);
            while (p.hasNext()) {
                Graph.EdgeDescriptor<W> theEdge = p.next();
                int v = theEdge.getRemoteVertex();

                // If this vertex has never been seen, compute info about it.
                if (color[v] == WHITE) {
                    distance[v]    = distance[u] + 1.0;
                    predecessor[v] = u;
                    color[v]       = GRAY;

                    // Add the newly grayed vertex to the queue.
                    Queue.addLast(v);
                }
            }
            color[u] = BLACK;
        }

        // Return the interesting stuff to the caller.
        return new GraphTree(distance, predecessor);
    }


    /**
     * Compute the shortest path from a given source vertex to every other vertex in the graph.
     * This method considers the weighting on each edge. This method uses the Bellman-Ford
     * algorithm. It returns null if there is no solution due to a negative weight cycle.
     * 
     * @param theGraph An object storing a graph.
     * @param start The start vertex.
     * @return The single source shortest path tree rooted at the specified start vertex.
     */
    public static <W extends Number>
    GraphTree singleSourceShortestPath(Graph<W> theGraph, int start)
    {
        int size = theGraph.numVertices();

        // Verify that the given start vertex is in bounds.
        if (start < 0 || start >= size)
            throw new IndexOutOfBoundsException();

        // Allocate the necessary storage.
        double[] distance = new double[size];
        int[] predecessor = new int[size];

        // Initialize.
        for (int i = 0; i < size; ++i) {
            distance[i] = Double.POSITIVE_INFINITY;
            predecessor[i] = -1;
        }
        distance[start] = 0;

        // Sweep over the graph size - 1 times.
        for (int i = 0; i < size - 1; ++i) {

            // Process every edge.
            for (int u = 0; u < size; ++u) {
                Iterator<Graph.EdgeDescriptor<W>> p = theGraph.getAdjacency(u);
                while (p.hasNext()) {
                    Graph.EdgeDescriptor<W> theEdge = p.next();
                    int v = theEdge.getRemoteVertex();
                    double w = theEdge.getWeight().doubleValue();

                    // If following this edge from u to v is shorter than our current idea of
                    // the shortest distance to v, update our current idea of that distance!
                    // 
                    if (distance[v] > distance[u] + w) {
                        distance[v] = distance[u] + w;
                        predecessor[v] = u;
                    }
                }
            }
        }

        // Now verify the consistency of the result.
        for (int u = 0; u < size; ++u) {
            Iterator<Graph.EdgeDescriptor<W>> p = theGraph.getAdjacency(u);
            while (p.hasNext()) {
                Graph.EdgeDescriptor<W> theEdge = p.next();
                int v = theEdge.getRemoteVertex();
                double w = theEdge.getWeight().doubleValue();

                // If this condition is true now (after the loop above) then there must be a
                // negative weight cycle in the graph.
                // 
                if (distance[v] > distance[u] + w) return null;
            }
        }

        // The results are correct.
        return new GraphTree(distance, predecessor);
    }


    /**
     * Check if the given graph contains cycles or not.
     *
     * @param theGraph The graph to check.
     * @return True if the graph contains no cycles; false otherwise.
     */
    public static
    boolean isAcyclic(Graph<?> theGraph)
    {
        int   vertexCount = theGraph.numVertices();
        int[] color       = new int[vertexCount];

        for (int i = 0; i < vertexCount; ++i) {
            color[i] = WHITE;
        }
        for (int i = 0; i < vertexCount; ++i) {
            if (color[i] == WHITE) {
                if (!cyclicExplore(theGraph, color, i)) return false;
            }
        }
        return true;
    }

    
    private static <W>
    boolean cyclicExplore(Graph<W> theGraph, int[] color, int u)
    {
        color[u] = GRAY;
        Iterator<Graph.EdgeDescriptor<W>> it = theGraph.getAdjacency(u);
        while (it.hasNext()) {
            Graph.EdgeDescriptor<W> theEdge = it.next();
            int v = theEdge.getRemoteVertex();
            if (color[v] == WHITE) {
                cyclicExplore(theGraph, color, v);
            }
            else if (color[v] == GRAY) return false;
        }
        color[u] = BLACK;
        return true;
    }
}
