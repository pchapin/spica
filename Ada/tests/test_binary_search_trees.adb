---------------------------------------------------------------------------
-- FILE    : test_binary_search_trees.adb
-- SUBJECT : Package that encapsulates the binary search tree tests.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Ada.Text_IO; use Ada.Text_IO;

with Spica.Trees;
with Spica.Binary_Search_Trees;
with Generic_Tree_Tests;

package body Test_Binary_Search_Trees is

   package ITrees is new Spica.Trees(Integer);
   package IBinary_Search_Trees is new Spica.Binary_Search_Trees(Integer, "<", ITrees);

   package Tree_Tests is new Generic_Tree_Tests(ITrees, IBinary_Search_Trees.Tree);
   use Tree_Tests;

   -- Run the tests.
   procedure Execute is
   begin
      Put_Line("   Constructor"); Test_Tree_Constructor;
      Put_Line("   Insert");      Test_Tree_Insert;
      Put_Line("   Lookup");      Test_Tree_Lookup;
      Put_Line("   Delete");      Test_Tree_Delete;
   end Execute;

end Test_Binary_Search_Trees;
