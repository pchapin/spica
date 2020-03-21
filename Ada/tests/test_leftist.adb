with Ada.Text_IO; use Ada.Text_IO;

with Spica.Heaps;
with Spica.Leftist_Heaps;
with Generic_Heap_Tests;

package body Test_Leftist is

   package IHeaps is new Spica.Heaps(Integer);
   package ILeftist_Heaps is new Spica.Leftist_Heaps(Integer, "<", IHeaps);

   package Heap_Tests is new Generic_Heap_Tests(IHeaps, ILeftist_Heaps.Heap);
   use Heap_Tests;

   --  Run the tests.
   procedure Execute is
   begin
      Put_Line("   Constructor"); Test_Heap_Constructor;
      Put_Line("   Insert");      Test_Heap_Insert;
      Put_Line("   Delete");      Test_Heap_Delete;
   end Execute;

end Test_Leftist;
