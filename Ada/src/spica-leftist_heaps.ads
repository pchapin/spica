---------------------------------------------------------------------------
-- FILE    : spica-leftist_heaps.ads
-- SUBJECT : Generic package providing leftist heaps
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Spica.Heaps;
private with Ada.Finalization;

generic
   type Element_Type is private;
   with function "<"(Left, Right : Element_Type) return Boolean is <>;
   with package Heaps_Package is new Heaps(Element_Type);
package Spica.Leftist_Heaps is

   type Heap is limited new Heaps_Package.Heap with private;

   -- Inserts Item into heap H. Duplicate items are allowed. O(log(N))
   overriding procedure Insert(H : in out Heap; Item : in Element_Type);

   -- Merges heap Source into Destination. The source heap is emptied. O(log(N))
   overriding procedure Merge(Destination : in out Heap; Source : in out Heap);

   -- Removes the "top" element from the heap. O(log(N))
   overriding procedure Delete_Top(H : in out Heap);

   -- Returns the "top" (highest priority) heap element. O(1)
   overriding function Top(H : Heap) return Element_Type;

   -- Returns the number of elements in the heap. O(1)
   overriding function Size(H : Heap) return Natural;

   -- Raises Heaps_Package.Inconsistent_Heap if the heap is in an invalid state.
   overriding procedure Check_Sanity(H : in Heap);

private
   type Heap_Node;
   type Heap_Node_Access is access Heap_Node;
   type Heap_Node is
      record
         Data             : Element_Type;
         Count            : Natural := 1;
         Null_Path_Length : Natural := 0;
         Left_Child       : Heap_Node_Access := null;
         Right_Child      : Heap_Node_Access := null;
      end record;

   type Heap is new Ada.Finalization.Limited_Controlled and Heaps_Package.Heap with
      record
         Root : Heap_Node_Access;
      end record;

   overriding procedure Finalize(H : in out Heap);

end Spica.Leftist_Heaps;
