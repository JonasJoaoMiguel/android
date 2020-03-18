package com.example.androidserver.websocket;

import android.os.Build;
import android.util.Log;

import com.example.androidserver.TaskHandler;
import com.example.androidserver.task.TerminalPaymentTask;
import com.example.androidserver.websocket.model.WebsocketIn;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class ToUpperWebsocket extends WebSocketServer{

    private TaskHandler taskHandler;

    public ToUpperWebsocket(int port, TaskHandler taskHandler) {
        super(new InetSocketAddress(port));
        this.taskHandler = taskHandler;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            String hostname = getHostname();
            this.onSucess(conn, hostname);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
        }
    }

    @Override
    public void onStart() {

    }

    private void onSucess(WebSocket conn, String hostname) {

        conn.send(hostname);
    }

    private String getHostname() {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            Log.d("teste", "oou: " + getString.invoke(null, "net.hostname").toString());
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
