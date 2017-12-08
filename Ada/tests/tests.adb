---------------------------------------------------------------------------
-- FILE    : tests.adb
-- SUBJECT : Main procedure for the Allegra test program.
-- AUTHOR  : (C) Copyright 2017 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <chapinp@acm.org>
---------------------------------------------------------------------------
with Ada.Text_IO; use Ada.Text_IO;

-- List testing packages.
with Test_Double_List;

-- Tree testing packages.
with Test_Binary_Search_Trees;
with Test_Splay;

-- Heap testing packages.
with Test_Leftist;

-- Various other testing packages.
with Test_Very_Longs;

procedure Tests is
begin
   New_Line; Put_Line("Lists: Double Linked");        Test_Double_List.Execute;
   New_Line; Put_Line("Trees: Basic Binary Search");  Test_Binary_Search_Trees.Execute;
   New_Line; Put_Line("Trees: Splay");                Test_Splay.Execute;
   New_Line; Put_Line("Heaps: Leftist");              Test_Leftist.Execute;
   New_Line; Put_Line("Various: Very Long Integers"); Test_Very_Longs.Execute;
end Tests;
