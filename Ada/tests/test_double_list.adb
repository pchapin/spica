
pragma SPARK_Mode(On);

with Assertions;

package body Test_Double_List is
   use Assertions;

   -- Run the tests.
   procedure Execute is

      subtype Test_Index_Type is Integer range 1 .. 10;
      Test_Sequence : array(Test_Index_Type) of Integer :=
        (10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

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
