package net.diegolemos.codeelevator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyHttpServer
{
    private static final int PORT = 9000;
    private static HttpServer server;

    void run() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server. createContext("/test", new TestHttpHandler());
            server. createContext("/call", new CallHttpHandler());

            server.start();
            System.out.println("Server running on port " + PORT + "...");
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void main( String[] args ) throws IOException {
        new MyHttpServer().run();
    }

    static class TestHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Server is up!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private class CallHttpHandler extends TestHttpHandler {
    }
}
