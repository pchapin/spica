#
# Makefile for the Spica C library.
#

CC=gcc
CCFLAGS=-Wall -c -O
check: CCFLAGS=-Wall -c -fprofile-arcs -ftest-coverage
LINK=ar
LINKFLAGS=rc
SOURCES=hash.c        \
	Interval.c    \
	str.c         \
	ThreadPool.c  \
	Timer.c       \
	Tree.c        \
	Vector3.c
OBJECTS=$(SOURCES:.c=.o)
LIBRARY=libSpicaC.a

%.o:	%.c
	$(CC) $(CCFLAGS) $< -o $@

$(LIBRARY):	$(OBJECTS)
	$(LINK) $(LINKFLAGS) -o $@ $(OBJECTS)

check:	$(LIBRARY)

# File Dependencies
###################

# Module dependencies -- Produced with 'depend' on Sun Jul 14 15:55:05 2013


hash.o:	hash.c hash.h 

Interval.o:	Interval.c Interval.h 

str.o:		str.c str.h 

ThreadPool.o:	ThreadPool.c ThreadPool.h 

Timer.o:	Timer.c Timer.h environ.h 

Tree.o:	Tree.c Tree.h 

Vector3.o:	Vector3.c Vector3.h 


# Additional Rules
##################
clean:
	rm -f *.bc *.o $(LIBRARY) *.s *.ll *.gcov *.gcda *.gcno *~
