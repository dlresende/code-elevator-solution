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

    MyHttpServer(int port) {
        this.port = port;
    }

    void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/test", new TestHttpHandler());
        server.createContext("/call", new CallHttpHandler());
        server.createContext("/nextCommand", new NextCommandHttpHandler());
        server.createContext("/go", new GoHttpHandler());
        server.createContext("/reset", new ResetHttpHandler());
        server.createContext("/userHasEntered", new DoNothing());
        server.createContext("/userHasExited", new DoNothing());

        server.start();

        System.out.println("Server running on port " + port + "...");
    }

    public static void main( String[] args ) throws IOException {
        new MyHttpServer(9000).run();
    }

    private abstract class AbstractHttpHandler implements HttpHandler {
        protected static final String OK = "OK";

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = respond(httpExchange);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println(httpExchange.getRequestURI() + " " + response);
        }

        public abstract String respond(HttpExchange httpExchange);

        protected Map<String, String> mapQuery(String query){
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
    }

    private class TestHttpHandler extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            return "Server is up!";
        }
    }

    private class CallHttpHandler extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> params = mapQuery(query);
            elevator.goTo(parseInt(params.get("atFloor")));
            return OK;
        }
    }

    private class NextCommandHttpHandler extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            return elevator.nextCommand();
        }
    }

    private class GoHttpHandler extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> params = mapQuery(query);
            elevator.goTo(parseInt(params.get("floorToGo")));
            return OK;
        }
    }

    private class ResetHttpHandler extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            elevator = new Elevator();
            return OK;
        }
    }

    private class DoNothing extends AbstractHttpHandler {
        @Override
        public String respond(HttpExchange httpExchange) {
            return OK;
        }
    }
}
