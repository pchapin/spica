%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FILE      : tree.erl
% SUBJECT   : Simple binary tree data structure in a functional style.
% PROGRAMMER: (C) Copyright 2007 by Peter C. Chapin
%
% Please send comments or bug reports to
%
%      Peter C. Chapin
%      Computer Information Systems
%      Vermont Technical College
%      Williston, VT 05495
%      Peter.Chapin@vtc.vsc.edu
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-module(tree).
-export([make/1, insert/2, lookup/2, delete/2]).

% Tree constructor.
make(Compare) -> {leaf, Compare}.

% Insert Item into a tree.
insert(Item, leaf, _) -> {vertex, Item, leaf, leaf}; 
insert(Item, {vertex, Existing, Left, Right}, Compare) ->
    if
        Item =:= Existing -> {vertex, Existing, Left, Right};
        true ->
          case Compare(Item, Existing) of
              true  -> {vertex, Existing, insert(Item, Left, Compare), Right};
              false -> {vertex, Existing, Left, insert(Item, Right, Compare)}
          end
    end.
    
insert(Item, {Subtree, Compare}) -> {insert(Item, Subtree, Compare), Compare}.
    

% Lookup Item in a tree. Return true if present; false otherwise.
lookup(_, {leaf, _}) -> false;
lookup(Item, {{vertex, Existing, Left, Right}, Compare}) ->
    if
        Item =:= Existing -> true;
        true ->
            case Compare(Item, Existing) of
                true  -> lookup(Item, {Left, Compare});
                false -> lookup(Item, {Right, Compare})
            end
    end.

    
% Find the minimum vertex of the subtree rooted at the given vertex.
find_minimum({vertex, Value, Left, Right}) ->
    if
        Left =:= leaf -> {vertex, Value, Left, Right};
        true          -> find_minimum(Left)
    end.
    
    
% Delete Item from the tree. Return the modified tree.
delete(_, leaf, _) -> leaf;
delete(Item, {vertex, Existing, Left, Right}, Compare) ->
    if
        Item =:= Existing ->
            case {Left, Right} of
                {leaf, leaf}     -> leaf;
                {leaf, RSubTree} -> RSubTree;
                {LSubTree, leaf} -> LSubTree;
                {LSubTree, RSubTree} ->
                    {vertex, Successor, _, _} = find_minimum(RSubTree),
                    {vertex, Successor, LSubTree, delete(Successor, RSubTree, Compare)}
            end;
            
        true ->
            case Compare(Item, Existing) of
                true  -> {vertex, Existing, delete(Item, Left, Compare), Right};
                false -> {vertex, Existing, Left, delete(Item, Right, Compare)}
            end
    end.

delete(Item, {Subtree, Compare}) -> {delete(Item, Subtree, Compare), Compare}.

