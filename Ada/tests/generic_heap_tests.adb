---------------------------------------------------------------------------
-- FILE    : generic_heap_tests.adb
-- SUBJECT : Package with subprograms that are useful for testing heaps.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Assertions;

package body Generic_Heap_Tests is
   use Assertions;

   procedure Test_Heap_Constructor is
      H : Heap;
   begin
      H.Check_Sanity;
      Assert(H.Size = 0, "Default heap has non-zero size");
   end Test_Heap_Constructor;


   procedure Test_Heap_Insert is
      H : Heap;

      -- Not sure if these values are particularly interesting, but...
      subtype Data_Index is Positive range 1 .. 16;
      type Test_Array is array(Data_Index) of Integer;
      Test_Data : Test_Array := (10, 9, 11, 8, 12, 7, 13, 6, 14, 5, 16, 4, 18, 3, 18, 2);
      Top_Data  : Test_Array := (10, 9,  9, 8,  8, 7,  7, 6,  6, 5,  5, 4,  4, 3,  3, 2);
   begin
      for I in Data_Index loop
         H.Insert(Test_Data(I));
         H.Check_Sanity;
         Assert(H.Size = I, "Invalid size after insert");
         Assert(H.Top = Top_Data(I), "Invalid top element after insert");
      end loop;
   end Test_Heap_Insert;


   procedure Test_Heap_Delete is
      H : Heap;

      -- Not sure if these values are particularly interesting, but...
      subtype Data_Index is Positive range 1 .. 16;
      type Test_Array is array(Data_Index) of Integer;
      Test_Data : Test_Array := (10, 9, 11, 8, 12, 7, 13, 6, 14, 5, 16, 4, 18, 3, 18, 2);
      Top_Data  : Test_Array := (2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 18, 18);

      Index : Positive := 1;
   begin
      for I in Data_Index loop
         H.Insert(Test_Data(I));
      end loop;

      -- This loop is a little awkward in this case, but checks a common idiom.
      while H.Size > 0 loop
         Assert(H.Top = Top_Data(Index), "Invalid top element before delete");
         Index := Index + 1;
         H.Delete_Top;
         H.Check_Sanity;
      end loop;
   end Test_Heap_Delete;


end Generic_Heap_Tests;