---------------------------------------------------------------------------
-- FILE    : generic_heap_tests.ads
-- SUBJECT : Package with subprograms that are useful for testing heaps.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Spica.Heaps;

generic
   with package Heap_Package is new Spica.Heaps(Element_Type => Integer);
   type Heap is limited new Heap_Package.Heap with private;
package Generic_Heap_Tests is

   procedure Test_Heap_Constructor;
   procedure Test_Heap_Insert;
   procedure Test_Heap_Delete;

end Generic_Heap_Tests;
