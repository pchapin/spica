---------------------------------------------------------------------------
-- FILE    : spica-trees.ads
-- SUBJECT : Generic package providing an interface to trees of various types.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------

generic
   type Element_Type is private;
package Spica.Trees is

   type Tree is limited interface;

   -- Inserts Item into tree T. Duplicate items overwrite existing tree elements.
   procedure Insert(T : in out Tree; Item : in Element_Type) is abstract;

   -- Returns True if Item is in the tree, False otherwise. If found replace Item from tree.
   procedure Lookup(T : in out Tree; Item : in out Element_Type; Result : out Boolean) is abstract;

   -- Deletes Item from tree T. If Item is not in T, there is no effect.
   procedure Delete(T : in out Tree; Item : in Element_Type) is abstract;

   -- Returns the root element. Raises Constraint_Error if there is no such element.
   function Get_Root(T : Tree) return Element_Type is abstract;

   -- Returns the number of elements in the tree.
   function Size(T : Tree) return Natural is abstract;

   --------------------
   -- Debugging/Testing
   --------------------

   -- Exception raised by Check_Santity (below).
   Inconsistent_Tree : exception;

   -- For debugging. Raises Inconsistent_Tree if a problem is found.
   procedure Check_Sanity(T : in Tree) is abstract;

end Spica.Trees;
