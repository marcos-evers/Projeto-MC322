package sigmabank.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sigmabank.database.Database;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class LoanPaymentHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handlePOSTMethod(exchange);
        } else {
            String response = "Invalid request";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    /**
     * 
     * @param exchange
     * @throws IOException
     */
    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();

        Map<String, String> params = parseParams(query);

        System.out.println("[MSG] Recived data: " + query);

        UUID clientUUID = UUID.fromString(params.get("clientuuid"));
        UUID loanUUID = UUID.fromString(params.get("loanuuid"));
        BigDecimal value = new BigDecimal(params.get("value"));

        try {
            Client client = (Client) Database.getInstance()
                .query("Clients", (Object obj) -> {
                    return ((Client) obj).getUUID().equals(clientUUID);
                }).get(0);
            Loan loan = (Loan) Database.getInstance()
                .query("Loans", (Object obj) -> {
                    return ((Loan)obj).getLoanUUID().equals(loanUUID)
                        && ((Loan)obj).getClientUUID().equals(clientUUID);
                }).get(0);

            client.removeLoan(loan);
            loan.payLoan(value);

            if (loan.getAmount().compareTo(BigDecimal.ZERO) < 0)
                throw new Exception("Value more then the amount");
            if (loan.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                Database.getInstance().deleteEntries("Loans",
                    (Object obj) -> {
                        return ((Loan)obj).getLoanUUID().equals(loanUUID)
                            && ((Loan)obj).getClientUUID().equals(clientUUID);
                });
            } else client.addLoan(loan);

            Database.getInstance().saveToXML();

            String response = "Payed " + value + " from loan successfuly";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            String response = "Something went wrong " + e.getMessage();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    /**
     * 
     * @param query
     * @return
     * @throws UnsupportedEncodingException
     */
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
