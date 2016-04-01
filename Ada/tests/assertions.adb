---------------------------------------------------------------------------
-- FILE    : assertions.adb
-- SUBJECT : Assertion support subprograms.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Ada.Text_IO;

package body Assertions is

   procedure Assert(Condition : Boolean; Message : String) is
   begin
      if not Condition then
         Ada.Text_IO.Put_Line("      ASSERTION FAILED: " & Message);
      end if;
   end Assert;

end Assertions;
