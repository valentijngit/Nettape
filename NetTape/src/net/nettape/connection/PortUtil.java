package net.nettape.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PortUtil {

    private static final String PROTOCOL_TOKEN = "Full Duplex Http";

    public static void writeProtocol(OutputStream outputStream, int version) throws IOException {
        String token = PROTOCOL_TOKEN + " " + version + (char) 0;
        outputStream.write(token.getBytes());
    }

    public static int readProtocol(InputStream inputStream) throws IOException {
        StringBuilder buf = new StringBuilder();

        int c = inputStream.read();
        while (true) {
            if (c == -1)
                throw new IOException("Input stream is closed unexpectedly");

            if (c == 0)
                break;

            buf.append((char) c);

            if (buf.length() > 32)
                throw new IOException("Invalid protocol: token is too long: " + buf);

            c = inputStream.read();
        }

        String token = buf.toString();
        if (!token.startsWith(PROTOCOL_TOKEN))
            throw new IOException("Invalid protocol: unknown token: " + token);

        try {
            return Integer.parseInt(token.substring(PROTOCOL_TOKEN.length()).trim());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid protocol: unknown token: " + token);
        }
    }

    public static String readHttpHeader(InputStream in) throws IOException {
        StringBuilder buf = new StringBuilder();

        int b = in.read();
        while (true) {
            if (b == -1)
                throw new IOException("Unexpected EOF");

            if (b == 13) {
                if (in.read() != 10)
                    throw new IOException("Missing CRLF");
                else
                    break;
            }

            buf.append((char) b);
            b = in.read();

            if (buf.length() > 128)
                throw new IOException("HTTP header is too long: " + buf);
        }

        return buf.toString();
    }

}
