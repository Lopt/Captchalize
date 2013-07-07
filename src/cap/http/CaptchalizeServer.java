package cap.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchalizeServer {
    private HttpServer server = null;

    public static CaptchalizeServer create(final InetSocketAddress socketAddress, int maxConnections) {
        CaptchalizeServer server = null;

        try {
            server = new CaptchalizeServer(socketAddress, maxConnections);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

        server.createContext("/", new FormHandler());

        return server;
    }

    public CaptchalizeServer(final InetSocketAddress socketAddress, int maxConnections) throws IOException {
        this.server = HttpServer.create(socketAddress, maxConnections);
        this.server.setExecutor(null);
    }

    public void start() {
        this.server.start();
    }

    public InetSocketAddress getAddress() {
        return this.server.getAddress();
    }

    public HttpContext createContext(final String s, final HttpHandler httpHandler) {
        return server.createContext(s, httpHandler);
    }
}
