package server;

import org.jboss.com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Controller {

    public void conect() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHttpHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
