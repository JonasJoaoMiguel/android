package com.example.androidserver.server;

import com.example.androidserver.MainActivity;

import org.jboss.com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Controller {

    public void connect(MainActivity context) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/mobile", new MyHttpHandler(context));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
