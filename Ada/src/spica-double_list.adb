---------------------------------------------------------------------------
-- FILE    : spica-double_list.adb
-- SUBJECT : Package providing a bounded doubly linked list.
-- AUTHOR  : (C) Copyright 2017 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <chapinp@acm.org>
---------------------------------------------------------------------------

package body Spica.Double_List
with
  Refined_State => (Internal_List => (Memory, Count, Free_List, Free))
is

   type List_Node is
      record
         Value    : Element_Type;
         Next     : Index_Type;
         Previous : Index_Type;
      end record;

   type Node_Array is array(Index_Type) of List_Node;
   type Free_Array is array(Index_Type) of Index_Type;

   Memory    : Node_Array;  -- Holds the list nodes.
   Count     : Index_Type;  -- Number of items on the list.
   Free_List : Free_Array;  -- Maps available nodes.
   Free      : Index_Type;  -- Points at the head of the free list.

   procedure Clear
     with
       Refined_Global => (Output => (Memory, Count, Free_List, Free)),
       Refined_Depends => ((Memory, Count, Free_List, Free) => null)
   is
   begin
      -- Make sure the entire array has some appropriate initial value.
      Memory := (others => (Default_Element, 0, 0));
      Count := 0;

      -- Prepare the free list.
      Free_List := (others => 0);
      Free := 1;
      for I in Index_Type range 1 .. Index_Type'Last - 1 loop
         Free_List(I) := I + 1;
      end loop;
   end Clear;


   procedure Insert_Before
     (It : in Iterator; Item : in Element_Type; Status : out Status_Type)
     with
       Refined_Global => (Input => Free_List,
                          In_Out => (Memory, Count, Free)),
       Refined_Depends => (Memory =>+ (Count, It, Item, Free),
                           (Count, Status) => Count, Free =>+ (Count, Free_List)),
       Refined_Post =>
         (if Count = Max_Size then Status = Insufficient_Space else Status = Success) and
         Count = (if Count = Max_Size then Count'Old else Count'Old + 1)
   is
      New_Pointer : Index_Type;
   begin
      if Count = Max_Size then
         Status := Insufficient_Space;
         pragma Assert(Status = Insufficient_Space);
      else
         Status := Success;

         -- Get an item from the free list.
         New_Pointer := Free;
         Free        := Free_List(Free);

         -- Fill in the fields and link the new item into the list.
         Memory(New_Pointer) := (Item, It.Pointer, Memory(It.Pointer).Previous);
         Memory(Memory(It.Pointer).Previous).Next := New_Pointer;
         Memory(It.Pointer).Previous := New_Pointer;

         -- Adjust count.
         Count := Count + 1;
         pragma Assert(Status = Success);
      end if;
      pragma Assert(if Count = Max_Size then Status = Insufficient_Space else Status = Success);
   end Insert_Before;


   function Front return Iterator
     with
       Refined_Global => (Input => Memory)
   is
   begin
      return (Pointer => Memory(0).Next);
   end Front;


   function Back return Iterator is
   begin
      return (Pointer => 0);
   end Back;


   procedure Forward(It : in out Iterator; Status : out Status_Type)
     with
       Refined_Global => (Input => Memory),
       Refined_Depends => (It =>+ Memory, Status => It)
   is
   begin
      -- If already off the end we can't go forward.
      if It.Pointer = 0 then
         Status := Invalid_Step;
      else
         Status := Success;
         It.Pointer := Memory(It.Pointer).Next;
      end if;
   end Forward;


   procedure Backward(It : in out Iterator; Status : out Status_Type)
     with
       Refined_Global => (Input => Memory),
       Refined_Depends => (It =>+ Memory, Status => (It, Memory))
   is
   begin
      -- If already at the beginning we can't go backward.
      if It.Pointer = Memory(0).Next then
         Status := Invalid_Step;
      else
         Status := Success;
         It.Pointer := Memory(It.Pointer).Previous;
      end if;
   end Backward;


   function Is_Dereferencable(It : Iterator) return Boolean is
   begin
      return It.Pointer /= 0;
   end Is_Dereferencable;


   procedure Get_Value(It : Iterator; Item : out Element_Type; Status : out Status_Type)
     with
       Refined_Global => (Input => Memory),
       Refined_Depends => (Item => (It, Memory), Status => It)
   is
   begin
      if It.Pointer = 0 then
         Status := Bad_Iterator;
         Item := Default_Element;
      else
         Status := Success;
         Item := Memory(It.Pointer).Value;
      end if;
   end Get_Value;


   procedure Put_Value(It : Iterator; Item : in Element_Type; Status : out Status_Type)
     with
       Refined_Global => (In_Out => Memory),
       Refined_Depends => (Memory =>+ (It, Item), Status => It)
   is
   begin
      if It.Pointer = 0 then
         Status := Bad_Iterator;
      else
         Status := Success;
         Memory(It.Pointer).Value := Item;
      end if;
   end Put_Value;


   function Size return Natural
     with
       Refined_Global => (Input => Count),
       Refined_Post => Size'Result = Count
   is
   begin
      return Count;
   end Size;

end Spica.Double_List;
