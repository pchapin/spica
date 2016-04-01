---------------------------------------------------------------------------
-- FILE    : spica-splay_trees.ads
-- SUBJECT : Generic package providing splay trees.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------

with Ada.Finalization;

generic
   type Item_Type is private;
   with function "<"(L : Item_Type; R : Item_Type) return Boolean;
package Spica.Splay_Trees is

   type Tree is new Ada.Finalization.Limited_Controlled with private;

   -- Raised if Lookup can't find the item requested.
   Not_Found : exception;

   -- Inserts the given item into the given tree. Duplicates are ignored.
   procedure Insert(T : in out Tree; Item : in Item_Type);

   -- Returns a copy of the Item as it exists in the tree or raises Not_Found.
   procedure Lookup
     (T : in out Tree; Item : in Item_Type; Result : out Item_Type);

   --------------------------------
   -- The following are for testing
   --------------------------------

   -- Raised if Check_Consistency finds a problem.
   Inconsistent : exception;

   -- Check T's invariants.
   procedure Check_Consistency(T : in Tree);

   -- The return type of Dump is an array of Dump_Items.
   type Dump_Item is record
      Item  : Item_Type;
      Level : Natural;
   end record;
   type Dump_Type is array(Natural range <>) of Dump_Item;

   -- Return an array of tree items with structural information.
   function Dump(T : in Tree) return Dump_Type;

private
   type Node;
   type Node_Access is access Node;
   type Node is record
      Parent : Node_Access := null;
      Left   : Node_Access := null;
      Right  : Node_Access := null;
      Item   : Item_Type;
   end record;

   type Tree is new Ada.Finalization.Limited_Controlled with record
      Root  : Node_Access := null;
      Count : Natural     := 0;
   end record;

   -- Removes all the nodes.
   procedure Finalize(T : in out Tree);

end Spica.Splay_Trees;
