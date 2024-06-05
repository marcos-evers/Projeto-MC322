package sigmabank;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        
        System.out.println("[MSG] Server started in port " + port);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
