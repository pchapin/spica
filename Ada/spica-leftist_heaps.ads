
with Spica.Heaps;
private with Ada.Finalization;

generic
   type Key_Type is private;
   with package Heaps_Package is new Heaps(Key_Type);
   with function "<"(Left, Right : Key_Type) return Boolean is <>;
package Spica.Leftist_Heaps is

   type Heap is limited new Heaps_Package.Heap with private;

   -- Inserts Item into heap H. Duplicate items are allowed. O(log(N))
   overriding
   procedure Insert(H : in out Heap; Item : in Key_Type);

   -- Returns the highest priority key in the heap. O(1)
   overriding
   function Top_Priority(H : Heap) return Key_Type;

   -- Removes the highest priority key from the heap. O(log(N))
   overriding
   procedure Delete_Top_Priority(H : in out Heap);

   -- Merges heap Source into Destination. The source heap is emptied. O(log(N))
   overriding
   procedure Union(Destination : in out Heap; Source : in out Heap);

   -- Returns the number of elements in the heap. O(1)
   overriding
   function Size(H : Heap) return Natural;

   -- Raises Heaps_Package.Inconsistent_Heap if the heap is in an invalid state.
   overriding
   procedure Check_Sanity(H : in Heap; Message : in String);

private
   type Heap_Node;
   type Heap_Node_Access is access Heap_Node;
   type Heap_Node is
      record
         Data             : Key_Type;
         Count            : Natural := 1;
         Null_Path_Length : Natural := 0;
         Left_Child       : Heap_Node_Access := null;
         Right_Child      : Heap_Node_Access := null;
      end record;

   type Heap is limited new Ada.Finalization.Limited_Controlled and Heaps_Package.Heap with
      record
         Root : Heap_Node_Access;
      end record;

   overriding procedure Finalize(H : in out Heap);

end Spica.Leftist_Heaps;
