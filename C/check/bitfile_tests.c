/****************************************************************************
FILE         : testbitfile.cpp
LAST REVISED : March 1998
AUTHOR       : Peter Chapin

This file exercises the Bit_File abstract data type.

Please send comments or bug reports to

     Peter Chapin
     P.O. Box 317
     Randolph Center, VT 05061
     pchapin@twilight.vtc.vsc.edu
****************************************************************************/

#include "environ.h"
#include <stdio.h>
#include "bitfile.h"

#define Clear_Screen() printf("\033[2J")

#define FALSE 0
#define TRUE  1

void Wait(void)
  {
    printf("ENTER to continue...");
    while (getchar() != '\n') /* Null */ ;
    printf("\n");
  }

void Test_Open(void)
  {
    FILE     *Raw;
    Bit_File  Input;
    Bit_File  Output;
    int       Result;
    int       Ch;

    Clear_Screen();
    printf("Ready to test Open_Bit() and Close_Bit()... "); Wait();

    printf("Attempting to open a non-existant file for reading: ");
    Result = Open_Bit(&Input, "IN.BIT", BIT_IN);
    printf("Returned %d: %s\n", Result, (Result == FALSE) ? "Ok" : "FAILED");

    printf("Attempting to close the above file: ");
    Result = Close_Bit(&Input);
    printf("Returned %d: %s\n", Result, (Result == FALSE) ? "Ok" : "FAILED");

    printf("Attempting to open a non-existant file for writing: ");
    Result = Open_Bit(&Output, "OUT.BIT", BIT_OUT);
    printf("Returned %d: %s\n", Result, (Result == TRUE) ? "Ok" : "FAILED");

    printf("Attempting to close the above file: ");
    Result = Close_Bit(&Output);
    printf("Returned %d: %s\n", Result, (Result == TRUE) ? "Ok" : "FAILED");

    if ((Raw = fopen("OUT.BIT", "rb")) == NULL) {
      printf("FAILED: The output file does not exist!\n");
    }
    else {
      printf("Ok: The output file exists.\n");
      if ((Ch = getc(Raw)) != EOF)
        printf("FAILED: The output file is not empty! It contains: 0x%02X\n", Ch);
      else
        printf("Ok: The output file is empty.\n");
      fclose(Raw);
    }
    remove("OUT.BIT");

    Wait();
  }

void Test_Get(void)
  {
    FILE     *Raw;
    Bit_File  Data;
    int       A_Bit;

    Clear_Screen();
    printf("Ready to test Get_Bit()... "); Wait();

    Raw = fopen("IN.BIT", "wb");
    putc(0x33, Raw); putc(0xCC, Raw);
    fclose(Raw);

    Open_Bit(&Data, "IN.BIT", BIT_IN);
    printf("Reading a file containing one 0x33 byte and one 0xCC byte:\n");
    while ((A_Bit = Get_Bit(&Data)) != EOF) {
      printf("%d ", A_Bit);
    }
    printf("EOF\n");

    printf("Attempting to read the file again: ");
    A_Bit = Get_Bit(&Data);
    printf("Result %d: %s\n", A_Bit, (A_Bit == EOF) ? "Ok" : "FAILED");

    Close_Bit(&Data);

    remove("IN.BIT");

    Wait();
  }

void Test_Put(void)
  {
    FILE      *Raw;
    int        Ch;
    Bit_File   Data;
    static int The_Bits[] = { 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1 };
           int Count;

    Clear_Screen();
    printf("Ready to test Put_Bit()... "); Wait();

    Open_Bit(&Data, "OUT.BIT", BIT_OUT);
    printf("Writing the sequence: 1 1 0 0 1 0 1 0, 1 1 1 1\n");
    for (Count = 0; Count < 12; Count++) {
      printf("%s ", (Put_Bit(&Data, The_Bits[Count]) == TRUE) ? "Ok" : "FAILED");
    }
    Close_Bit(&Data);
    printf("\n");

    Raw = fopen("OUT.BIT", "rb");
    printf("The file contains:\n");
    while ((Ch = getc(Raw)) != EOF) {
      printf("Wrote: 0x%02X\n", Ch);
    }
    fclose(Raw);

    remove("OUT.BIT");

    Wait();
  }

void Test_Read(void)
  {
    FILE    *Raw;
    int      Array[2];
    Bit_File Data;
    int      Result;

    Clear_Screen();
    printf("Ready to test Read_BitHeader()... "); Wait();

    Raw = fopen("IN.BIT", "wb");
    putc(0x12, Raw); putc(0x34, Raw), putc(0x56, Raw), putc(0x78, Raw); putc(0x9A, Raw);
    fclose(Raw);

    Open_Bit(&Data, "IN.BIT", BIT_IN);
    printf("Reading a file containing 0x3412, 0x7856, and 0x9A\n");
    Result = Read_BitHeader(&Data, Array, sizeof(Array));
    printf("0x%04X, 0x%04X; Result %d: %s\n", Array[0], Array[1],
      Result, (Result == 4) ? "Ok" : "FAILED");

    printf("Using Get_Bit() to get the remaining bits\n");
    while ((Result = Get_Bit(&Data)) != EOF) {
      printf("%d ", Result);
    }
    printf("\n");

    printf("Attempting to read the file again with Read_BitHeader(): ");
    Result = Read_BitHeader(&Data, Array, sizeof(Array));
    printf("Result %d: %s\n", Result, (Result == 0) ? "Ok" : "FAILED");

    printf("Attempting to read the file again with Get_Bit(): ");
    Result = Get_Bit(&Data);
    printf("Result %d: %s\n", Result, (Result == EOF) ? "Ok" : "FAILED");

    Close_Bit(&Data);

    remove("IN.BIT");

    Wait();
  }

void Test_Write(void)
  {
    FILE      *Raw;
    int        Ch;
    Bit_File   Data;
    static int Array[] = { 0x1234, 0x5678 };
    int        Result;

    Clear_Screen();
    printf("Ready to test Write_BitHeader()... "); Wait();

    Open_Bit(&Data, "OUT.BIT", BIT_OUT);
    printf("Writing a file containing 0x1234 and 0x5678\n");
    Result = Write_BitHeader(&Data, Array, sizeof(Array));
    printf("Result %d: %s\n", Result, (Result == 4) ? "Ok" : "FAILED");
    printf("Writing 0, 0, 1, 1 into the file with Put_Bit(): ");
    if (Put_Bit(&Data, 0) == FALSE ||
        Put_Bit(&Data, 0) == FALSE ||
        Put_Bit(&Data, 1) == FALSE ||
        Put_Bit(&Data, 1) == FALSE) { printf("FAILED\n"); }
    else { printf("Ok\n"); }
    Close_Bit(&Data);

    Raw = fopen("OUT.BIT", "rb");
    printf("The file contains:\n");
    while ((Ch = getc(Raw)) != EOF) {
      printf("0x%02X\n", Ch);
    }
    fclose(Raw);

    remove("OUT.BIT");

    Wait();
  }

int bitfile_tests(void)
  {
    Test_Open();
    Test_Get();
    Test_Put();
    Test_Read();
    Test_Write();
    return 0;
  }

