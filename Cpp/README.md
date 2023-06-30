
SpicaCpp
========

This is the C++ part of the Spica meta-library.

The code in this library is intended to be cross platform and to support multiple build tools.
Specifically:

+ The `Makefile` is the main source of authority for building SpicaCpp. However, it assumes
  GNUmake and thus can't (easily) be used in some cases.
  
+ The `.vscode` folder contains setting information for Visual Studio Code that should allow
  VSCode to work properly on all of its supported platforms: Windows, macOS, and Linux.
  
+ There are Eclipse/CDT project files, but they are configured for the Cygwin C++ tool chain and
  thus will only work as-is on Windows platforms with Cygwin installed.
  
+ There is a project file for Visual Studio that can be included into a solution for a larger
  system where SpicaCpp is being used.
  
+ There is a Code::Blocks projet file that can be included into a solution for a larger system
  where Code::Blocks is bieng used.
  
SpicaCpp currently assumes C++ 2011. However, some of the components are inside the subset of
C++ 1998 supported by Open Watcom. Accordingly there is an Open Watcom target definition file
for the Open Watcom IDE, and there are makefiles for various Open Watcom targets under
`owbuild`.

Peter Chapin  
chapin@proton.me  
