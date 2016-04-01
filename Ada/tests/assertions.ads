---------------------------------------------------------------------------
-- FILE    : assertions.ads
-- SUBJECT : Assertion support subprograms.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------

package Assertions is
   procedure Assert(Condition : Boolean; Message : String);
end Assertions;
