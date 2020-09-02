with Ada.Unchecked_Deallocation;

package body Spica.Fibonacci_Heaps is

   procedure Deallocate_Node is new
     Ada.Unchecked_Deallocation(Node, Node_Access);


   procedure Initialize(H : in out Heap) is
   begin
      H.Top  := null;
      H.Size := 0;
   end Initialize;


   procedure Finalize(H : in out Heap) is

      procedure Finalize_Children(Node : in not null Node_Access) is
         Current : Node_Access := Node;
         Temp    : Node_Access;
      begin
         loop
            if Current.Child /= null then
               Finalize_Children(Current.Child);
            end if;
            Temp := Current.Right_Sibling;
            Deallocate_Node(Current);
            Current := Temp;
            exit when Current = Node;
         end loop;
      end Finalize_Children;

   begin
      if H.Top /= null then
         Finalize_Children(H.Top);
      end if;
   end Finalize;


   procedure Insert(H : in out Heap; Item : in Key_Type) is
   begin
      raise Program_Error with "Not Implemented";
   end Insert;


   function Top_Priority(H : Heap) return Key_Type is
   begin
      return H.Top.Key;
   end Top_Priority;


   procedure Delete_Top_Priority(H : in out Heap) is
   begin
      raise Program_Error with "Not Implemented";
   end Delete_Top_Priority;


   procedure Union(Destination : in out Heap; Source : in out Heap) is
   begin
      raise Program_Error with "Not Implemented";
   end Union;


   function Size(H : Heap) return Natural is
   begin
      return H.Size;
   end Size;


   procedure Check_Sanity(H : in Heap; Message : in String) is
   begin
      raise Program_Error with "Not Implemented";
   end Check_Sanity;


   procedure Insert(H : in out Heap; Item : in Key_Type; Result_Node : out Node_Handle) is
   begin
      raise Program_Error with "Not Implemented";
   end Insert;


   procedure Raise_Key_Priority
     (H : in out Heap; Existing_Node : in Node_Handle; New_Item : in Key_Type) is
   begin
      raise Program_Error with "Not Implemented";
   end Raise_Key_Priority;


   procedure Delete(H : in out Heap; Existing_Node : in out Node_Handle) is
   begin
      raise Program_Error with "Not Implemented";
   end Delete;


   function Get_Key(Existing_Node : Node_Handle) return Key_Type is
   begin
      return Existing_Node.Key;
   end Get_Key;

end Spica.Fibonacci_Heaps;
