with Ada.Text_IO;
with Test_Arrays;
with Test_Binary_Search_Trees;
with Test_RedBlack_Trees;
with Test_Skip_Lists;
with Test_Splay_Trees;
with Test_Sorters;

procedure Main is
begin
   Ada.Text_IO.Put_Line("No news is good news!");

   -- Introductory code samples.
   Test_Sorters.Test_Insertion_Sort;
   Test_Sorters.Test_Merge_Sort;

   -- Homework #1
   Test_Arrays.Test_Maximum_Subarray;

   -- Homework #2
   Test_Skip_Lists;

   Test_Binary_Search_Trees;
   Test_Splay_Trees;

   -- Homework #3
   Test_RedBlack_Trees;
end Main;
