package sigmabank;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import sigmabank.handler.RegisterHandler;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/register", new RegisterHandler());
        server.setExecutor(null);
        
        System.out.println("[MSG] Server started in port " + port);
        server.start();
    }
}
