
Spica Erlang
============

This directory contains the Erlang portion of the Spica library. At the moment it is very
minimal. The file tree.erl contains the implementation of a simple binary tree module using a
functional style (meaning in sequential Erlang).

It would probably be worthwhile to reimplement tree.erl in a concurrent style. In fact, it may
make sense in the case of Erlang to have both functional and concurrent implementations of all
the basic (and not so basic) data structures. Contrasting the two forms might be quite
interesting and enlightening.

Also EDoc should be investigated. If it works the way I think, it should probably be used on all
of Spica's Erlang modules.
