---------------------------------------------------------------------------
-- FILE    : test_double_list.ads
-- SUBJECT : Package that encapsulates the double list tests.
-- AUTHOR  : (C) Copyright 2017 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <chapinp@acm.org>
---------------------------------------------------------------------------
pragma SPARK_Mode(On);

with Spica.Double_List;

package Test_Double_List is

   package Integer_Double_List is
     new Spica.Double_List(Element_Type => Integer, Max_Size => 16, Default_Element => 0);
   use Integer_Double_List;


   procedure Execute
     with
       Global => (Output => Integer_Double_List.Internal_List),
       Depends => (Integer_Double_List.Internal_List => null);

end Test_Double_List;
