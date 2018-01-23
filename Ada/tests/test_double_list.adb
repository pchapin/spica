---------------------------------------------------------------------------
-- FILE    : test_double_list.adb
-- SUBJECT : Package that encapsulates the double_list tests.
-- AUTHOR  : (C) Copyright 2018 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <chapinp@acm.org>
---------------------------------------------------------------------------
pragma SPARK_Mode(On);

with Ada.Text_IO; use Ada.Text_IO;

with Spica.Double_List;
with Assertions;

package body Test_Double_List is
   use Assertions;

   package Integer_Double_List is
     new Spica.Double_List(Element_Type => Integer, Max_Size => 16, Default_Element => 0);
   use Integer_Double_List;

   subtype Test_Index_Type is Integer range 1 .. 10;
   Test_Sequence : array(Test_Index_Type) of Integer :=
     (10, 20, 30, 40, 50, 60, 70, 80, 90, 100);


   -- Run the tests.
   procedure Execute is
      It     : Integer_Double_List.Iterator;
      Status : Integer_Double_List.Status_Type;
      Index  : Test_Index_Type;
      Item   : Integer;
   begin
      Clear;

      It := Front;
      for I in Test_Sequence'Range loop
         Insert_Before(It, Test_Sequence(I), Status);
         Assert(Status = Success, "Bad Status during Insert_Before");
      end loop;

      It := Front;
      Index := Test_Sequence'First;
      while Is_Dereferencable(It) loop
         Get_Value(It, Item);
         Assert(Item = Test_Sequence(Index), "Bad Item from Get_Value");
         Forward(It);
         if Index < Test_Index_Type'Last then
            Index := Index + 1;
         end if;
      end loop;

   end Execute;

end Test_Double_List;
