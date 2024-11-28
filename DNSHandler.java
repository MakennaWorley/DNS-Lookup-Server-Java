import java.io.*;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DNSHandler {
    public static final int BUFFER_SIZE = 256;

    public void process(Socket client) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream fromClient = null;
        OutputStream toClient = null;

        int numBytes;
        String searchAddress;
        InetAddress address;
        String ip;

        try {
            fromClient = new BufferedInputStream(client.getInputStream());
            toClient = new BufferedOutputStream(client.getOutputStream());

            numBytes = fromClient.read(buffer);

            searchAddress = new String(buffer, 0, numBytes).trim();

            try {
                address = InetAddress.getByName(searchAddress);
                ip = address.getHostAddress();

                toClient.write(ip.getBytes());
            }
            catch (UnknownHostException uhe) {
                String error = "Unknown Host";
                toClient.write(error.getBytes());
            }

            toClient.flush();
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
        finally {
            if (fromClient != null)
                fromClient.close();
            if (toClient != null)
                toClient.close();
        }
    }
}
