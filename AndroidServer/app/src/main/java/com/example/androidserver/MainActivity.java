package com.example.androidserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jboss.com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import server.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller controller = new Controller();
        try {
            controller.conect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
