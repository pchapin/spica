#!/usr/bin/env python3
###########################################################################
# FILE          : graph_search.py
# LAST REVISION : 2008-11-04
# SUBJECT       : Two classic graph search algorithms.
# PROGRAMMER    : (C) Copyright 2008 by Peter C. Chapin
#
# This file contains implementations of the classic graph searching algorithms.
#
# Please send comments or bug reports to
#
#      Peter C. Chapin
#      Computer Information Systems Department
#      Vermont Technical College
#      Randolph Center, VT 05061
#      Peter.Chapin@vtc.vsc.edu
###########################################################################

import graph

def BFS(G, start):
    """Breadth first search of a graph.

    This function does a breadth first search of a directed graph. It returns a pair (parent,
    distance) of two lists indexed by vertex number. The value parent[i] is the parent of vertex
    i in the breadth first tree. The value distance[i] is the distance to vertex i from the
    start vertex, measured in the number of edges crossed. If parent[i] is the string "NULL"
    then the vertex has no parent. If a vertex is unreachable, its distance will be the string
    "INF."

    G    : A graph as defined by the graph module.
    start: The start vertex number."""

    # Prepare color, distance, and parent arrays.
    color    = ["WHITE"] * G.num_verticies()
    distance = ["INF"  ] * G.num_verticies()
    parent   = ["NULL" ] * G.num_verticies()
    
    # Initialize.
    color[start] = "GRAY"
    distance[start] = 0
    Q = []
    Q.append(start)

    # Main loop.    
    while len(Q) != 0:
        u = Q[0]
        del Q[0]
        for edge in G.get_adjacency(u):
            v = edge[0]
            if color[v] == "WHITE":
                color[v] = "GRAY"
                distance[v] = distance[u] + 1
                parent[v] = u
                Q.append(v)
        color[u] = "BLACK"
    return (parent, distance)


def DFS_visit(G, u, color, parent, discovery, finishing, time, has_cycle):
    color[u] = "GRAY"
    time = time + 1
    discovery[u] = time
    for edge in G.get_adjacency(u):
        v = edge[0]
        if color[v] == "GRAY":
            has_cycle = True
        if color[v] == "WHITE":
            parent[v] = u
            (time, has_cycle) = \
                DFS_visit(G, v, color, parent, discovery, finishing, time, has_cycle)
    color[u] = "BLACK"
    time = time + 1
    finishing[u] = time
    return (time, has_cycle)

def DFS(G):
    """Depth first search of a graph.

    This function does a depth first search of a directed, weighted graph. Unlike BFS, this
    algorithm picks an arbitrary start vertex and runs until all verticies have been processed.
    It thus returns a "depth first forest." Specifically it returns a tuple (parent, discovery,
    finishing, has_cycle) where parent[i] is the parent of vertex i in the depth first tree,
    discovery[i] is the step when vertex i is discovered, finishing[i] is the step when vertex i
    is completely processed, and has_cycle is True if the graph contains a cycle. If parent[i]
    is the string "NULL" then the vertex has no parent.

    G: The graph to search."""

    color     = ["WHITE"] * G.num_verticies()
    parent    = ["NULL" ] * G.num_verticies()
    discovery = ["INF"  ] * G.num_verticies()
    finishing = ["INF"  ] * G.num_verticies()
    time      = 0
    has_cycle = False
    for u in range(0, G.num_verticies()):
        if color[u] == "WHITE":
            (time, has_cycle) = \
                DFS_visit(G, u, color, parent, discovery, finishing, time, has_cycle)
    return (parent, discovery, finishing, has_cycle)


def strongly_connected(G):
    """Returns true if graph is strongly connected.
    
    A graph is strongly connected if there is a path from every vertex to every other vertex.
    This function also returns true if G is empty or if G contains a single vertex.
    
    G: Graph to analyze."""
    
    if G.num_verticies() == 0: return True
    (pf, df) = BFS(G, 0)
    rG = graph.reverse_graph(G)
    (pr, dr) = BFS(rG, 0)
    
    # Make sure every vertex was found in both BFS operations.
    for index in range(0, G.num_verticies()):
        if df[index] == "INF": return False
        if dr[index] == "INF": return False
    return True


if __name__ == "__main__":
    G = graph.Graph();
    G.create_edge(0, 1, 0)
    G.create_edge(0, 3, 0)
    G.create_edge(1, 4, 0)
    G.create_edge(2, 5, 0)
    G.create_edge(3, 1, 0)
    G.create_edge(3, 2, 0)
    G.create_edge(3, 4, 0)
    G.create_edge(4, 5, 0)
    # G.create_edge(5, 1, 0)
    
    # The results should be automatically checked.
    print("\nDoing a BFS...")
    (p, d) = BFS(G, 0)
    print("parents = ", p)
    print("distances = ", d)
    
    # The results should be automatically checked.
    print("\nDoing a DFS...")
    (p, d, f, c) = DFS(G)
    print("parents   = ", p)
    print("discovery = ", d)
    print("finishing = ", f)
    print("has_cycle = ", c)
    
    # Test strongly_connected
    G_empty = graph.Graph()
    if not strongly_connected(G_empty):
        print("FAIL: G_empty not reported as strongly connected!")
        
    G_single = graph.Graph()
    G_single.create_vertex(0)
    if not strongly_connected(G_single):
        print("FAIL: G_single not reported as strongly connected!")

    G_connected = graph.Graph()    
    G_connected.create_edge(0, 1, 0)
    G_connected.create_edge(1, 2, 0)
    G_connected.create_edge(2, 0, 0)
    if not strongly_connected(G_connected):
        print("FAIL: G_connected not reported as strongly connected!")
        
    G_notconnected = graph.Graph()
    G_notconnected.create_edge(0, 1, 0)
    G_notconnected.create_edge(1, 2, 0)
    if strongly_connected(G_notconnected):
        print("FAIL: G_notconnected reported as strongly connected!")
