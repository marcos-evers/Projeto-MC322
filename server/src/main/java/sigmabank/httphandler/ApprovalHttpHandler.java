package sigmabank.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.StringWriter;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.util.List;

import sigmabank.database.Database;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class ApprovalHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handlePOSTMethod(exchange);
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGETMethod(exchange);
        } else {
            String response = "Invalid request";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private boolean handleClientApproval(Map<String, String> params) {
        UUID clientUUID = UUID.fromString(params.get("uuid"));
        boolean isapproved = params.get("isapproved").equals("true");

        List<Object> clients = Database.getInstance().deleteEntries("ClientsToApproval",
            (Object obj) -> {
                Client client = (Client) obj;
                return client.getUUID().equals(clientUUID);
        });

        if (clients.size() != 1 || !isapproved) return false;

        Client client = (Client) clients.get(0);
        Database.getInstance().addEntry("Clients", client);
        Database.getInstance().saveToXML();

        return true;
    }

    private boolean handleLoanApproval(Map<String, String> params) {
        UUID loanUUID = UUID.fromString(params.get("uuid"));
        boolean isapproved = params.get("isapproved").equals("true");

        List<Object> loans = Database.getInstance().deleteEntries("LoansToApproval",
            (Object obj) -> {
                Loan loan = (Loan) obj;
                return loan.getLoanUUID().equals(loanUUID);
        });

        if (loans.size() != 1 || !isapproved) return false;

        Loan loan = (Loan) loans.get(0);
        Database.getInstance().addEntry("Loans", loan);
        Database.getInstance().saveToXML();

        return true;
    }

    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        Map<String, String> params = parseParams(query);

        String response = "";
        if ((params.get("type").equals("loan") && !handleLoanApproval(params))
                || (params.get("type").equals("client") && !handleClientApproval(params))) {
            response = "Not Approved";
        } else {
            response = "Approved";
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void handleGETMethod(HttpExchange exchange) throws IOException {
        List<Object> clients = Database.getInstance().query("ClientsToApproval",
            (Object obj) -> {
                return true;
        });

        List<Object> loans = Database.getInstance().query("LoansToApproval",
            (Object obj) -> {
                return true;
        });

        System.out.println("[MSG] clients to approval: " + clients);
        System.out.println("[MSG] loans to approval: " + loans);

        try {
            StringWriter swclient = new StringWriter();
            StringWriter swloan = new StringWriter();
            JAXBContext jaxbcontext = JAXBContext.newInstance(Client.class, Loan.class);
            Marshaller marshaller = jaxbcontext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            for (Object client: clients)
                marshaller.marshal(client, swclient);
            for (Object loan: loans)
                marshaller.marshal(loan, swloan);

            String response = 
                "<Response>" 
                + "<ClientsToApproval>" + swclient.toString() + "</ClientsToApproval>"
                + "<LoansToApproval>" + swloan.toString() + "</LoansToApproval>"
                + "</Response>";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            System.out.println("[ERR] " + e.getMessage());
            e.printStackTrace();
            String response = "Something went wrong: " + e.getMessage();
            exchange.sendResponseHeaders(400, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private Map<String, String> parseParams(String query) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], URLDecoder.decode(entry[1], StandardCharsets.UTF_8.name()));
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
