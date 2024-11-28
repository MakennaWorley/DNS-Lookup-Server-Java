import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

public class DNSClient {
    public static final int DEFAULT_PORT = 6052;

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("usage: java DNSClient <DNS server> <IP name to be resolved>");
            System.exit(0);
        }

        BufferedReader fromServer = null;
        BufferedWriter toServer = null;
        Socket server = null;

        try {
            server = new Socket(args[0], DEFAULT_PORT);

            toServer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));

            toServer.write(args[1]); //sending server the IP name
            toServer.newLine();
            toServer.flush();

            fromServer = new BufferedReader(new InputStreamReader(server.getInputStream())); //reading from server

            String line;

            while ( (line = fromServer.readLine()) != null)
                System.out.println(line);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        finally {
            if (fromServer!= null)
                fromServer.close();
            if (server != null)
                server.close();
        }
    }

}
