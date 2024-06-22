package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.List;

import sigmabank.model.loan.Loan;

public class LoanPaymentConnection implements IConnection<Loan> {
    private final String uri;

    public LoanPaymentConnection(String uri) {
        this.uri = uri;
    }

    /**
     * Do a GET request to the server for search for the loans of a client.
     *
     * @param params A map with two keys:
     *      - clientUUID (String).
     * @return a list containing the loans of the client.
     * @throws IOException if a error occur connecting to the server.
     */
    public List<Loan> fetch(Map<String, Object> params) throws IOException {
        return null;
    }

    /**
     * Do a POST request to register a client that will pending approval
     *
     * @param params A map with client attributes.
     * @throws IOException if a error occur connecting to the server
     */
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

    private String buildPostData(Map<String, Object> params) {
        return "clientuuid=" + params.get("clientuuid").toString() +"&"
            +   "loanuuid=" + params.get("loanuuid") .toString()+ "&"
            +   "value=" + params.get("value").toString();
    }
}
