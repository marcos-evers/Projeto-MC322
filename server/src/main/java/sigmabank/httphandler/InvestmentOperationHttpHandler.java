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
import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.ClientInvestmentMultiton;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.register.Client;

public class InvestmentOperationHttpHandler implements HttpHandler {
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

    private void handleAssetOperation(Map<String, String> params) {
        UUID clientUUID = UUID.fromString(params.get("uuid"));
        AssetInvestEnum investEnum = AssetInvestEnum.valueOf(params.get("invenum"));
        BigDecimal amount = new BigDecimal(params.get("amount"));

        AssetInvestment investment = ClientInvestmentMultiton.getInstance()
            .getAssetInvestments(clientUUID).get(investEnum);
        Client client = (Client) Database.getInstance().query("Clients",
            (Object obj) -> {
                return ((Client) obj).getUUID().equals(clientUUID);
        }).get(0);
        
        if (params.get("optype").equals("retrieve")) {
            investment.retrieveInvestment(amount);
            client.setBalance(client.getBalance().add(amount));
        } else {
            investment.investMore(amount);
            client.setBalance(client.getBalance().subtract(amount));
        }
    }

    private void handleRateOperation(Map<String, String> params) {
        UUID clientUUID = UUID.fromString(params.get("uuid"));
        RateInvestEnum investEnum = RateInvestEnum.valueOf(params.get("invenum"));
        BigDecimal amount = new BigDecimal(params.get("amount"));

        RateInvestment investment = ClientInvestmentMultiton.getInstance()
            .getRateInvestments(clientUUID).get(investEnum);
        Client client = (Client) Database.getInstance().query("Clients",
            (Object obj) -> {
                return ((Client) obj).getUUID().equals(clientUUID);
        }).get(0);

        if (params.get("optype").equals("retrieve")) {
            investment.retrieveInvestment(amount);
            client.setBalance(client.getBalance().add(amount));
        } else {
            investment.investMore(amount);
            client.setBalance(client.getBalance().subtract(amount));
        }
    }

    /**
     * Handles a investment operation.
     *      - optype = retrieve | investmore
     *      - invtype = asset | rate
     *      - invenum = AssetInvestEnum | RateInvestEnum
     *      - uuid = client uuid
     *      - amount = amount to retrieve or invest more
     */
    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        Map<String, String> params = parseParams(query);

        System.out.println("[MSG] Recived data: " + query);

        try {
            if (params.get("invtype").equals("asset")) {
                handleAssetOperation(params);
            } else {
                handleRateOperation(params);
            }

            Database.getInstance().saveToXML("src/main/resources/database");
            ClientInvestmentMultiton.getInstance()
                .saveInvestments(
                    "src/main/resources/database/RateInvestments.xml",
                    "src/main/resources/database/AssetInvestments.xml"
                );

            String response = "Operation Successful";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            String response = "Registration Unsuccessful: " + e.getMessage();
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
