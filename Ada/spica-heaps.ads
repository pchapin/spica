
generic
   type Element_Type is private;
package Spica.Heaps is

   type Heap is limited interface;

   -- Inserts Item into heap H. Duplicate items are allowed.
   procedure Insert(H : in out Heap; Item : in Element_Type) is abstract;

   -- Merges Source into Destination. Source is emptied.
   procedure Merge(Destination : in out Heap; Source : in out Heap) is abstract;

   -- Removes the "top" element from the heap. Raises Constraint_Error if no such element.
   procedure Delete_Top(H : in out Heap) is abstract;

   -- Returns the "top" element of the heap. Raises Constraint_Error if no such element.
   function Top(H : Heap) return Element_Type is abstract;

   -- Returns the number of elements in the heap.
   function Size(H : Heap) return Natural is abstract;

   --------------------
   -- Debugging/Testing
   --------------------

   -- Exception raised by Check_Santity (below).
   Inconsistent_Heap : exception;

   -- For debugging. Raises Inconsistent_Heap if a problem is found.
   procedure Check_Sanity(H : in Heap) is abstract;

end Spica.Heaps;
