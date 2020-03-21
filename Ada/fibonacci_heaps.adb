with Ada.Unchecked_Deallocation;

package body Fibonacci_Heaps is

   procedure Deallocate_Node is new
     Ada.Unchecked_Deallocation(Node, Node_Access);


   procedure Initialize(H : in out Heap) is
   begin
      H.Min  := null;
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
      if H.Min /= null then
         Finalize_Children(H.Min);
      end if;
   end Finalize;


   procedure Insert(H : in out Heap; Item : in Key_Type) is
   begin
      raise Program_Error with "Not Implemented";
   end Insert;


   function Minimum(H : Heap) return Key_Access is
      Result : Key_Access;
   begin
      if H.Min = null then
         Result := null;
      else
         Result := H.Min.Key'Access;
      end if;
      return Result;
   end Minimum;


   procedure Delete_Minimum(H : in out Heap) is
   begin
      raise Program_Error with "Not Implemented";
   end Delete_Minimum;


   function Union(H1, H2 : in out Heap) return Heap is
   begin
      raise Program_Error with "Not Implemented";
      return Result : Heap do
         null;
      end return;
   end Union;

end Fibonacci_Heaps;
