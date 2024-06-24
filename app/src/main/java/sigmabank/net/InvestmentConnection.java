package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.RateInvestment;
import sigmabank.utils.readers.ReaderFactory;

public class InvestmentConnection extends Connection<Investment> {
    public InvestmentConnection(String uri) {
        super(uri);
    }

    /**
     * Do a POST request to create a new investment.
     *
     * @param params A map with thw data to create a investment
     *      - clientUUID
     *      - invtype = asset or rate
     *      - type: AssetInvestmentEnum | RateInvestmentEnum
     *      - investedvalue
     *      - startdate
     */
    public void send(Map<String, Object> params) throws IOException {
        super.send(params);
    }

    public List<Investment> fetch(Map<String, Object> params) throws IOException {
        URI requestURI = URI.create(buildFetchURI(params));
        URL requestURL = requestURI.toURL();
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection(); 

        connection.setRequestMethod("GET");
        
        String responseBody = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            responseBody = response.toString();
            System.out.println("[MSG] Fetch client: " + responseBody);
        }
        connection.disconnect();
        
        List<Object> rateInvestments = ReaderFactory.createReader(RateInvestment.class).readStringXML(responseBody);
        List<Object> assetInvestments = ReaderFactory.createReader(AssetInvestment.class).readStringXML(responseBody);

        List<Investment> investmentsInvestment = new ArrayList<>();
        for (Object investment : rateInvestments)  investmentsInvestment.add((Investment) investment);
        for (Object investment : assetInvestments) investmentsInvestment.add((Investment) investment);
        return investmentsInvestment;
    }

    private String buildFetchURI(Map<String, Object> params) {
        return getURI() + "?"
            + "uuid=" + params.get("clientUUID");
    }
}
