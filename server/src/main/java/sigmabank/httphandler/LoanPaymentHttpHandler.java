package sigmabank.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.StringWriter;

import java.math.BigDecimal;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.util.List;

import sigmabank.database.Database;
import sigmabank.model.loan.Loan;

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

    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        Map<String, String> params = parseParams(query);

        UUID clientUUID = UUID.fromString(params.get("clientUUID"));
        UUID loanUUID = UUID.fromString(params.get("loanUUID"));
        BigDecimal value = new BigDecimal(params.get("value"));

        System.out.println("[MSG] Recived data: " + query);

        try { 
            Loan loan = (Loan) Database.getInstance().query("Loan",
                (Object obj) -> {
                    return ((Loan)obj).getLoanUUID().equals(loanUUID)
                        && ((Loan)obj).getClientUUID().equals(clientUUID);
            }).get(0);

            loan.payLoan(value);

            if (loan.getAmount().compareTo(BigDecimal.ZERO) < 0)
                throw new Exception("Value more then the amount");
            if (loan.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                Database.getInstance().deleteEntries("Loan",
                    (Object obj) -> {
                        return ((Loan)obj).getLoanUUID().equals(loanUUID)
                            && ((Loan)obj).getClientUUID().equals(clientUUID);
                });
            }
                

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
