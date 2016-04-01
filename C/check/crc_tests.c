/****************************************************************************
FILE          : crctst.c
LAST REVISION : April 1997
SUBJECT       : Program to exercise the CRC checksum.
PROGRAMMER    : Peter Chapin

Please send comments or bug reports to

     Peter Chapin
     P.O. Box 317
     Randolph Center, VT 05061
     pchapin@twilight.vtc.vsc.edu
****************************************************************************/

#include <stdio.h>
#include "channel.h"
#include "crc.h"

unsigned char Raw_Data[1024];

int main(void)
  {
    int i, j;
    unsigned short CRC_Xmit;
    unsigned short CRC_Recv;
    int Detected_Count = 0;
    int Okay_Count     = 0;

    /* Initialize. */
    for (i = 0; i < 1024; i++) {
      Raw_Data[i] = (unsigned char)i;
    }
    CRC_Xmit = CRC_Clear();
    Initialize_Noise(1.0e-5);

    /* Compute the CRC checksum. */
    for (i = 0; i < 1024; i++) {
      CRC_Xmit = CRC_Update(CRC_Xmit, Raw_Data[i]);
    }
    CRC_Xmit = CRC_Finish(CRC_Xmit);

    /* Now loop many times sending the block of data through the channel. */
    for (i = 0; i < 1024; i++) {
      CRC_Recv = CRC_Clear();

      for (j = 0; j < 1024; j++) {
        CRC_Recv = CRC_Update(CRC_Recv, Channel(Raw_Data[j]));
      }
      CRC_Recv = CRC_Update(CRC_Recv, Channel((CRC_Xmit & 0xFF00) >> 8));
      CRC_Recv = CRC_Update(CRC_Recv, Channel(CRC_Xmit & 0x00FF));

      if (CRC_Recv != 0) Detected_Count++;
        else Okay_Count++;
    }

    printf("Blocks with detected errors: %d\n", Detected_Count);
    printf("Blocks okay: %d\n", Okay_Count);

    return 0;
  }
