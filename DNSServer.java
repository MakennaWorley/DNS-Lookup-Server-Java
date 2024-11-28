import java.io.IOException;

import java.net.ServerSocket;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DNSServer {
    public static final int DEFAULT_PORT = 6052;
    private static final Executor exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket sock = null;

        try {
            sock = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Runnable task = new DNSConnection(sock.accept());
                exec.execute(task);
            }
        }
        catch (IOException ioe) { System.err.println(ioe); }
        finally {
            if (sock != null)
                sock.close();
        }
    }
}
