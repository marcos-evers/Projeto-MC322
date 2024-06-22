package sigmabank.net;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.RateInvestment;

public class InvestmentOperationConnection implements IConnection<Investment> {
    private final String uri;

    public InvestmentOperationConnection(String uri) {
        this.uri = uri;
    }

    /**
     * Send an investment operation to the server
     *
     * @param investment the investment of the operation
     * @param operation the operation name, 'retrieve' or 'investmore'
     * @param amount how much to retrieve or how much to invest more
     */
    public void sendOperation(Investment investment, String operation, BigDecimal amount) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put("optype", operation);
        if (investment instanceof AssetInvestment) {
            params.put("invtype", "asset");
            params.put("invenum", ((AssetInvestment) investment).getAssetType());
        } else {
            params.put("invtype", "rate");
            params.put("invenum", ((RateInvestment) investment).getRateType());
        }
        params.put("uuid", investment.getClientUUID());
        params.put("amount", amount);

        send(params);
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
        return null;
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
