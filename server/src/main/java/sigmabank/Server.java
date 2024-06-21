package sigmabank;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import sigmabank.database.Database;
import sigmabank.httphandler.ApprovalHttpHandler;
import sigmabank.httphandler.InvestmentHttpHandler;
import sigmabank.httphandler.LoanHttpHandler;
import sigmabank.httphandler.RegisterHttpHandler;

import sigmabank.model.register.Client;
import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.loan.Loan;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        Database.getInstance()
            .addTable("Clients", Client.class)
            .addTable("ClientsToApproval", Client.class)
            .addTable("Loans", Loan.class)
            .addTable("AssetInvestments", AssetInvestment.class)
            .addTable("RateInvestments", RateInvestment.class)
            .loadFromXML();

        server = HttpServer.create(new InetSocketAddress(port), 0);

        // POST - Add a new client to approval
        // GET - Given CPF and password return the respective client
        server.createContext("/client", new RegisterHttpHandler());
        
        // POST - Informs that a client was approved or not
        // GET - Return the clients to be approved
        server.createContext("/client/toapproval", new ApprovalHttpHandler());

        // POST - Create a new investment in the server
        // GET - Return the investments of a client
        server.createContext("/investment", new InvestmentHttpHandler());

        // POST - Create a new loan in the server
        // GET - Return the loans of a client
        server.createContext("/loan", new LoanHttpHandler());

        server.setExecutor(null);

        server.start();
        System.out.println("[MSG] Server listening on port " + port);
    }
}
