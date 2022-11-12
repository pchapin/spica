#
# Makefile for the Spica C++ unit library test program.
#

CXX=g++
CXXFLAGS=-std=c++11 -Wall -I.. -c -O
LINKFLAGS=
SOURCES=u_tests.cpp        \
	BinomialHeap_tests.cpp \
	BoundedList_tests.cpp  \
	Graph_tests.cpp        \
	sort_tests.cpp         \
	str_tests.cpp          \
	Timer_tests.cpp
OBJECTS=$(SOURCES:.cpp=.o)
LIBRARY=libSpicaCpp.a
EXECUTABLE=u_tests

%.o:	%.cpp
	$(CXX) $(CXXFLAGS) $< -o $@
	
all:	$(EXECUTABLE)

$(EXECUTABLE):	$(OBJECTS) ../$(LIBRARY)
	$(CXX) $(LINKFLAGS) -o $(EXECUTABLE) $(OBJECTS) -L.. -lSpicaCpp

# File Dependencies
###################

u_tests.o:				u_tests.cpp u_tests.hpp ../UnitTestManager.hpp

BinomialHeap_tests.o:	BinomialHeap_tests.cpp ../BinomialHeap.hpp

BoundedList_tests.o:	BoundedList_tests.cpp ../BoundedList.hpp

Graph_tests.o:			Graph_tests.cpp ../Graph.hpp

sort_tests.o:			sort_tests.cpp ../sorters.hpp

str_tests.o:			str_tests.cpp ../str.hpp

Timer_tests.o:			Timer_tests.cpp ../Timer.hpp

# Additional Rules
##################
clean:
	rm -f *.bc *.o $(EXECUTABLE) *.s *.ll *~
