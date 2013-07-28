package net.diegolemos.codeelevator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class MyHttpServer
{
    private final int port;

    private Elevator elevator = new Elevator();
    private HttpServer server;

    MyHttpServer(int port) {
        this.port = port;
    }

    void run() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            server. createContext("/test", new TestHttpHandler());
            server. createContext("/call", new CallHttpHandler());
            server. createContext("/nextCommand", new NextCommandHttpHandler());
            server. createContext("/go", new GoHttpHandler());
            server. createContext("/reset", new ResetHttpHandler());
            server. createContext("/userHasEntered", new UserHasEnteredOrExitedHttpHandler());
            server. createContext("/userHasExited", new UserHasEnteredOrExitedHttpHandler());

            server.start();
            System.out.println("Server running on port " + port + "...");
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void main( String[] args ) throws IOException {
        new MyHttpServer(9000).run();
    }

    private class TestHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Server is up!";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }
    }

    private class CallHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Server is up!";
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> params = mapQuery(query);
            elevator.goTo(parseInt(params.get("atFloor")));
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
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
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }
    }

    private class GoHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> params = mapQuery(query);
            elevator.goTo(parseInt(params.get("floorToGo")));
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }
    }

    private Map<String, String> mapQuery(String query){
        Map<String, String> result = new HashMap<>();

        for (String param : query.split("&")) {
            String pair[] = param.split("=");

            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }
            else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    private class ResetHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            elevator = new Elevator();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }
    }

    private class UserHasEnteredOrExitedHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            elevator = new Elevator();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }
    }
}
