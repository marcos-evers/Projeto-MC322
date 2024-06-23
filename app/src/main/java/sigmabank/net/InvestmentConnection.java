package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sigmabank.model.investment.Investment;
import sigmabank.utils.readers.ReaderFactory;

public class InvestmentConnection implements IConnection<Investment> {
    private final String uri;

    public InvestmentConnection(String uri) {
        this.uri = uri;
    }

    public void send(Map<String, Object> params) throws IOException {
        URL url = URI.create(uri).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(buildPostData(params));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("[MSG] Response: " + responseCode + "/" + response.toString());
        }
        connection.disconnect();
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
        
        List<Object> investments = ReaderFactory.createReader(Investment.class).readStringXML(responseBody);
        List<Investment> investmentsInvestment = new ArrayList<>();
        for (Object investment : investments) {
            investmentsInvestment.add((Investment) investment);
        }
        return investmentsInvestment;
    }

    private String buildFetchURI(Map<String, Object> params) {
        return uri + "?"
            + "uuid=" + params.get("clientUUID").toString();
    }

    private String buildPostData(Map<String, Object> params) {
        String postdata = "";
        for (String key: params.keySet()) {
            String data = params.get(key).toString();
            if (postdata.isEmpty())
                postdata += "&" + data;
            else
                postdata += "&" + data;
        }
        return postdata;
    }
}
