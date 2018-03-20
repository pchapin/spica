#
# Makefile for the SpicaC unit test program.
#

CC=gcc
CFLAGS=-c -I..
LD=gcc
LDFLAGS=-lpthread -lcunit -lncurses -lgcov
SOURCES=main.c string_tests.c thread_pool_tests.c tree_tests.c
OBJECTS=$(SOURCES:.c=.o)
EXECUTABLE=CheckC

%.o:	%.c
	$(CC) $(CFLAGS) $< -o $@

$(EXECUTABLE):	$(OBJECTS)
	$(LD) $(OBJECTS) ../libSpicaC.a $(LDFLAGS) -o $@

# File Dependencies
###################

main.o:			main.c

string_tests.o:		string_tests.c string_tests.h ../str.h

thread_pool_tests.o:	thread_pool_tests.c thread_pool_tests.h ../ThreadPool.h

tree_tests.o:		tree_tests.c tree_tests.h ../Tree.h


# Additional Rules
##################
clean:
	rm -f *.o *.bc *.s *.ll *~ $(EXECUTABLE)
