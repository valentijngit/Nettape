package net.nettape.connection;


import java.io.IOException;
import java.io.OutputStream;

public class Http11ChunkedOutputStream extends OutputStream {

    static private final byte[] CRLF = new byte[] { (byte) 13, (byte) 10 };

    private OutputStream outputStream;

    private byte[] cache;
    private int cachePos;

    public Http11ChunkedOutputStream(OutputStream outputStream) {
        this(outputStream, 2048);
    }

    public Http11ChunkedOutputStream(OutputStream outputStream, int bufferSize) {
        this.outputStream = outputStream;
        this.cache = new byte[bufferSize];
        this.cachePos = 0;
    }

    public void write(int b) throws IOException {
        if (closed())
            throw new IOException("Stream is closed.");

	    // is cache[0] ever filled?
	    
        cache[cachePos++] = (byte) b;

        if (cachePos == cache.length)
            flush();
    }

    public void flush() throws IOException {
        if (closed())
            throw new IOException("Stream is closed.");

        if (cachePos > 0) {
            outputStream.write(Integer.toHexString(cachePos).getBytes());
            outputStream.write(CRLF);

            outputStream.write(cache, 0, cachePos);
            outputStream.write(CRLF);

            outputStream.flush();
            cachePos = 0;
        }
    }

    public void close() throws IOException {
        if (closed())
            return;

        try {
            flush();

            outputStream.write('0');
            outputStream.write(CRLF);
            outputStream.write(CRLF);

            outputStream.flush();
        } finally {
            outputStream = null;
            cache = null;
        }
    }

    private boolean closed() {
        return outputStream == null;
    }

}
