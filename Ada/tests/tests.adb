---------------------------------------------------------------------------
-- FILE    : tests.adb
-- SUBJECT : Main procedure for the Allegra test program.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Ada.Text_IO; use Ada.Text_IO;

-- Tree testing packages.
with Test_Binary_Search_Trees;
with Test_Splay;

-- Heap testing packages.
with Test_Leftist;

-- Various other testing packages.
with Test_Very_Longs;

procedure Tests is
begin
   -- Test tests.
   New_Line; Put_Line("Trees: Basic Binary Search"); Test_Binary_Search_Trees.Execute;
   New_Line; Put_Line("Trees: Splay");               Test_Splay.Execute;

   -- Heap tests.
   New_Line; Put_Line("Heaps: Leftist"); Test_Leftist.Execute;

   -- Various other tests.
   New_Line; Put_Line("Various: Very Long Integers"); Test_Very_Longs.Execute;
end Tests;

