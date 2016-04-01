/* *************************************************************************
FILE          : Graph.java
SUBJECT       : General purpose graph interface.
LAST REVISION : 2009-01-04
AUTHOR        : (C) Copyright 2009 by Peter C. Chapin

This file contains the definition of a generic graph interface.
************************************************************************* */

package edu.vtc.spica;

import java.util.Iterator;

/**
 * General purpose graph interface. This interface requires that vertices be numbered
 * sequentially from 0 to N-1 without any missing values. This requirement allows the methods
 * that work with a graph to store vertex information in a simple array. If vertices are to be
 * labeled in some more elaborate way, that must be done outside of this interface. Note that
 * this interface allows for multi-graphs.
 *
 * This interface is intended to mimic the organization of Java's standard collection classes.
 *
 * @param <W> The type used for edge weights in the graph.
 */
public interface Graph<W> {

    /**
     * Information about a graph edge. This class contains the information that describes an
     * edge in a vertex's adjacency list.
     *
     * @param <W> The type used for edge weights in the graph.
     */
    public static class EdgeDescriptor<W> {
        /** The vertex number of the "other" vertex. */
        private int remoteVertex;

        /** The weight of this edge. */
        private W weight;

        /**
         * The constructor initializes the fields.
         * 
         * @param r The vertex number of the remote vertex.
         * @param w The weight of this edge.
         */
        public EdgeDescriptor(int r, W w)
        {
            remoteVertex = r;
            weight = w;
        }

        /** Returns the remote vertex number associated with this edge. */
        public int getRemoteVertex() { return remoteVertex; }

        /** Returns the weight associated with this edge. */
        public W getWeight() { return weight; }

        /**
         * Compares two edges.
         *
         * @param otherEdge The other edge to which we are being compared.
         * @return True if the two edges have the same remote vertex and the same weight.
         */
        public boolean equals(EdgeDescriptor<W> otherEdge)
        {
            if (remoteVertex == otherEdge.remoteVertex &&
                weight.equals(otherEdge.weight)) return true;
            return false;
        }
    }


    /** Returns the number of vertices in this graph. Runs in O(1) time. */
    public int numVertices();

    /**
     * Returns an object that allows you to iterate over the edges of a given vertex. Each call
     * of the iterator's next() method returns an object that can be cast into an EdgeDescriptor
     * containing the vertex number of an adjacent vertex and the weight of the corresponding
     * edge. It is permissible to have several adjacency list scans open at once. This method
     * runs in O(1) time. The iterator's hasNext() and next() methods also run in O(1) time.
     *
     * @param u The vertex for which you want the adjacency list.
     */
    Iterator<EdgeDescriptor<W>> getAdjacency(int u);
}
