package com.example.androidserver.server;

import com.example.androidserver.MainActivity;
import com.example.androidserver.mapper.PlugPaymentMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.com.sun.net.httpserver.HttpExchange;
import org.jboss.com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedHashMap;

import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;

public class MyHttpHandler implements HttpHandler {

    private static MainActivity context;

    public MyHttpHandler(MainActivity context) {
        MyHttpHandler.context = context;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if(httpExchange.getRequestMethod().equals("GET")) {
            byte[] response = "Welcome Real's HowTo test page".getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response);
            os.close();
        }else if (httpExchange.getRequestMethod().equals("POST")) {

            InputStream is = httpExchange.getRequestBody();

            String s = this.getString(is);

            JsonNode plug = new ObjectMapper().readTree(s);

            Object plugPagPaymentData = new ObjectMapper().treeToValue(plug, Object.class);

            PlugPagPaymentData plugPag = PlugPaymentMapper.mapFrom((LinkedHashMap) plugPagPaymentData);

            this.executePost(plugPag);

            byte[] response = "Testando método POST".getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response);
            os.close();
        }

    }

    private void executePost(PlugPagPaymentData plugData) {

        this.context.runOnUiThread(() -> {
            MyHttpHandler.context.startTerminalCreditPayment(plugData);
        });

    }

    private String getString(InputStream is) {
        if(is != null) {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line = "";
            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
        else {
            return "";
        }
    }


}
