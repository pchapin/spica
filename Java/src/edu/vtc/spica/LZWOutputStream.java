
package edu.vtc.spica;

import java.io.FilterOutputStream;
import java.io.OutputStream;

/**
 * This class adds compression/decompression services to a FilterOutputStream. It uses the LZW compression algorithm.
 */
// TODO: Implement class LZWOutputStream
class LZWOutputStream extends FilterOutputStream {

    // private HashTable stringTable = new HashTable(2*65536);

    // The constructor just accepts a reference to the enclosed OutputStream and passes it to the FilterOutputStream for
    // later.
    // 
    public LZWOutputStream(OutputStream out)
    {
        super(out);
    }


    // Must override this method from FilterOutputStream. The other forms of write call this one and so will work if
    // this one works even though they might work inefficiently.
    // 
    public void write(int b)
    {
    }


    // Must override this method so that the internal state of the LZW algorithm will be properly flushed to the
    // underlying stream before a close. The close() in FilterOutputStream calls this method so I don't need to override
    // close(). NOTE: an override of close might be a good idea anyway, however, so we can dump the string table when
    // the stream is closed and thus recover memory.
    // 
    public void flush()
    {
    }

}
