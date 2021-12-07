/* ****************************************************************************
FILE          : Base64OutputStream.java
PROGRAMMER    : Peter Chapin
LAST REVISION : March 14, 2002

(C) Copyright 2002 by Peter Chapin

This file contains a class that implements base64 encoding. This class is a FilterOutputStream
and can thus be used in a natural way with the other Java I/O classes. For example:

   FileOutputStream myFile = new FileOutputStream("out.txt");
   Base64OutputStream myEncoder = new Base64OutputStream(myFile);

Data written into myEncoder is base64 encoded on its way to the output file.
**************************************************************************** */
package org.pchapin.spica;

public class Base64OutputStream extends java.io.FilterOutputStream {

    private static final String base64Table = new String(
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

    private byte[] input  = new byte[3];
    private byte[] output = new byte[4];
    private int    count;
    private int    line_width = 0;

    //
    // This method is used to reset the byte arrays defined above.
    //
    private void initializeWorkspace()
    {
        for (int i = 0; i < 3; ++i) input[i] = 0;
        for (int i = 0; i < 4; ++i) output[i] = (byte)'=';
        count = 0;
    }

    //
    // This method does the grunt work of encoding. It assumes that the workspaces were
    // initialized and that count elements of input contain characters to encode.
    //
    private void encode()
    {
        int index;

        if (count == 0) return;

        // Handle the first input byte.
        index  = (input[0] & 0xFC) >> 2;
        output[0] = (byte)base64Table.charAt(index);
        index  = (input[0] & 0x03) << 4;
        index |= (input[1] & 0xF0) >> 4;
        output[1] = (byte)base64Table.charAt(index);

        if (count == 1) return;

        // Finish handling the second input byte.
        index  = (input[1] & 0x0F) << 2;
        index |= (input[2] & 0xC0) >> 6;
        output[2] = (byte)base64Table.charAt(index);

        if (count == 2) return;

        // Finish handling the last input byte.
        index = input[2] & 0x3F;
        output[3] = (byte)base64Table.charAt(index);
    }

    //
    // Pass my parameter to the superclass.
    //
    public Base64OutputStream(java.io.OutputStream out)
    {
        super(out);
        initializeWorkspace();
    }

    //
    // This is the only write method I must define. The other methods are implemented in
    // FilterOutputStream in terms of this one. For efficiency it would probably be smart to
    // implement the other methods directly as well. Maybe another time.
    //
    public void write(int b) throws java.io.IOException
    {
        input[count++] = (byte)b;
        if (count == 3) {
            encode();

            // Output this group. Wrap to the next line if necessary.
            out.write(output);
            line_width += 4;
            if (line_width >= 72) {
                out.write(0x0D); out.write(0x0A);
                line_width = 0;
            }

            // Get ready for the next group.
            initializeWorkspace();
        }
    }

    //
    // This method closes the stream by handling the last partial block.
    //
    public void close() throws java.io.IOException
    {
        if (count != 0) {
            encode();
            out.write(output);
        }
        out.write(0x0D); out.write(0x0A);
        out.flush();
        out.close();
    }
}
