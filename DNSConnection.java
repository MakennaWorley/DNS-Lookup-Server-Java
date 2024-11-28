import java.net.Socket;

public class DNSConnection implements Runnable {
    private Socket client;
    private static DNSHandler handler = new DNSHandler();

    public DNSConnection(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            handler.process(client);
        }
        catch (java.io.IOException ioe) {
            System.err.println(ioe);
        }
    }
}
