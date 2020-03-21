
pragma SPARK_Mode(On);

with CIS5050.Double_List;

package Test_Double_List is

   package Integer_Double_List is
     new CIS5050.Double_List(Element_Type => Integer, Max_Size => 16, Default_Element => 0);
   use Integer_Double_List;


   procedure Execute
     with
       Global => (Output => Integer_Double_List.Internal_List),
       Depends => (Integer_Double_List.Internal_List => null);

end Test_Double_List;
