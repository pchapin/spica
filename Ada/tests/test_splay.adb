---------------------------------------------------------------------------
-- FILE    : test_splay.adb
-- SUBJECT : Package that encapsulates the splay tree tests.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Ada.Text_IO; use Ada.Text_IO;

with Spica.Splay_Trees;
with Assertions;

package body Test_Splay is
   use Assertions;

   package Integer_Splay_Trees is new Spica.Splay_Trees(Integer, "<");
   use Integer_Splay_Trees;


   procedure Test_Splay_Constructor is
      T : Tree;
   begin
      Check_Consistency(T);
   end Test_Splay_Constructor;


   -- This test only exercises 5 of 6 possible reconfigurations. Also not all
   -- possible arrangements of non-null subtrees is checked.
   --
   procedure Test_Splay_Insert is
      T : Tree;
      subtype Test_Index is Natural range 0..6;
      Values : array(Test_Index) of Integer := (5, 3, 7, 4, 6, 2, 8);

      Dump_Results : array(Test_Index) of Dump_Type(Test_Index) :=
        (
         ((5, 1), others => (0, 0)),
         ((3, 1), (5, 2), others => (0, 0)),
         ((3, 3), (5, 2), (7, 1), others => (0, 0)),
         ((3, 2), (4, 1), (5, 3), (7, 2), others => (0, 0)),
         ((3, 3), (4, 2), (5, 3), (6, 1), (7, 2), others => (0, 0)),
         ((2, 1), (3, 3), (4, 4), (5, 5), (6, 2), (7, 3), others => (0, 0)),
         ((2, 2), (3, 5), (4, 6), (5, 7), (6, 4), (7, 3), (8, 1))
        );

   begin
      for I in Test_Index loop
         Insert(T, Values(I));
         Check_Consistency(T);

         -- Verify the tree's structure to make sure splaying is right.
         declare
            Result : Dump_Type := Dump(T);
         begin
            for J in Result'Range loop
               Assert(Dump_Results(I)(J) = Result(J), "bad structure");
            end loop;
         end;
      end loop;
   end Test_Splay_Insert;


   -- Verify that each lookup operation reconfigures the tree properly.
   procedure Test_Splay_Lookup is
      T : Tree;

      Values : array(0..7) of Integer := (4, 6, 3, 1, 4, 2, 8, 5);
      subtype Test_Index is Natural range 1..3;
      Search : array(Test_Index) of Integer := (3, 3, 6);

      Result : Integer;

      Dump_Results : array(Test_Index) of Dump_Type(0..6) :=
        (
         ((1, 3), (2, 2), (3, 1), (4, 3), (5, 2), (6, 4), (8, 3)),
         ((1, 3), (2, 2), (3, 1), (4, 3), (5, 2), (6, 4), (8, 3)),
         ((1, 4), (2, 3), (3, 2), (4, 4), (5, 3), (6, 1), (8, 2))
        );

   begin
      -- Populate tree.
      for I in Values'Range loop
         Insert(T, Values(I));
         Check_Consistency(T);
      end loop;

      -- Look for things.
      for I in Test_Index loop
         Lookup(T, Search(I), Result);
         Assert(Search(I) = Result, "found wrong value");
         Check_Consistency(T);

         -- Verify the tree's structure to make sure splaying is right.
         declare
            Result : Dump_Type := Dump(T);
         begin
            for J in Result'Range loop
               Assert(Dump_Results(I)(J) = Result(J), "bad structure");
            end loop;
         end;

      end loop;

      -- Verify that Not_Found is raised when appropriate.
      begin
         Lookup(T, 0, Result);
         Assert(1 = 0, "Not_Found not raised");
      exception
         when Not_Found =>
            Check_Consistency(T);
      end;

   end Test_Splay_Lookup;


   -- Run the tests.
   procedure Execute is
   begin
      Put_Line("   Constructor"); Test_Splay_Constructor;
      Put_Line("   Insert");      Test_Splay_Insert;
      Put_Line("   Lookup");      Test_Splay_Lookup;
   end Execute;

end Test_Splay;
