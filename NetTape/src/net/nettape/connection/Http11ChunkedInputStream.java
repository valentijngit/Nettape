package net.nettape.connection;


import java.io.IOException;
import java.io.InputStream;

public class Http11ChunkedInputStream extends InputStream {

    private InputStream inputStream;

    private int state;
    private int state2Remaining;

    public Http11ChunkedInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.state = 1;
    }

    public int read() throws IOException {
        if (closed())
            throw new IOException("Stream is closed.");

        try {
            switch (state) {
		// possible problem 'probably not: strange that another skipCRLF is called, while the only way that case could be 3 is through state2remaining = 0 and then a double skipCRLF is already done
                case 3:
                    if (!skipCRLF()) {
                        state = -1;
                        return -1;
                    }

                    state = 1;

                case 1:
                    state2Remaining = readChunkSize();

                    if (state2Remaining <= 0) {
                        state = -1;
                        return -1;
                    }

                    state = 2;

                case 2:
                    int c = inputStream.read();
                    state2Remaining--;

                    if (c == -1) {
                        state = -1;
                        return -1;
                    }

                    if (state2Remaining == 0) {
                        state = 3;
                    }

                    return c;

                default:
                    return -1;
            }
        } catch (IOException e) {
            state = -1;
            throw e;
        }
    }

    private int readChunkSize() throws IOException {
        StringBuilder hexIntBuf = new StringBuilder();

        int c = inputStream.read();
        while (true) {
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')) {
                hexIntBuf.append((char) c);
                if (hexIntBuf.length() > 8)
                    throw new IOException("Invalid chunk size: too long");
                else
                    c = inputStream.read();
            } else if (c == 13) {
                if ((inputStream.read()) == 10)
                    break;
                else
                    throw new IOException("Invalid chunk size: missing closing CRLF");
            } else if (c == -1) {
                return -1;
            } else {
                throw new IOException("Invalid chunk size: unknown char");
            }
        }

        if (hexIntBuf.length() == 0)
            throw new IOException("Invalid chunk size: missing size");

        try {
            int len = Integer.parseInt(hexIntBuf.toString(), 16);

            if (len < 0)
                throw new IOException("Invalid chunk size: must be zero or greater");
            else if (len == 0)
                skipCRLF();

            return len;
        } catch (NumberFormatException e) {
            throw new IOException("Invalid chunk size: unknown char: " + hexIntBuf);
        }
    }

    private boolean skipCRLF() throws IOException {
        int c = inputStream.read();
        if (c == -1)
            return false;
        else if (c != 13)
            throw new IOException("Invalid chunk data: missing closing CRLF");

        c = inputStream.read();
        if (c == -1)
            return false;
        else if (c != 10)
            throw new IOException("Invalid chunk data: missing closing CRLF");

        return true;
    }

    public int available() throws IOException {
        if (closed())
            throw new IOException("Stream is closed.");

        if (state == 2) {
            return Math.min(state2Remaining, inputStream.available());
        } else {
            return 0;
        }
    }

    public void close() throws IOException {
        if (closed())
            return;

        inputStream = null;
    }

    private boolean closed() {
        return inputStream == null;
    }

}
