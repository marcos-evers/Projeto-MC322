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


import java.util.List;

import sigmabank.database.Database;
import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;

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
        List<Object> objects = Database.getInstance().query("AssetInvestments",
            (Object obj) -> {
                AssetInvestment investment = (AssetInvestment) obj;
                return investment.getClientUUID().equals(clientUUID)
                    && investment.getAssetType().equals(investEnum);
        });

        if (objects.size() != 1) return;

        AssetInvestment investment = (AssetInvestment) objects.get(0);

        if (params.get("optype").equals("retrive")) {
            investment.retrieveInvestment(amount);
        } else {
            investment.investMore(amount);
        }
    }

    private void handleRateOperation(Map<String, String> params) {
        UUID clientUUID = UUID.fromString(params.get("uuid"));
        RateInvestEnum investEnum = RateInvestEnum.valueOf(params.get("invenum"));
        BigDecimal amount = new BigDecimal(params.get("amount"));
        List<Object> objects = Database.getInstance().query("RateInvestment",
            (Object obj) -> {
                RateInvestment investment = (RateInvestment) obj;
                return investment.getClientUUID().equals(clientUUID)
                    && investment.getRateType().equals(investEnum);
        });

        if (objects.size() != 1) return;

        RateInvestment investment = (RateInvestment) objects.get(0);

        if (params.get("optype").equals("retrive")) {
            investment.retrieveInvestment(amount);
        } else {
            investment.investMore(amount);
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
