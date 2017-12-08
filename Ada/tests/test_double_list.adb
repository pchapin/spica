---------------------------------------------------------------------------
-- FILE    : test_double_list.adb
-- SUBJECT : Package that encapsulates the double_list tests.
-- AUTHOR  : (C) Copyright 2017 by Peter C. Chapin
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


   procedure Test_Double_List_Clear is
   begin
      Integer_Double_List.Clear;
   end Test_Double_List_Clear;


   -- Run the tests.
   procedure Execute is
   begin
      Put_Line("   Clear"); Test_Double_List_Clear;
   end Execute;

end Test_Double_List;
