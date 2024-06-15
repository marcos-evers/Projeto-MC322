package sigmabank;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import sigmabank.database.Database;
import sigmabank.httphandler.RegisterHttpHandler;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        Database.getInstance()
            .addTable("ClientPersonal")
            .addTable("ClientEnterprise")
            .addTable("Investment")
            .addTable("Loan");

        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/register", new RegisterHttpHandler());

        server.setExecutor(null);

        server.start();
        System.out.println("[MSG] Server listening on port " + port);
    }
}
