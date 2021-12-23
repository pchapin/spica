############################################################################
# FILE   : makefile
# AUTHOR : Peter C. Chapin <peter@pchapin.org>
#
# This is the makefile for the SpicaCpp project.
############################################################################

!include master.mif

.cpp:     ..\..
.cpp.obj: .AUTODEPEND
    wpp386 $(cpp_options) -DSCR_DIRECT /bt=os2 $[*.cpp
