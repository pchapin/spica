private with Ada.Finalization;

generic
   type Key_Type is private;
   with function "<"(L, R : Key_Type) return Boolean is <>;
package Fibonacci_Heaps is

   type Heap is tagged limited private;
   type Key_Access is access all Key_Type;

   ----------------------------
   -- Mergeable Heap Operations
   ----------------------------

   -- Inserts item into the given heap.
   procedure Insert(H : in out Heap; Item : in Key_Type);

   -- Returns a pointer to the minimum key in the heap.
   function Minimum(H : Heap) return Key_Access;

   -- Removes the minimum key from the heap.
   procedure Delete_Minimum(H : in out Heap);

   -- Returns a new heap that contains the merger of H1 and H2. The heaps H1 and H2 are
   -- emptied by this operation, however they can continue to be used.
   function Union(H1, H2 : in out Heap) return Heap;

private

   type Node;
   type Node_Access is access Node;

   type Heap is new Ada.Finalization.Limited_Controlled with
      record
         Min  : Node_Access;
         Size : Natural;
      end record;

   overriding procedure Initialize(H : in out Heap);
   overriding procedure Finalize(H : in out Heap);

   type Node is
      record
         Key    : aliased Key_Type;
         Parent : Node_Access := null;
         Left_Sibling  : Node_Access := null;
         Right_Sibling : Node_Access := null;
         Child  : Node_Access := null;
      end record;

end Fibonacci_Heaps;
