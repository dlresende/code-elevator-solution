package net.diegolemos.codeelevator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class MyHttpServer
{
    private static final int PORT = 9000;
    private static HttpServer server;
    private Elevator elevator = new Elevator();

    void run() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server. createContext("/test", new TestHttpHandler());
            server. createContext("/call", new CallHttpHandler());
            server. createContext("/nextCommand", new NextCommandHttpHandler());

            server.start();
            System.out.println("Server running on port " + PORT + "...");
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void main( String[] args ) throws IOException {
        new MyHttpServer().run();
    }

    private class TestHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Server is up!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private class CallHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Server is up!";
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> params = mapQuery(query);
            elevator.callFrom(Integer.parseInt(params.get("atFloor")));
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private class NextCommandHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = elevator.nextCommand();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private Map<String, String> mapQuery(String query){
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
