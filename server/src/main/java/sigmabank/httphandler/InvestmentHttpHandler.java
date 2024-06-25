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

import sigmabank.database.Database;
import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.ClientInvestmentMultiton;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.register.Client;

public class InvestmentHttpHandler implements HttpHandler {
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

    /**
     * Handle the create of a new investment
     * Expected Data:
     *      - clientUUID
     *      - invtype: asset or rate
     *      - type: AssetInvestmentType or RateInvestmentType
     *      - investedvalue
     *      - startdate
     */
    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        Map<String, String> params = parseParams(query);

        System.out.println("[MSG] Recived data: " + query);

        try {
            UUID clientUUID = UUID.fromString(params.get("clientUUID"));
            Investment investment = null;
            if (params.get("invtype").equals("asset")) {
                investment = ClientInvestmentMultiton.getInstance().getAssetInvestment(
                    UUID.fromString(params.get("clientUUID")),
                    AssetInvestEnum.valueOf(params.get("type")),
                    new BigDecimal(params.get("investedvalue")),
                    LocalDate.parse(params.get("startdate"))
                );
            } else {
                investment = ClientInvestmentMultiton.getInstance().getRateInvestment(
                    UUID.fromString(params.get("clientUUID")),
                    RateInvestEnum.valueOf(params.get("type")),
                    new BigDecimal(params.get("investedvalue")),
                    LocalDate.parse(params.get("startdate"))
                );
            }

            ((Client) Database.getInstance().query("Clients", (Object obj) -> {
                return ((Client) obj).getUUID().equals(clientUUID);
            }).get(0)).addInvestment(investment);
        
            Database.getInstance().saveToXML("src/main/resources/database");
            ClientInvestmentMultiton.getInstance()
                .saveInvestments(
                    "src/main/resources/database/RateInvestments.xml",
                    "src/main/resources/database/AssetInvestments.xml"
                );

            String response = "Register investment: " + investment.toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            e.printStackTrace();
            String response = "Cannot invest: " + e.getMessage();
            exchange.sendResponseHeaders(200, response.getBytes().length);
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
    private void handleGETMethod(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().toString().substring("/investment?".length());
        Map<String, String> params = parseParams(query);

        UUID clientUUID = UUID.fromString(params.get("uuid"));

        Map<?, ?> rateInvestments = ClientInvestmentMultiton.getInstance()
            .getRateInvestments(clientUUID);

        Map<?, ?> assetInvestments = ClientInvestmentMultiton.getInstance()
            .getAssetInvestments(clientUUID);

        try {
            StringWriter sw = new StringWriter();
            JAXBContext jaxbcontext = JAXBContext.newInstance(Investment.class, AssetInvestment.class, RateInvestment.class);
            Marshaller marshaller = jaxbcontext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            for (Object investment: assetInvestments.values())
                marshaller.marshal(investment, sw);
            for (Object investment: rateInvestments.values())
                marshaller.marshal(investment, sw);

            String response = "<Investments>" + sw.toString() + "</Investments>";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            e.printStackTrace();
            String response = "Something went wrong: " + e.getMessage();
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
