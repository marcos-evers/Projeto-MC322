package sigmabank;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import sigmabank.database.Database;
import sigmabank.httphandler.ApprovalHttpHandler;
import sigmabank.httphandler.InvestmentHttpHandler;
import sigmabank.httphandler.InvestmentOperationHttpHandler;
import sigmabank.httphandler.LoanHttpHandler;
import sigmabank.httphandler.LoanPaymentHttpHandler;
import sigmabank.httphandler.RegisterHttpHandler;

import sigmabank.model.register.Client;
import sigmabank.model.investment.ClientInvestmentMultiton;
import sigmabank.model.loan.Loan;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        Database.getInstance()
            .addTable("Clients", Client.class)
            .addTable("ClientsToApproval", Client.class)

            .addTable("Loans", Loan.class)
            .addTable("LoansToApproval", Loan.class)

            .loadFromXML();

        ClientInvestmentMultiton.getInstance()
            .loadInvestments("src/main/resources/database/RateInvestments.xml",
                             "src/main/resources/database/AssetInvestments.xml");

        server = HttpServer.create(new InetSocketAddress(port), 0);

        // POST - Add a new client to approval
        // GET - Given CPF and password return the respective client
        server.createContext("/client", new RegisterHttpHandler());
        
        // POST - Informs that a client or a loan was approved or not
        // GET - Return the clients and loans to be approved
        server.createContext("/toapproval", new ApprovalHttpHandler());

        // POST - Create a new investment in the server
        // GET - Return the investments of a client
        server.createContext("/investment", new InvestmentHttpHandler());

        // POST - Retrieve or invest more
        server.createContext("/investment/operate", new InvestmentOperationHttpHandler());

        // POST - Create a new loan in the server
        // GET - Return the loans of a client
        server.createContext("/loan", new LoanHttpHandler());

        // POST - Receive a loan payment
        server.createContext("/loan/payment", new LoanPaymentHttpHandler());

        server.setExecutor(null);

        server.start();
        System.out.println("[MSG] Server listening on port " + port);
    }
}
