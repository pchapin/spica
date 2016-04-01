---------------------------------------------------------------------------
-- FILE    : generic_tree_tests.ads
-- SUBJECT : Package with subprograms that are useful for testing trees.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Spica.Trees;

generic
   with package Tree_Package is new Spica.Trees(Element_Type => Integer);
   type Tree is limited new Tree_Package.Tree with private;
package Generic_Tree_Tests is

   procedure Test_Tree_Constructor;
   procedure Test_Tree_Insert;
   procedure Test_Tree_Lookup;
   procedure Test_Tree_Delete;

end Generic_Tree_Tests;
