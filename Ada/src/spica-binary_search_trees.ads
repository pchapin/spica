---------------------------------------------------------------------------
-- FILE    : spica-binary_search_trees.ads
-- SUBJECT : Package providing general binary search trees.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Spica.Trees;
private with Ada.Finalization;

generic
   type Element_Type is private;
   with function "<"(Left, Right : Element_Type) return Boolean is <>;
   with package Trees_Package is new Trees(Element_Type);
package Spica.Binary_Search_Trees is

   type Tree is limited new Trees_Package.Tree with private;

   -- Inserts Item into tree T. Duplicate items are not allowed in the tree.
   overriding procedure Insert(T : in out Tree; Item : in Element_Type);

   -- Returns True if Item is in the tree, False otherwise. If found replace Item from tree.
   overriding procedure Lookup(T : in out Tree; Item : in out Element_Type; Result : out Boolean);

   -- Deletes Item from tree T. If Item is not in T, there is no effect.
   overriding procedure Delete(T : in out Tree; Item : in Element_Type);

   -- Returns the root element.
   overriding function Get_Root(T : Tree) return Element_Type;

   -- Returns the number of elements in the heap.
   overriding function Size(T : Tree) return Natural;

   -- For debugging. Raises Inconsistent_Heap if a problem is found.
   overriding procedure Check_Sanity(T : in Tree);

private
   type Tree_Node;
   type Tree_Node_Access is access Tree_Node;
   type Tree_Node is
      record
         Data             : Element_Type;
         Parent           : Tree_Node_Access := null;
         Left_Child       : Tree_Node_Access := null;
         Right_Child      : Tree_Node_Access := null;
      end record;

   type Tree is new Ada.Finalization.Limited_Controlled and Trees_Package.Tree with
      record
         Root  : Tree_Node_Access := null;
         Count : Natural          := 0;
      end record;

   overriding procedure Finalize(T : in out Tree);

end Spica.Binary_Search_Trees;
