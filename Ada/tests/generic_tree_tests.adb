---------------------------------------------------------------------------
-- FILE    : generic_tree_tests.adb
-- SUBJECT : Package with subprograms that are useful for testing trees.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Assertions;

package body Generic_Tree_Tests is
   use Assertions;

   subtype Test_Index is Positive range 1 .. 16;
   type Test_Array is array(Test_Index) of Integer;


   function Make_Test_Tree(Test_Data : Test_Array) return Tree is
   begin
      return T : Tree do
         for I in Test_Index loop
            T.Insert(Test_Data(I));
            T.Check_Sanity;
            Assert(T.Size = I, "Invalid size after insert");
         end loop;
      end return;
   end Make_Test_Tree;


   procedure Test_Tree_Constructor is
      T : Tree;
   begin
      T.Check_Sanity;
      Assert(T.Size = 0, "Default tree has non-zero size");
   end Test_Tree_Constructor;


   procedure Test_Tree_Insert is
      Test_Data : Test_Array := (10, 5, 15, 8, 12, 7, 13, 6, 14, 9, 11, 4, 16, 3, 17, 2);
      T         : Tree := Make_Test_Tree(Test_Data);
   begin
      -- Verify that inserting a duplicate item does not cause a problem.
      T.Insert(9);
      Assert(T.Size = Test_Data'Length, "Insert of duplicate causes invalid size");
      T.Check_Sanity;
   end Test_Tree_Insert;

   procedure Test_Tree_Lookup is
      Test_Data : Test_Array := (10, 5, 15, 8, 12, 7, 13, 6, 14, 9, 11, 4, 16, 3, 17, 2);
      T         : Tree := Make_Test_Tree(Test_Data);
      Found     : Boolean;
      Good_Item : Integer;
      Bad_Item  : Integer := -1;
   begin
      for I in Test_Data'Range loop
         Good_Item := Test_Data(I);
         T.Lookup(Good_Item, Found);
         Assert(Found, "Lookup of existing item failed");
         Assert(Good_Item = Test_Data(I), "Successful lookup changed search item equivalency");
         T.Check_Sanity;
      end loop;
      T.Lookup(Bad_Item, Found);
      Assert(not Found, "Lookup of non-existing item succeeded");
      Assert(Bad_Item = -1, "Failed lookup modified search item");
   end Test_Tree_Lookup;


   procedure Test_Tree_Delete is
      Test_Data : Test_Array := (10, 5, 15, 8, 12, 7, 13, 6, 14, 9, 11, 4, 16, 3, 17, 2);
      T         : Tree := Make_Test_Tree(Test_Data);
      Index     : Positive := 1;
   begin
      -- This loop is a little awkward in this case, but checks a common idiom.
      while T.Size > 0 loop
         T.Delete(Test_Data(Index));
         Index := Index + 1;
         T.Check_Sanity;
      end loop;
   end Test_Tree_Delete;


end Generic_Tree_Tests;
