package sigmabank;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;


import sigmabank.httphandler.RegisterHttpHandler;
public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String filePath = "./post.html"; // Correct path to your HTML file
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            }
        });

        server.createContext("/register", new RegisterHttpHandler());

        server.setExecutor(null); // creates a default executor
        System.out.println("[MSG] Server started on port " + port);
        server.start();
    }
}
