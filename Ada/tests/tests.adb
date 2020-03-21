with Ada.Text_IO;
with Test_Arrays;
with Test_Binary_Search_Trees;
with Test_Double_List;
with Test_Leftist_Heaps;
with Test_RedBlack_Trees;
with Test_Skip_Lists;
with Test_Splay_Trees;
with Test_Sorters;
with Test_Very_Longs;

procedure Tests is
begin
   -- Sorting...
   Test_Sorters.Test_Insertion_Sort;
   Test_Sorters.Test_Merge_Sort;

   -- Arrays...
   Test_Arrays.Test_Maximum_Subarray;

   -- Number Theory...
   Test_Very_Longs;

   -- Lists...
   Test_Double_List;
   Test_Skip_Lists;

   -- Heaps...
   Test_Leftist_Heaps;

   -- Trees...
   Test_Binary_Search_Trees;
   Test_Splay_Trees;
   Test_RedBlack_Trees;
end Tests;
