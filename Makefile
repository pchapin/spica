#
# This is the Makefile for the Spica project.
#

all:	C/SpicaC.bc C++/SpicaCpp.bc

C/SpicaC.bc:
	(cd C; make)

C++/SpicaCpp.bc:
	(cd Cpp; make)
