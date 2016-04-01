#!/usr/bin/env python3
###########################################################################
# FILE          : graph.py
# LAST REVISION : 2008-11-04
# SUBJECT       : Class that defines a graph type.
# PROGRAMMER    : (C) Copyright 2008 by Peter C. Chapin
#
# Please send comments or bug reports to
#
#      Peter C. Chapin
#      Computer Information Systems Department
#      Vermont Technical College
#      Randolph Center, VT 05061
#      Peter.Chapin@vtc.vsc.du
###########################################################################

class Graph:
    """Representation of a Graph.
    
    Graphs are stored in adjacency list format. Verticies are numbered from 0 to N-1 (where N is
    the number of verticies). All values in the range 0 .. N-1 must correspond to a vertex;
    there are no "holes" in the vertex numbering.
    
    Edges are represented by (remote_vertex, weight) pairs. The "local" vertex is implicitly
    defined by the adjacency list the edge is on. All graphs are directed. If an undirected
    graph is needed add two edges, one in each direction. Algorithms that use this
    representation must take this into consideration.
    
    Edge weights are required. If weights are not needed by the application, a weight value of
    zero (or some other suitable null value) should be used."""

    def __init__(self):
        """Intializes a graph to an empty collection."""
        
        self.adjacency = []
    
    def num_verticies(self):
        """Returns N, the number of verticies in the graph."""
        
        return len(self.adjacency)
    
    def num_edges(self):
        """Returns the number of edges in the graph."""
        
        edge_count = 0
        for vertex in self.adjacency:
            edge_count += len(vertex)
        return edge_count
    
    def create_vertex(self, vertex_number):
        """Adds a vertex to the graph.
        
        vertex_number: The vertex to add. If the vertex already exists there is no effect. If
        vertex_number >= N, the missing verticies in the range N .. vertex_number are created as
        well. Note that the first vertex is vertex number zero."""
    
        if vertex_number < len(self.adjacency): return
        for i in range(len(self.adjacency), vertex_number + 1):
            self.adjacency.append([])
    
    def create_edge(self, vertex_1, vertex_2, weight):
        """Adds a weighted, directed edge to the graph.
        
        If the edge already exists, it is added a second time (thus creating a multigraph). This
        should be fixed.
        
        vertex_1: The source vertex. Created by calling create_vertex. vertex_2: The destination
        vertex. Created by calling create_vertex. weight : The weight on the edge."""
    
        self.create_vertex(vertex_1)
        self.create_vertex(vertex_2)
        self.adjacency[vertex_1].append( (vertex_2, weight) )
    
    def get_adjacency(self, vertex_number):
        """Returns an adjacency list for a particular vertex.
        
        Returns a list of edge information tuples of the form (remote_vertex, weight). Each
        tuple in the list is for an edge that flows away from vertex_number. This list might be
        empty.
            
        vertex_number: The vertex of interest."""
   
        return self.adjacency[vertex_number]

    
def reverse_graph(G):
    """Reverses the edges in a graph.
    
    Returns a new graph with the same verticies but with all the edges in the opposite
    direction. The edge weights are unchanged.

    G: The graph to be reversed."""

    rG = Graph();
    for vertex in range(0, G.num_verticies()):
        rG.create_vertex(vertex)
        for edge in G.get_adjacency(vertex):
            rG.create_edge(edge[0], vertex, edge[1])
    return rG

    
if __name__ == "__main__":
    G = Graph()
    G.create_edge(0, 1, 1.00)
    G.create_edge(1, 2, 2.00)
    G.create_edge(2, 3, 3.00)
    G.create_edge(0, 2, 1.50)
    G.create_edge(1, 3, 2.50)
    G.create_edge(3, 0, 4.00)
    G.create_edge(1, 1, 3.00)
    G.create_edge(4, 1, 1.50)
    G.create_vertex(5)
    
    if G.num_verticies() != 6:
        print("FAIL: G reports wrong number of verticies!")
    if G.num_edges() != 8:
        print("FAIL: G reports wrong number of edges!")
    
    print("\nSample graph...")
    # The results should be automatically checked.
    for vertex in range(0, G.num_verticies()):
        for edge in G.get_adjacency(vertex):
            print("%d -> %d with weight %f" % (vertex, edge[0], edge[1]))
            
    rG = reverse_graph(G)
    if rG.num_verticies() != 6:
        print("FAIL: rG reports wrong number of verticies!")
    if rG.num_edges() != 8:
        print("FAIL: rG reports wrong number of edges!")
        
    print("\nSample graph reversed...")
    # The results should be automatically checked.
    for vertex in range(0, rG.num_verticies()):
        for edge in rG.get_adjacency(vertex):
            print("%d -> %d with weight %f" % (vertex, edge[0], edge[1]))
