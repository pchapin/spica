
Spica TODO List
===============

+ Clean up the C++ check program so that the tests are fully automated.

+ Add the C check program to the Visual Studio solution. Note that right now the C check program
  uses the CUnit test framework. Some serious thought should be given to dropping that. Since
  Spica is intended to be cross platform a better cross platform solution should be used.
  Question: Can CUnit be used on Windows without too much pain?
  
+ Does it make sense, for Spica, to break the .gitignore and .gitattributes up across the
  different subproject? Each programming language has it's own "ignore needs", and trying to
  create a single .gitignore for all of them creates a lot of coupling between the subprojects.
